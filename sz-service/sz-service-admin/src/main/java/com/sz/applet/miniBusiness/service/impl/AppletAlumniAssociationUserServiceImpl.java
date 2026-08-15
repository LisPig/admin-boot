package com.sz.applet.miniBusiness.service.impl;


import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociation;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationService;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.common.exception.common.BusinessException;
import com.sz.admin.system.service.MediaCheckService;
import com.sz.core.util.BeanCopyUtils;
import com.sz.security.core.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationUserService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationUser;
import com.sz.applet.miniBusiness.mapper.AppletAlumniAssociationUserMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.util.List;

/**
 * 校友会用户表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class AppletAlumniAssociationUserServiceImpl extends ServiceImpl<AppletAlumniAssociationUserMapper, AppletAlumniAssociationUser> implements AppletAlumniAssociationUserService {

    private final MiniUserService miniUserService;

    private final MediaCheckService mediaCheckService;

    @Override
    public boolean join(AppletAlumniAssociationUser appletAlumniAssociationUser) {
        // 先判断是否已经是会员
        AppletAlumniAssociationUser user = this.getOne(new QueryWrapper()
                .eq(AppletAlumniAssociationUser::getAlumniAssociationId,appletAlumniAssociationUser.getAlumniAssociationId())
                .eq(AppletAlumniAssociationUser::getUserId, LoginUtils.getMiniLoginUser().getUserId()));
        if (user == null) {
            // 添加
            appletAlumniAssociationUser.setUserId(LoginUtils.getMiniLoginUser().getUserId());
            return this.save(appletAlumniAssociationUser);
        }
        return false;
    }

    @Override
    public MiniUserVO getUser(Long userId) {
        MiniUser miniUser = miniUserService.getById(userId);
        MiniUserVO miniUserVO = new MiniUserVO();
        BeanCopyUtils.copy(miniUser, miniUserVO);
        miniUserVO.setAvatarUrl(mediaCheckService.resolveAvatarUrl(miniUserVO.getAvatarUrl()));

        return miniUserVO;
    }

    @Override
    public boolean transfer(AppletAlumniAssociationUser appletAlumniAssociationUser) {
        AppletAlumniAssociationUser managerUser = this.getOne(new QueryWrapper()
                .eq(AppletAlumniAssociationUser::getAlumniAssociationId,appletAlumniAssociationUser.getAlumniAssociationId())
                .eq(AppletAlumniAssociationUser::getUserId, LoginUtils.getMiniLoginUser().getUserId())
        );
        if(managerUser != null && "2".equals(managerUser.getIdentity())){

            AppletAlumniAssociationUser userToUpdate = this.getOne(new QueryWrapper()
                    .eq(AppletAlumniAssociationUser::getAlumniAssociationId,appletAlumniAssociationUser.getAlumniAssociationId())
                    .eq(AppletAlumniAssociationUser::getUserId, appletAlumniAssociationUser.getUserId())
            );
            userToUpdate.setIdentity("2");
            managerUser.setIdentity("1");
            return this.updateById(managerUser) && this.updateById(userToUpdate);

        }
        throw new BusinessException(CommonResponseEnum.FAILURE,null, "转让异常");
    }
}