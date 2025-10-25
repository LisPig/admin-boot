package com.sz.applet.miniBusiness.service;


import com.sz.applet.miniBusiness.pojo.bo.AppletAlumniCardBo;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniCard;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 校友卡表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IAppletAlumniCardService extends IService<AppletAlumniCard> {

    // 审核校园卡
    Boolean checkAppletAlumniCard(AppletAlumniCardBo  bo);

    Boolean save(AppletAlumniCardBo appletAlumniCardBo);

}