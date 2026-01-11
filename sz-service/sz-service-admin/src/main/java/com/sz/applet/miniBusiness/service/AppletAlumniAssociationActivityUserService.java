package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityListDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityUpdateDTO;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivity;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivityUser;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationActivityVO;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;

import java.util.List;

/**
 * <p>
 * 校友会活动表 Service
 * </p>
 *
 * @author sz-admin
 * @since 2025-10-25
 */
public interface AppletAlumniAssociationActivityUserService extends IService<AppletAlumniAssociationActivityUser> {

    Boolean apply(Long associationId);
}