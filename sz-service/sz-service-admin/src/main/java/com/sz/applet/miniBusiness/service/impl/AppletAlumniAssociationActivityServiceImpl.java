package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.applet.miniBusiness.mapper.AppletAlumniAssociationMapper;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociation;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivityUser;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationUser;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityUserService;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationService;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationUserService;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.applet.miniuser.service.impl.SubscribeMessageService;
import com.sz.core.common.exception.common.BusinessException;
import com.sz.core.common.translate.Translate;
import com.sz.core.common.translate.TranslateUtil;
import com.sz.security.core.util.LoginUtils;
import com.sz.utils.MapstructUtils;
import com.sz.wechat.WechatProperties;
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
import org.springframework.transaction.annotation.Transactional;

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

    private final AppletAlumniAssociationUserService appletAlumniAssociationUserService;
    private final AppletAlumniAssociationMapper appletAlumniAssociationMapper;
    private final WechatProperties wechatProperties;
    private final SubscribeMessageService subscribeMessageService;
    private final MiniUserService miniUserService;
    private final AppletAlumniAssociationActivityUserService appletAlumniAssociationActivityUserService;
    private final TranslateUtil translateUtil;


    @Override
    public String create(AppletAlumniAssociationActivityCreateDTO dto){
        // 判断是否是会长，仅会长才能发布活动
        AppletAlumniAssociation appletAlumniAssociation = appletAlumniAssociationMapper.selectOneById(dto.getAlumniAssociationId());
        if(!appletAlumniAssociation.getCreateId().equals(LoginUtils.getMiniLoginUser().getUserId())){
            throw new BusinessException(CommonResponseEnum.NO_PERMISSION,null,"您不是会长，暂无发布活动权限");
        }
        AppletAlumniAssociationActivity appletAlumniAssociationActivity = BeanCopyUtils.copy(dto, AppletAlumniAssociationActivity.class);
        if(save(appletAlumniAssociationActivity)){
            return wechatProperties.getMini().getTemplateId("CHECK_RESULT");
        }
        return null;
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

        AppletAlumniAssociationActivityVO appletAlumniAssociationActivityVO = new AppletAlumniAssociationActivityVO();
        BeanCopyUtils.copy(appletAlumniAssociationActivity, appletAlumniAssociationActivityVO);
        if(appletAlumniAssociationActivityUserService.exists(new QueryWrapper()
                .eq(AppletAlumniAssociationActivityUser::getAlumniAssociationActivityId, id)
                .eq(AppletAlumniAssociationActivityUser::getUserId, LoginUtils.getMiniLoginUser().getUserId())
                .eq(AppletAlumniAssociationActivityUser::getStatus, "1"))){
            assert appletAlumniAssociationActivityVO != null;
            appletAlumniAssociationActivityVO.setIsJoin( true);
        } else {
            assert appletAlumniAssociationActivityVO != null;
            appletAlumniAssociationActivityVO.setIsJoin( false);
        }
        translateUtil.translate(appletAlumniAssociationActivityVO);
        return appletAlumniAssociationActivityVO;
    }

    @Override
    public Boolean approve(AppletAlumniAssociationActivityUpdateDTO dto) {
        AppletAlumniAssociationActivity appletAlumniAssociationActivity = this.getById(dto.getId());
        if (Utils.isNotNull(appletAlumniAssociationActivity)) {
            appletAlumniAssociationActivity.setStatus(dto.getStatus());
            updateById(appletAlumniAssociationActivity);
            MiniUser miniUser = miniUserService.getOne(new QueryWrapper().eq(MiniUser::getId, appletAlumniAssociationActivity.getCreateId()));
            if(miniUser != null && miniUser.getOpenid() != null) {
                subscribeMessageService.sendAssociationActivityMsgForUser(miniUser.getOpenid(), appletAlumniAssociationActivity);
            }
            return true;
        }
        return false;
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
        wrapper.orderBy(AppletAlumniAssociationActivity::getCreateTime, false);
        return wrapper;
    }
}