package com.sz.applet.miniBusiness.translation;

import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationUser;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationUserService;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.core.common.translate.Translator;
import com.sz.utils.MapstructUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 校友会其下用户翻译
 */
@Service
@AllArgsConstructor
public class AppletAlumniAssociationUserTranslator implements Translator<Long, List<MiniUserVO>> {
    private final AppletAlumniAssociationUserService appletAlumniAssociationUserService;
    private final MiniUserService  miniUserService;
    @Override
    public List<MiniUserVO> translate(Long sourceValue) {
        List<AppletAlumniAssociationUser> appletAlumniAssociationUsers = appletAlumniAssociationUserService.list(new QueryWrapper()
                .eq(AppletAlumniAssociationUser::getAlumniAssociationId, sourceValue)
                .eq(AppletAlumniAssociationUser::getStatus, 1));
        if(appletAlumniAssociationUsers.isEmpty()){
            return null;
        }
        List<MiniUserVO> miniUsers =  miniUserService.listAs(new QueryWrapper()
                .in(MiniUser::getId,appletAlumniAssociationUsers.stream().map(AppletAlumniAssociationUser::getUserId).toList())
                .orderBy(MiniUser::getCreateTime,false), MiniUserVO.class);
        miniUsers.forEach(miniUserVO -> {
            miniUserVO.setIdentity(appletAlumniAssociationUsers.stream().filter(appletAlumniAssociationUser -> appletAlumniAssociationUser.getUserId().equals(miniUserVO.getId())).findFirst().get().getIdentity());
        });
        return miniUsers;//MapstructUtils.convert(miniUsers, MiniUserVO.class);
    }
}
