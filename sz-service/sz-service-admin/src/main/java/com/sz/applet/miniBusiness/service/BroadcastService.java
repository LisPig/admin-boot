package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.po.Broadcast;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.entity.PageResult;
import java.util.List;
import com.sz.applet.miniBusiness.pojo.dto.BroadcastCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.BroadcastUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.BroadcastListDTO;
import com.sz.applet.miniBusiness.pojo.vo.BroadcastVO;

/**
 * <p>
 * 广播表 Service
 * </p>
 *
 * @author LisPig
 * @since 2025-10-30
 */
public interface BroadcastService extends IService<Broadcast> {

    void create(BroadcastCreateDTO dto);

    void update(BroadcastUpdateDTO dto);

    PageResult<BroadcastVO> page(BroadcastListDTO dto);

    List<BroadcastVO> list(BroadcastListDTO dto);

    void remove(SelectIdsDTO dto);

    BroadcastVO detail(Object id);
}