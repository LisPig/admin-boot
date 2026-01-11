package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociation;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationListVO;
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

    String create(AppletAlumniAssociationCreateDTO dto);

    void update(AppletAlumniAssociationUpdateDTO dto);

    /**
     * 校友会审核
     * @param dto
     * @return
     */
    Boolean approve(AppletAlumniAssociationUpdateDTO dto); // 校友会审核

    PageResult<AppletAlumniAssociationVO> page(AppletAlumniAssociationListDTO dto);

    List<AppletAlumniAssociationListVO> list(AppletAlumniAssociationListDTO dto);

    void remove(SelectIdsDTO dto);

    AppletAlumniAssociationVO detail(Object id);

    // 入会申请
    Boolean apply(Long associationId);

    // 申请成为会长
    Boolean applyPresident(Long associationId);

    // 用户加入的校友会列表
    List<AppletAlumniAssociationVO> userJoinList(Long userId);

    // 用户退出校友会
    Boolean userQuit(Long associationId);
}