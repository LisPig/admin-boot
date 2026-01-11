package com.sz.applet.miniBusiness.translation;

import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivity;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityService;
import com.sz.core.common.translate.Translator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 校友会活动统计表 翻译器
 * </p>
 *
 * @author LisPig
 * @since 2025-10-25
 */
@Service
@AllArgsConstructor
public class AssociationActivityCountTranslator implements Translator<Long,Long> {
    private final AppletAlumniAssociationActivityService appletAlumniAssociationActivityService;
    @Override
    public Long translate(Long sourceValue) {
        return appletAlumniAssociationActivityService.count(new QueryWrapper()
                .eq(AppletAlumniAssociationActivity::getStatus,"1")
                .eq(AppletAlumniAssociationActivity::getAlumniAssociationId,sourceValue));
    }
}
