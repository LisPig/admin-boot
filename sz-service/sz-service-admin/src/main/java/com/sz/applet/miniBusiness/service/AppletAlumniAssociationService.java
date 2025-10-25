package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociation;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.entity.PageResult;
import java.util.List;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationListDTO;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationVO;

/**
 * <p>
 * 校友会表 Service
 * </p>
 *
 * @author LisPig
 * @since 2025-10-25
 */
public interface AppletAlumniAssociationService extends IService<AppletAlumniAssociation> {

    void create(AppletAlumniAssociationCreateDTO dto);

    void update(AppletAlumniAssociationUpdateDTO dto);

    PageResult<AppletAlumniAssociationVO> page(AppletAlumniAssociationListDTO dto);

    List<AppletAlumniAssociationVO> list(AppletAlumniAssociationListDTO dto);

    void remove(SelectIdsDTO dto);

    AppletAlumniAssociationVO detail(Object id);
}