package com.sz.applet.miniBusiness.translation;

import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityListDTO;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationActivityVO;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityService;
import com.sz.core.common.translate.Translator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 校友会活动列表翻译
 */
@Service
@AllArgsConstructor
public class AppletAlumniAssociationActivityTranslator implements Translator<Long, List<AppletAlumniAssociationActivityVO>> {
    private final AppletAlumniAssociationActivityService appletAlumniAssociationActivityService;

    @Override
    public List<AppletAlumniAssociationActivityVO> translate(Long sourceValue) {
        return appletAlumniAssociationActivityService.list(
                new AppletAlumniAssociationActivityListDTO().setAlumniAssociationId(sourceValue)
                        .setStatus("1"));
    }
}
