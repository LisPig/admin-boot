package com.sz.applet.miniBusiness.service;


import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationUser;
import com.mybatisflex.core.service.IService;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;

import java.util.List;

/**
 * 校友会用户表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface AppletAlumniAssociationUserService extends IService<AppletAlumniAssociationUser> {

    boolean join(AppletAlumniAssociationUser appletAlumniAssociationUser);

    MiniUserVO getUser(Long userId);

}