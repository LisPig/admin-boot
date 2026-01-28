package com.sz.applet.miniBusiness.translation;

import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityListDTO;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociation;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationActivityVO;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityService;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationService;
import com.sz.core.common.translate.Translator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 校友会活动列表翻译
 */
@Service
@AllArgsConstructor
public class AppletAlumniAssociationNameTranslator implements Translator<Long, String> {
    private final AppletAlumniAssociationService appletAlumniAssociationService;

    @Override
    public String translate(Long sourceValue) {
        AppletAlumniAssociation appletAlumniAssociation =  appletAlumniAssociationService.getOne(
                new QueryWrapper().eq(AppletAlumniAssociation::getId,sourceValue));
        return appletAlumniAssociation.getName();
    }
}
