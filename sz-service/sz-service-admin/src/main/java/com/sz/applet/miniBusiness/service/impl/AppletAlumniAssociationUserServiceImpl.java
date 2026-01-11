package com.sz.applet.miniBusiness.service.impl;


import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociation;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationService;
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
}