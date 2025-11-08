package com.sz.applet.miniBusiness.translation;

import cn.hutool.core.util.ObjectUtil;
import com.sz.core.common.translate.Translator;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DistanceEndDaysTranslationImpl implements Translator<String, Integer> {
    @Override
    public Integer translate(String sourceValue) {
        // 切割sourceValue

        if(ObjectUtil.isNotEmpty(sourceValue)) {
            String[] split = sourceValue.split(",");
            //得出开始时间和结束时间
            String startTime = split[0];
            String endTime = split[1];

            //计算剩余天数
            return (int) (Long.parseLong(endTime) - Long.parseLong(startTime)) / 86400000;
        }
        return null;
    }
}
