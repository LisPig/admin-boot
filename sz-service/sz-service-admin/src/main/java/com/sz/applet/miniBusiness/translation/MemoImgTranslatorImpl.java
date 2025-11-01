package com.sz.applet.miniBusiness.translation;

import cn.hutool.core.util.ObjectUtil;
import com.sz.core.common.translate.Translator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MemoImgTranslatorImpl implements Translator<String, List<String>> {
    @Override
    public List<String> translate(String sourceValue) {
        if(ObjectUtil.isNotEmpty(sourceValue)) {
            return List.of(sourceValue.split(","));
        }
        return null;
    }
}
