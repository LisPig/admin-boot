package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.applet.miniBusiness.mapper.AppletAlumniAssociationActivityMapper;
import com.sz.applet.miniBusiness.mapper.AppletAlumniAssociationActivityUserMapper;
import com.sz.applet.miniBusiness.mapper.AppletAlumniAssociationMapper;
import com.sz.applet.miniBusiness.mapper.AppletAlumniAssociationUserMapper;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivity;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivityUser;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationUser;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityUserService;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.common.exception.common.BusinessException;
import com.sz.security.core.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 校友会活动用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class AppletAlumniAssociationActivityUserServiceImpl extends ServiceImpl<AppletAlumniAssociationActivityUserMapper, AppletAlumniAssociationActivityUser> implements AppletAlumniAssociationActivityUserService{

    private final AppletAlumniAssociationActivityMapper appletAlumniAssociationActivityMapper;
    private final AppletAlumniAssociationUserMapper appletAlumniAssociationUserMapper;

    @Override
    public Boolean apply(Long associationActivityId) {
        if(this.exists(new QueryWrapper()
                .eq(AppletAlumniAssociationActivityUser::getAlumniAssociationId,associationActivityId)
                .eq(AppletAlumniAssociationActivityUser::getUserId,LoginUtils.getMiniLoginUser().getUserId())))
            throw new RuntimeException("您已报名该活动");
        AppletAlumniAssociationActivity appletAlumniAssociationActivity = appletAlumniAssociationActivityMapper.selectOneByQuery(new QueryWrapper()
                .eq(AppletAlumniAssociationActivity::getId, associationActivityId)
                .eq(AppletAlumniAssociationActivity::getStatus, "1"));
        if(!isMember(appletAlumniAssociationActivity.getAlumniAssociationId())){
            throw new BusinessException(CommonResponseEnum.AssociationUserNotExist,null,"请先加入该校友会");
        }
        if(appletAlumniAssociationActivity != null){
            AppletAlumniAssociationActivityUser appletAlumniAssociationActivityUser = new AppletAlumniAssociationActivityUser();
            appletAlumniAssociationActivityUser.setAlumniAssociationActivityId(appletAlumniAssociationActivity.getId());
            appletAlumniAssociationActivityUser.setAlumniAssociationId(appletAlumniAssociationActivity.getAlumniAssociationId());
            appletAlumniAssociationActivityUser.setStatus("1");
            appletAlumniAssociationActivityUser.setUserId(LoginUtils.getMiniLoginUser().getUserId());
            return this.save(appletAlumniAssociationActivityUser);
        }
        return false;
    }

    private Boolean isMember(Long associationId) {
        AppletAlumniAssociationUser appletAlumniAssociationUser = appletAlumniAssociationUserMapper.selectOneByQuery(
                new QueryWrapper().eq(AppletAlumniAssociationUser::getUserId, LoginUtils.getMiniLoginUser().getUserId())
                        .eq(AppletAlumniAssociationUser::getAlumniAssociationId, associationId));
        return appletAlumniAssociationUser != null;
    }
}
