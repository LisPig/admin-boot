package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivity;
import com.sz.applet.miniBusiness.mapper.AppletAlumniAssociationActivityMapper;
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
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityListDTO;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationActivityVO;

/**
 * <p>
 * 校友会活动表 服务实现类
 * </p>
 *
 * @author sz-admin
 * @since 2025-10-25
 */
@Service
@RequiredArgsConstructor
public class AppletAlumniAssociationActivityServiceImpl extends ServiceImpl<AppletAlumniAssociationActivityMapper, AppletAlumniAssociationActivity> implements AppletAlumniAssociationActivityService {
    @Override
    public void create(AppletAlumniAssociationActivityCreateDTO dto){
        AppletAlumniAssociationActivity appletAlumniAssociationActivity = BeanCopyUtils.copy(dto, AppletAlumniAssociationActivity.class);
        save(appletAlumniAssociationActivity);
    }

    @Override
    public void update(AppletAlumniAssociationActivityUpdateDTO dto){
        AppletAlumniAssociationActivity appletAlumniAssociationActivity = BeanCopyUtils.copy(dto, AppletAlumniAssociationActivity.class);
        QueryWrapper wrapper;
        // id有效性校验
        wrapper = QueryWrapper.create()
            .eq(AppletAlumniAssociationActivity::getId, dto.getId());
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);

        saveOrUpdate(appletAlumniAssociationActivity);
    }

    @Override
    public PageResult<AppletAlumniAssociationActivityVO> page(AppletAlumniAssociationActivityListDTO dto){
        Page<AppletAlumniAssociationActivityVO> page = pageAs(PageUtils.getPage(dto), buildQueryWrapper(dto), AppletAlumniAssociationActivityVO.class);
        return PageUtils.getPageResult(page);
    }

    @Override
    public List<AppletAlumniAssociationActivityVO> list(AppletAlumniAssociationActivityListDTO dto){
        return listAs(buildQueryWrapper(dto), AppletAlumniAssociationActivityVO.class);
    }

    @Override
    public void remove(SelectIdsDTO dto){
        CommonResponseEnum.INVALID_ID.assertTrue(dto.getIds().isEmpty());
        removeByIds(dto.getIds());
    }

    @Override
    public AppletAlumniAssociationActivityVO detail(Object id){
        AppletAlumniAssociationActivity appletAlumniAssociationActivity = getById((Serializable) id);
        CommonResponseEnum.INVALID_ID.assertNull(appletAlumniAssociationActivity);
        return BeanCopyUtils.copy(appletAlumniAssociationActivity, AppletAlumniAssociationActivityVO.class);
    }

    private static QueryWrapper buildQueryWrapper(AppletAlumniAssociationActivityListDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create().from(AppletAlumniAssociationActivity.class);
        if (Utils.isNotNull(dto.getAlumniAssociationId())) {
            wrapper.eq(AppletAlumniAssociationActivity::getAlumniAssociationId, dto.getAlumniAssociationId());
        }
        if (Utils.isNotNull(dto.getTitle())) {
            wrapper.eq(AppletAlumniAssociationActivity::getTitle, dto.getTitle());
        }
        if (Utils.isNotNull(dto.getAvatar())) {
            wrapper.eq(AppletAlumniAssociationActivity::getAvatar, dto.getAvatar());
        }
        if (Utils.isNotNull(dto.getContent())) {
            wrapper.eq(AppletAlumniAssociationActivity::getContent, dto.getContent());
        }
        if (Utils.isNotNull(dto.getTime())) {
            wrapper.eq(AppletAlumniAssociationActivity::getTime, dto.getTime());
        }
        if (Utils.isNotNull(dto.getLocation())) {
            wrapper.eq(AppletAlumniAssociationActivity::getLocation, dto.getLocation());
        }
        if (Utils.isNotNull(dto.getStatus())) {
            wrapper.eq(AppletAlumniAssociationActivity::getStatus, dto.getStatus());
        }
        return wrapper;
    }
}