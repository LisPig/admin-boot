package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.applet.miniBusiness.mapper.AppletAlumniAssociationActivityUserMapper;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivityUser;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityUserService;
import com.sz.security.core.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 校友会活动用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class AppletAlumniAssociationActivityUserServiceImpl extends ServiceImpl<AppletAlumniAssociationActivityUserMapper, AppletAlumniAssociationActivityUser> implements AppletAlumniAssociationActivityUserService{


    @Override
    public Boolean apply(Long associationId) {
        if(this.exists(new QueryWrapper()
                .eq(AppletAlumniAssociationActivityUser::getAlumniAssociationId,associationId)
                .eq(AppletAlumniAssociationActivityUser::getUserId,LoginUtils.getMiniLoginUser().getUserId())))
            throw new RuntimeException("您已报名该活动");
        AppletAlumniAssociationActivityUser appletAlumniAssociationActivityUser = new AppletAlumniAssociationActivityUser();
        appletAlumniAssociationActivityUser.setAlumniAssociationId(associationId);
        appletAlumniAssociationActivityUser.setStatus("1");
        appletAlumniAssociationActivityUser.setUserId(LoginUtils.getMiniLoginUser().getUserId());
        return this.save(appletAlumniAssociationActivityUser);
    }
}
