package com.sz.core.common.sensitive;

/**
 * 敏感词工具类
 */
public class SensitiveWordUtils {
    
    /**
     * 敏感词过滤器实例
     */
    private static final SensitiveWordFilter FILTER = new SensitiveWordFilter();
    
    /**
     * 检查文本是否包含敏感词
     * @param text 待检查文本
     * @return true:包含敏感词, false:不包含敏感词
     */
    public static boolean containsSensitiveWord(String text) {
        return FILTER.containsSensitiveWord(text);
    }
    
    /**
     * 获取文本中的所有敏感词
     * @param text 待检查文本
     * @return 敏感词列表
     */
    public static java.util.List<String> getSensitiveWords(String text) {
        return FILTER.getSensitiveWords(text);
    }
    
    /**
     * 替换文本中的敏感词
     * @param text 待处理文本
     * @param replaceChar 替换字符
     * @return 处理后的文本
     */
    public static String replaceSensitiveWords(String text, char replaceChar) {
        return FILTER.replaceSensitiveWords(text, replaceChar);
    }
    
    /**
     * 添加敏感词
     * @param word 敏感词
     */
    public static void addSensitiveWord(String word) {
        FILTER.addSensitiveWord(word);
    }
    
    /**
     * 移除敏感词
     * @param word 敏感词
     */
    public static void removeSensitiveWord(String word) {
        FILTER.removeSensitiveWord(word);
    }
}