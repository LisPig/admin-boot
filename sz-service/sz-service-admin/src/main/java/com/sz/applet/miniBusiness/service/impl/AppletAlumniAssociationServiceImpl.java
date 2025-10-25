package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivity;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationUser;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationActivityVO;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityService;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociation;
import com.sz.applet.miniBusiness.mapper.AppletAlumniAssociationMapper;
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
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationListDTO;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationVO;

/**
 * <p>
 * 校友会表 服务实现类
 * </p>
 *
 * @author LisPig
 * @since 2025-10-25
 */
@Service
@RequiredArgsConstructor
public class AppletAlumniAssociationServiceImpl extends ServiceImpl<AppletAlumniAssociationMapper, AppletAlumniAssociation> implements AppletAlumniAssociationService {

    private final AppletAlumniAssociationUserService appletAlumniAssociationUserService;

    private final AppletAlumniAssociationActivityService appletAlumniAssociationActivityService;

    @Override
    public void create(AppletAlumniAssociationCreateDTO dto){
        AppletAlumniAssociation appletAlumniAssociation = BeanCopyUtils.copy(dto, AppletAlumniAssociation.class);
        save(appletAlumniAssociation);
    }

    @Override
    public void update(AppletAlumniAssociationUpdateDTO dto){
        AppletAlumniAssociation appletAlumniAssociation = BeanCopyUtils.copy(dto, AppletAlumniAssociation.class);
        QueryWrapper wrapper;
        // id有效性校验
        wrapper = QueryWrapper.create()
            .eq(AppletAlumniAssociation::getId, dto.getId());
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);

        saveOrUpdate(appletAlumniAssociation);
    }

    @Override
    public PageResult<AppletAlumniAssociationVO> page(AppletAlumniAssociationListDTO dto){
        Page<AppletAlumniAssociationVO> page = pageAs(PageUtils.getPage(dto), buildQueryWrapper(dto), AppletAlumniAssociationVO.class);
        return PageUtils.getPageResult(page);
    }

    @Override
    public List<AppletAlumniAssociationVO> list(AppletAlumniAssociationListDTO dto){
        List<AppletAlumniAssociationVO> list = listAs(buildQueryWrapper(dto), AppletAlumniAssociationVO.class);
        for(AppletAlumniAssociationVO vo:list){
            //根据校友会id获取会员人数
            Long number = appletAlumniAssociationUserService.count(new QueryWrapper()
                    .eq(AppletAlumniAssociationUser::getAlumniAssociationId,vo.getId())
                    .eq(AppletAlumniAssociationUser::getStatus,1));
            vo.setNumber(number);

            Long activityCount = appletAlumniAssociationActivityService.count(new QueryWrapper()
                    .eq(AppletAlumniAssociationActivity::getAlumniAssociationId,vo.getId())
                    .eq(AppletAlumniAssociationActivity::getStatus,1));
            vo.setActivityNumber(activityCount);
        }
        return listAs(buildQueryWrapper(dto), AppletAlumniAssociationVO.class);
    }

    @Override
    public void remove(SelectIdsDTO dto){
        CommonResponseEnum.INVALID_ID.assertTrue(dto.getIds().isEmpty());
        removeByIds(dto.getIds());
    }

    @Override
    public AppletAlumniAssociationVO detail(Object id){
        AppletAlumniAssociation appletAlumniAssociation = getById((Serializable) id);
        CommonResponseEnum.INVALID_ID.assertNull(appletAlumniAssociation);

        List<AppletAlumniAssociationActivityVO> appletAlumniAssociationActivityList = appletAlumniAssociationActivityService.listAs(new QueryWrapper()
                .eq("alumni_association_id", id), AppletAlumniAssociationActivityVO.class);
        BeanCopyUtils.copy(appletAlumniAssociationActivityList, AppletAlumniAssociationActivityVO.class);

        AppletAlumniAssociationVO appletAlumniAssociationVO = new AppletAlumniAssociationVO();
        BeanCopyUtils.copy(appletAlumniAssociation, appletAlumniAssociationVO);
        appletAlumniAssociationVO.setActivityList(appletAlumniAssociationActivityList);
        return BeanCopyUtils.copy(appletAlumniAssociation, AppletAlumniAssociationVO.class);
    }

    private static QueryWrapper buildQueryWrapper(AppletAlumniAssociationListDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create().from(AppletAlumniAssociation.class);
        if (Utils.isNotNull(dto.getName())) {
            wrapper.like(AppletAlumniAssociation::getName, dto.getName());
        }
        if (Utils.isNotNull(dto.getAvatar())) {
            wrapper.eq(AppletAlumniAssociation::getAvatar, dto.getAvatar());
        }
        if (Utils.isNotNull(dto.getDescription())) {
            wrapper.eq(AppletAlumniAssociation::getDescription, dto.getDescription());
        }
        if (Utils.isNotNull(dto.getContract())) {
            wrapper.eq(AppletAlumniAssociation::getContract, dto.getContract());
        }
        if (Utils.isNotNull(dto.getPhone())) {
            wrapper.eq(AppletAlumniAssociation::getPhone, dto.getPhone());
        }
        if (Utils.isNotNull(dto.getStatus())) {
            wrapper.eq(AppletAlumniAssociation::getStatus, dto.getStatus());
        }
        return wrapper;
    }
}