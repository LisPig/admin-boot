package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationUser;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationActivityVO;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationListVO;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityService;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationUserService;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.applet.miniuser.service.impl.SubscribeMessageService;
import com.sz.core.common.exception.common.BusinessException;
import com.sz.core.common.translate.TranslateUtil;
import com.sz.security.core.util.LoginUtils;
import com.sz.wechat.WechatProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociation;
import com.sz.applet.miniBusiness.mapper.AppletAlumniAssociationMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.util.PageUtils;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.Utils;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private final TranslateUtil translateUtil;

    private final SubscribeMessageService subscribeMessageService;

    private final MiniUserService miniUserService;

    private final WechatProperties wechatProperties;

    @Override
    public String create(AppletAlumniAssociationCreateDTO dto){
        if(this.exists(new QueryWrapper().eq(AppletAlumniAssociation::getName, dto.getName()))){
            throw new BusinessException(CommonResponseEnum.EXISTS,null,"校友会名称已存在");
        }
        AppletAlumniAssociation appletAlumniAssociation = BeanCopyUtils.copy(dto, AppletAlumniAssociation.class);
        appletAlumniAssociation.setStatus("0");
        String templateId = wechatProperties.getMini().getTemplateId("CHECK_RESULT");
        if(this.save(appletAlumniAssociation)){
            return templateId;
        }
        return null;
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
    public Boolean approve(AppletAlumniAssociationUpdateDTO dto) {
        AppletAlumniAssociation appletAlumniAssociationUpdate = BeanCopyUtils.copy(dto, AppletAlumniAssociation.class);
        QueryWrapper wrapper;
        // id有效性校验
        wrapper = QueryWrapper.create()
                .eq(AppletAlumniAssociation::getId, dto.getId());
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);
        AppletAlumniAssociation appletAlumniAssociation = getById(dto.getId());
        MiniUser miniUser = miniUserService.getOne(new QueryWrapper().eq(MiniUser::getId, appletAlumniAssociation.getCreateId()));
        if(miniUser != null && miniUser.getOpenid() != null) {
            appletAlumniAssociationUpdate.setCreateTime(appletAlumniAssociation.getCreateTime());
            subscribeMessageService.sendApproveAssociationMsg(miniUser.getOpenid(), appletAlumniAssociationUpdate);
        }
        if(saveOrUpdate(appletAlumniAssociationUpdate)){
            if(dto.getStatus().equals("1")){ // 通过状态自动添加创建者为会长
                AppletAlumniAssociationUser appletAlumniAssociationUser = new AppletAlumniAssociationUser();
                appletAlumniAssociationUser.setUserId(appletAlumniAssociation.getCreateId());
                appletAlumniAssociationUser.setAlumniAssociationId(appletAlumniAssociation.getId());
                appletAlumniAssociationUser.setIdentity("2");
                appletAlumniAssociationUser.setStatus("1");
                appletAlumniAssociationUser.setCreateId(appletAlumniAssociation.getCreateId());
                appletAlumniAssociationUserService.save(appletAlumniAssociationUser);
            }
        }
        return saveOrUpdate(appletAlumniAssociationUpdate);
    }

    @Override
    public PageResult<AppletAlumniAssociationVO> page(AppletAlumniAssociationListDTO dto){
        Page<AppletAlumniAssociationVO> page = pageAs(PageUtils.getPage(dto), buildQueryWrapper(dto), AppletAlumniAssociationVO.class);
        translateUtil.translate(page.getRecords());
        return PageUtils.getPageResult(page);
    }

    @Override
    public List<AppletAlumniAssociationListVO> list(AppletAlumniAssociationListDTO dto){
        List<AppletAlumniAssociationListVO> list = listAs(buildQueryWrapper(dto), AppletAlumniAssociationListVO.class);
        for(AppletAlumniAssociationListVO appletAlumniAssociationVO: list){
            // 查找当前用户是否已是会员
            appletAlumniAssociationVO.setIsMember(isMember(appletAlumniAssociationVO.getId()));
        }
        translateUtil.translate(list);
        return list;
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
        appletAlumniAssociationVO.setIsMember(isMember(appletAlumniAssociationVO.getId()));
        translateUtil.translate(appletAlumniAssociationVO);
        return appletAlumniAssociationVO;
    }

    @Override
    public Boolean apply(Long associationId) {
        AppletAlumniAssociationUser appletAlumniAssociationUser = new AppletAlumniAssociationUser();
        MiniUser miniUser = miniUserService.getOne(new QueryWrapper().eq(MiniUser::getId, Objects.requireNonNull(LoginUtils.getMiniLoginUser()).getUserId()));
        if(miniUser.getAuthStatus() != 1){
            throw new BusinessException(CommonResponseEnum.FAILURE,null, "请先完成校友认证");
        }
        appletAlumniAssociationUser.setUserId(LoginUtils.getMiniLoginUser().getUserId());
        appletAlumniAssociationUser.setAlumniAssociationId(associationId);
        appletAlumniAssociationUser.setIdentity("1");
        return appletAlumniAssociationUserService.save(appletAlumniAssociationUser);
    }

    @Override
    public Boolean applyPresident(Long associationId) {
        // 先检查当前用户是否是会员
        AppletAlumniAssociationUser appletAlumniAssociationUser = appletAlumniAssociationUserService.getOne(
                new QueryWrapper().eq(AppletAlumniAssociationUser::getUserId, LoginUtils.getMiniLoginUser().getUserId())
                        .eq(AppletAlumniAssociationUser::getIdentity,"0")
                        .eq(AppletAlumniAssociationUser::getAlumniAssociationId, associationId));
        if(appletAlumniAssociationUser == null){
            throw new RuntimeException("请先申请加入该校友会");
        }
        appletAlumniAssociationUser.setIdentity("1");
        return appletAlumniAssociationUserService.updateById(appletAlumniAssociationUser);
    }

    @Override
    public List<AppletAlumniAssociationVO> userJoinList(Long userId) {
        List<AppletAlumniAssociationUser> appletAlumniAssociationUsers = appletAlumniAssociationUserService.list(
                new QueryWrapper().eq(AppletAlumniAssociationUser::getUserId, userId)
                        .eq(AppletAlumniAssociationUser::getStatus,1)
        );
        if(!appletAlumniAssociationUsers.isEmpty()){
            List<AppletAlumniAssociationVO> list = listAs(new QueryWrapper()
                    .in(AppletAlumniAssociation::getId,appletAlumniAssociationUsers.stream().map(AppletAlumniAssociationUser::getAlumniAssociationId).collect(Collectors.toList()))
                    .eq(AppletAlumniAssociation::getStatus,1), AppletAlumniAssociationVO.class);
            translateUtil.translate( list);
            return list;
        }
        return null;
    }

    @Override
    public Boolean userQuit(Long associationId) {
        AppletAlumniAssociationUser appletAlumniAssociationUser = appletAlumniAssociationUserService.getOne(
                new QueryWrapper().eq(AppletAlumniAssociationUser::getUserId, LoginUtils.getMiniLoginUser().getUserId())
                        .eq(AppletAlumniAssociationUser::getAlumniAssociationId, associationId));
        if(appletAlumniAssociationUser != null){
            return appletAlumniAssociationUserService.removeById(appletAlumniAssociationUser.getId());
        }
        throw new RuntimeException("请先加入该校友会");
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
        wrapper.orderBy(AppletAlumniAssociation::getCreateTime, false);
        return wrapper;
    }

    private Boolean isMember(Long associationId) {
        AppletAlumniAssociationUser appletAlumniAssociationUser = appletAlumniAssociationUserService.getOne(
                new QueryWrapper().eq(AppletAlumniAssociationUser::getUserId, LoginUtils.getMiniLoginUser().getUserId())
                        .eq(AppletAlumniAssociationUser::getAlumniAssociationId, associationId));
        return appletAlumniAssociationUser != null;
    }
}