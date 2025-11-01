package com.sz.core.common.translate;

import cn.hutool.extra.spring.SpringUtil;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;

/**
 * Translate 翻译工具类
 *
 * @author Toby Ye
 * @since 1.0.0
 */
@Slf4j
@Component
public class TranslateUtil {

    // 使用 Spring 管理的线程池来执行异步翻译任务
    @Resource(name = "scheduledExecutorService")
    private Executor translationExecutor;

    private Executor getExecutor() {
        return translationExecutor != null ? translationExecutor : ForkJoinPool.commonPool();
    }


    /**
     * 排除指定字段进行翻译（阻塞，内部异步） - 适用于列表
     */
    public <T> void translateExclude(List<T> list, String... fieldNames) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Set<String> excludes = fieldNames == null ? Collections.emptySet() : new HashSet<>(Arrays.asList(fieldNames));
        translateBlocking(list, field -> !excludes.contains(field.getName()));
    }

    /**
     * 排除指定字段进行翻译（阻塞，内部异步） - 适用于单对象
     */
    public <T> void translateExclude(T object, String... fieldNames) {
        if (object == null) {
            return;
        }
        translateExclude(Collections.singletonList(object), fieldNames);
    }

    /**
     * 仅翻译指定字段（阻塞，内部异步） - 适用于列表
     */
    public <T> void translateInclude(List<T> list, String... fieldNames) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Set<String> includes = fieldNames == null ? Collections.emptySet() : new HashSet<>(Arrays.asList(fieldNames));
        translateBlocking(list, field -> includes.contains(field.getName()));
    }

    /**
     * 仅翻译指定字段（阻塞，内部异步） - 适用于单对象
     */
    public <T> void translateInclude(T object, String... fieldNames) {
        if (object == null) {
            return;
        }
        translateInclude(Collections.singletonList(object), fieldNames);
    }

    /**
     * 通用翻译（阻塞，内部异步） - 适用于列表
     * 若未提供 fieldNames 或长度为 0，则翻译所有带有 @Translate 的字段；否则等价于 include 指定字段
     */
    public <T> void translate(List<T> list, String... fieldNames) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Predicate<Field> selector = null;
        if (fieldNames != null && fieldNames.length > 0) {
            Set<String> includes = new HashSet<>(Arrays.asList(fieldNames));
            selector = field -> includes.contains(field.getName());
        }
        translateBlocking(list, selector); // selector 为空则翻译全部
    }

    /**
     * 通用翻译（阻塞，内部异步） - 适用于单对象
     */
    public <T> void translate(T object, String... fieldNames) {
        if (object == null) {
            return;
        }
        translate(Collections.singletonList(object), fieldNames);
    }

    /**
     * 统一的阻塞实现：收集任务 -> 并行执行 -> 等待完成 -> 若有失败则抛出异常
     */
    private <T> void translateBlocking(List<T> list, Predicate<Field> fieldSelector) {
        TranslationContext context;
        try {
            context = collectTranslationTasks(list, fieldSelector);
        } catch (Exception e) {
            throw new RuntimeException("Translation failed when collecting tasks: " + e.getMessage(), e);
        }

        CompletableFuture<Void> barrier = executeTranslationTasks(context);
        // 等待所有异步任务结束
        barrier.join();

        if (!context.getErrors().isEmpty()) {
            String msg = String.join("; ", context.getErrors());
            throw new RuntimeException("Translation failed: " + msg);
        }
    }

    /**
     * 批量翻译对象列表（异步）
     */
    private <T> CompletableFuture<Void> translateListAsync(List<T> list) {
        if (list == null || list.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> collectTranslationTasks(list, null), getExecutor())
                .thenCompose(this::executeTranslationTasks);
    }

    /**
     * 翻译单个对象（异步）
     */
    private <T> CompletableFuture<Void> translateAsync(T object) {
        if (object == null) {
            return CompletableFuture.completedFuture(null);
        }
        return translateListAsync(Collections.singletonList(object));
    }

    /**
     * 带字段过滤器的异步翻译
     */
    private <T> CompletableFuture<Void> translateListAsync(List<T> list, Predicate<Field> fieldSelector) {
        if (list == null || list.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> collectTranslationTasks(list, fieldSelector), getExecutor())
                .thenCompose(this::executeTranslationTasks);
    }

    /**
     * 收集翻译任务（翻译目标字段受 fieldSelector 约束；为 null 时表示全部）
     */
    private <T> TranslationContext collectTranslationTasks(List<T> list, Predicate<Field> fieldSelector) {
        TranslationContext context = new TranslationContext();

        for (T item : list) {
            if (item == null) {
                continue;
            }
            Field[] fields = item.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Translate.class)
                        && (fieldSelector == null || fieldSelector.test(field))) {
                    processTranslateField(item, field, context);
                }
            }
        }

        return context;
    }

    /**
     * 处理翻译字段 -> 将任务放入上下文
     */
    private <T> void processTranslateField(T item, Field field, TranslationContext context) {
        Translate translate = field.getAnnotation(Translate.class);
        Class<? extends Translator<?, ?>> translatorClass = translate.translator();
        String sourceFieldName = translate.sourceField();

        try {
            Object sourceValue = getSourceFieldValue(item, field, sourceFieldName);
            if (sourceValue == null) {
                return;
            }
            context.addTranslationTask(translatorClass, sourceValue, item, field);
        } catch (Exception e) {
            throw new RuntimeException("Process translate field error: " + e.getMessage(), e);
        }
    }

    /**
     * 获取源字段值
     */
    private <T> Object getSourceFieldValue(T item, Field field, String sourceFieldName) throws Exception {
        if (!sourceFieldName.isEmpty()) {
            Field sourceField = item.getClass().getDeclaredField(sourceFieldName);
            sourceField.setAccessible(true);
            return sourceField.get(item);
        } else {
            field.setAccessible(true);
            return field.get(item);
        }
    }

    /**
     * 执行翻译任务（并行异步执行，聚合错误信息，不让整体 future 异常完成）
     */
    private CompletableFuture<Void> executeTranslationTasks(TranslationContext context) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (Map.Entry<Class<? extends Translator<?, ?>>, Set<Object>> entry : context.getTranslationValues().entrySet()) {
            Class<? extends Translator<?, ?>> translatorClass = entry.getKey();
            Set<Object> values = entry.getValue();

            Translator<?, ?> translator = SpringUtil.getBean(translatorClass);
            if (translator == null || values.isEmpty()) {
                continue;
            }

            for (Object value : values) {
                CompletableFuture<?> future = CompletableFuture
                        .supplyAsync(() -> invokeTranslateUnchecked(translator, value), getExecutor())
                        .thenAccept(translatedValue -> applyTranslation(context.getFieldInfos(translatorClass, value), translatedValue))
                        .whenComplete((v, ex) -> {
                            if (ex != null) {
                                Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                                context.getErrors().add("translator=" + translatorClass.getSimpleName() + ", value=" + value + ", error=" + cause.getMessage());
                            }
                        });
                futures.add(future);
            }
        }

        // 让整体 future 不因子任务失败而异常完成，统一由调用处检查 context.errors 后抛出
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .exceptionally(ex -> null);
    }

    /**
     * 反射调用 translator.translate(Object)；抛出的异常交由上层处理
     */
    private Object invokeTranslateUnchecked(Translator<?, ?> translator, Object value) {
        try {
            Method translateMethod = translator.getClass().getMethod("translate", Object.class);
            return translateMethod.invoke(translator, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 应用翻译结果
     */
    private void applyTranslation(List<FieldTranslationInfo> fieldInfos, Object translatedValue) {
        for (FieldTranslationInfo info : fieldInfos) {
            try {
                info.field.setAccessible(true);
                info.field.set(info.object, translatedValue);
            } catch (Exception e) {
                // 将写回失败也记录为错误（不抛出以便汇总）
                // 注意：此处无法访问 context，写回失败会在 whenComplete 中被捕捉不到，这里直接记录日志
                log.warn("Apply translation failed: field={}, objectClass={}, error={}", info.field.getName(), info.object.getClass().getSimpleName(), e.getMessage());
            }
        }
    }

    /**
     * 翻译上下文
     */
    private static class TranslationContext {
        @Getter
        private final Map<Class<? extends Translator<?, ?>>, Set<Object>> translationValues = new HashMap<>();
        private final Map<Class<? extends Translator<?, ?>>, Map<Object, List<FieldTranslationInfo>>> fieldInfos = new HashMap<>();
        @Getter
        private final List<String> errors = Collections.synchronizedList(new ArrayList<>());

        public void addTranslationTask(Class<? extends Translator<?, ?>> translatorClass, Object value, Object object, Field field) {
            translationValues.computeIfAbsent(translatorClass, k -> new HashSet<>()).add(value);
            fieldInfos.computeIfAbsent(translatorClass, k -> new HashMap<>())
                    .computeIfAbsent(value, k -> new ArrayList<>())
                    .add(new FieldTranslationInfo(object, field));
        }

        public List<FieldTranslationInfo> getFieldInfos(Class<? extends Translator<?, ?>> translatorClass, Object value) {
            return fieldInfos.getOrDefault(translatorClass, Collections.emptyMap())
                    .getOrDefault(value, Collections.emptyList());
        }
    }

    /**
     * 字段翻译信息
     */
    private static class FieldTranslationInfo {
        Object object;
        Field field;

        FieldTranslationInfo(Object object, Field field) {
            this.object = object;
            this.field = field;
        }
    }
}
