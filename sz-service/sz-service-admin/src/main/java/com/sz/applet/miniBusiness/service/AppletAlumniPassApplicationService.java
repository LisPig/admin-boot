package com.sz.applet.miniBusiness.service;


import com.sz.applet.miniBusiness.pojo.bo.AppletAlumniPassApplicationBo;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniPassApplicationCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniPassApplicationListDTO;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniPassApplication;
import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniPassApplicationVO;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;

import java.util.List;

/**
 * 校友通行证申请表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface AppletAlumniPassApplicationService extends IService<AppletAlumniPassApplication> {

    void create(AppletAlumniPassApplicationCreateDTO dto);

    void update(AppletAlumniPassApplicationCreateDTO dto);

    PageResult<AppletAlumniPassApplicationVO> list(AppletAlumniPassApplicationListDTO dto);

    void remove(SelectIdsDTO dto);

    AppletAlumniPassApplicationVO detail(Object id);
    PageResult<AppletAlumniPassApplicationVO> page(AppletAlumniPassApplicationBo bo);

    void approve(AppletAlumniPassApplicationCreateDTO dto);
}