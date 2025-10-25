package com.sz.applet.miniBusiness.service;


import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationUser;
import com.mybatisflex.core.service.IService;

/**
 * 校友会用户表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface AppletAlumniAssociationUserService extends IService<AppletAlumniAssociationUser> {

    boolean join(AppletAlumniAssociationUser appletAlumniAssociationUser);
}