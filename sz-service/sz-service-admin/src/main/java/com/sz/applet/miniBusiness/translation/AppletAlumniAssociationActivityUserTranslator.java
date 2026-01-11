package com.sz.applet.miniBusiness.translation;

import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivityUser;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityUserService;
import com.sz.core.common.translate.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 校友会活动用户数统计转换器
 */
@Service
@RequiredArgsConstructor
public class AppletAlumniAssociationActivityUserTranslator implements Translator<Long,Long> {
    private final AppletAlumniAssociationActivityUserService appletAlumniAssociationActivityUserService;
    @Override
    public Long translate(Long sourceValue) {
        return appletAlumniAssociationActivityUserService.count(new QueryWrapper()
                .eq(AppletAlumniAssociationActivityUser::getAlumniAssociationActivityId,sourceValue)
                .eq(AppletAlumniAssociationActivityUser::getStatus,"1"));
    }
}
