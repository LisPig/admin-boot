package com.sz.core.common.translate;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Translate 注解
 *
 * @author Toby Ye
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Translate {
    /**
     * 翻译器
     */
    Class<? extends Translator<?, ?>> translator();

    /**
     * 源字段名
     */
    String sourceField() default "";
}
