package com.sz.core.common.translate;

/**
 * Translate 翻译器接口
 *
 * @author Toby Ye
 * @since 1.0.0
 */
public interface Translator<S, R> {
    R translate(S sourceValue);
}