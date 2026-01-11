package com.sz.applet.miniBusiness.translation;

import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationUser;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationUserService;
import com.sz.core.common.translate.Translator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 校友会会员人数翻译
 */
@Service
@AllArgsConstructor
public class AssociationMemberTotalTranslator implements Translator<Long, Long> {

    private final AppletAlumniAssociationUserService appletAlumniAssociationUserService;
    @Override
    public Long translate(Long associationId) {
        return appletAlumniAssociationUserService.count(new QueryWrapper()
                .eq(AppletAlumniAssociationUser::getStatus, 1)
                .eq(AppletAlumniAssociationUser::getAlumniAssociationId, associationId));
    }
}
