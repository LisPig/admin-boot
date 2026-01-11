package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivity;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.entity.PageResult;
import java.util.List;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityListDTO;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationActivityVO;

/**
 * <p>
 * 校友会活动表 Service
 * </p>
 *
 * @author sz-admin
 * @since 2025-10-25
 */
public interface AppletAlumniAssociationActivityService extends IService<AppletAlumniAssociationActivity> {

    String create(AppletAlumniAssociationActivityCreateDTO dto);

    void update(AppletAlumniAssociationActivityUpdateDTO dto);

    PageResult<AppletAlumniAssociationActivityVO> page(AppletAlumniAssociationActivityListDTO dto);

    List<AppletAlumniAssociationActivityVO> list(AppletAlumniAssociationActivityListDTO dto);

    void remove(SelectIdsDTO dto);

    AppletAlumniAssociationActivityVO detail(Object id);

    /**
     * 活动审批
     * @param id
     * @return
     */
    Boolean approve(AppletAlumniAssociationActivityUpdateDTO dto);
}