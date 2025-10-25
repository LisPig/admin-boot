package com.sz.applet.miniBusiness.service.impl;


import com.sz.applet.miniBusiness.pojo.bo.AppletAlumniCardBo;
import com.sz.utils.MapstructUtils;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.IAppletAlumniCardService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniCard;
import com.sz.applet.miniBusiness.mapper.AppletAlumniCardMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.util.List;

/**
 * 校友卡表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class AppletAlumniCardServiceImpl extends ServiceImpl<AppletAlumniCardMapper, AppletAlumniCard> implements IAppletAlumniCardService {

    @Override
    public Boolean checkAppletAlumniCard(AppletAlumniCardBo appletAlumniCardBo) {
        AppletAlumniCard appletAlumniCard = new AppletAlumniCard();
        appletAlumniCard.setStatus(appletAlumniCardBo.getStatus());
        appletAlumniCard.setId(appletAlumniCardBo.getId());
        return updateById(appletAlumniCard);
    }

    @Override
    public Boolean save(AppletAlumniCardBo appletAlumniCardBo) {
        return this.save(MapstructUtils.convert(appletAlumniCardBo, AppletAlumniCard.class));
    }
}