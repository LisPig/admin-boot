package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.BroadcastService;
import com.sz.applet.miniBusiness.pojo.po.Broadcast;
import com.sz.applet.miniBusiness.mapper.BroadcastMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.query.QueryChain;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.util.PageUtils;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.Utils;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import java.io.Serializable;
import java.util.List;
import com.sz.applet.miniBusiness.pojo.dto.BroadcastCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.BroadcastUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.BroadcastListDTO;
import com.sz.applet.miniBusiness.pojo.vo.BroadcastVO;

/**
 * <p>
 * 广播表 服务实现类
 * </p>
 *
 * @author LisPig
 * @since 2025-10-30
 */
@Service
@RequiredArgsConstructor
public class BroadcastServiceImpl extends ServiceImpl<BroadcastMapper, Broadcast> implements BroadcastService {
    @Override
    public void create(BroadcastCreateDTO dto){
        Broadcast broadcast = BeanCopyUtils.copy(dto, Broadcast.class);
        save(broadcast);
    }

    @Override
    public void update(BroadcastUpdateDTO dto){
        Broadcast broadcast = BeanCopyUtils.copy(dto, Broadcast.class);
        QueryWrapper wrapper;
        // id有效性校验
        wrapper = QueryWrapper.create()
            .eq(Broadcast::getId, dto.getId());
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);

        saveOrUpdate(broadcast);
    }

    @Override
    public PageResult<BroadcastVO> page(BroadcastListDTO dto){
        Page<BroadcastVO> page = pageAs(PageUtils.getPage(dto), buildQueryWrapper(dto), BroadcastVO.class);
        return PageUtils.getPageResult(page);
    }

    @Override
    public List<BroadcastVO> list(BroadcastListDTO dto){
        return listAs(buildQueryWrapper(dto), BroadcastVO.class);
    }

    @Override
    public void remove(SelectIdsDTO dto){
        CommonResponseEnum.INVALID_ID.assertTrue(dto.getIds().isEmpty());
        removeByIds(dto.getIds());
    }

    @Override
    public BroadcastVO detail(Object id){
        Broadcast broadcast = getById((Serializable) id);
        CommonResponseEnum.INVALID_ID.assertNull(broadcast);
        return BeanCopyUtils.copy(broadcast, BroadcastVO.class);
    }

    private static QueryWrapper buildQueryWrapper(BroadcastListDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create().from(Broadcast.class);
        if (Utils.isNotNull(dto.getTitle())) {
            wrapper.eq(Broadcast::getTitle, dto.getTitle());
        }
        if (Utils.isNotNull(dto.getContent())) {
            wrapper.eq(Broadcast::getContent, dto.getContent());
        }
        if (Utils.isNotNull(dto.getStatus())) {
            wrapper.eq(Broadcast::getStatus, dto.getStatus());
        }
        return wrapper;
    }
}