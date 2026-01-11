package com.sz.applet.miniBusiness.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.sz.admin.system.pojo.po.SysUser;
import com.sz.admin.system.service.SysUserService;
import com.sz.applet.miniBusiness.pojo.bo.ApplyAuthBo;
import com.sz.applet.miniBusiness.pojo.bo.ApplyAuthListBo;
import com.sz.applet.miniBusiness.pojo.bo.SchoolUserBindingUpdateBo;
import com.sz.applet.miniBusiness.pojo.po.SchoolUserBinding;
import com.sz.applet.miniBusiness.pojo.vo.ApplyAuthVo;
import com.sz.applet.miniBusiness.pojo.vo.ApplyAutoDetailVo;
import com.sz.applet.miniBusiness.service.MiniUserAuthService;
import com.sz.applet.miniBusiness.service.SchoolUserBindingService;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.applet.miniuser.service.impl.SubscribeMessageService;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.PageResult;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.PageUtils;
import com.sz.security.core.util.LoginUtils;
import com.sz.wechat.WechatProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.ApplyAuthService;
import com.sz.applet.miniBusiness.pojo.po.ApplyAuth;
import com.sz.applet.miniBusiness.mapper.ApplyAuthMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.sz.applet.miniuser.pojo.po.table.MiniUserTableDef.MINI_USER;

/**
 * 校友申请认证表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ApplyAuthServiceImpl extends ServiceImpl<ApplyAuthMapper, ApplyAuth> implements ApplyAuthService {


    private final MiniUserService miniUserService;
    private final SchoolUserBindingService schoolUserBindingService;
    private final SysUserService sysUserService;
    private final SubscribeMessageService subscribeMessageService;
    private final WechatProperties wechatProperties;


    @Override
    public String applyAuth(ApplyAuthBo bo) {
        bo.setUserId(Objects.requireNonNull(LoginUtils.getMiniLoginUser()).getUserId());
        // 检查是否存在完全相同的申请（包括未通过的）
        ApplyAuth existingApply = this.getOne(new QueryWrapper()
                //.eq(ApplyAuth::getIdentity, bo.getIdentity(), ObjectUtil.isNotNull(bo.getIdentity()))
                .eq(ApplyAuth::getUserId, bo.getUserId())); // 包含待审核、已拒绝、已通过状态
        String templateId = wechatProperties.getMini().getTemplateId("CHECK_RESULT");
        if (ObjectUtil.isNotNull(existingApply)) {
            // 根据现有申请状态做不同处理
            if (existingApply.getStatus().equals("2")) {
                // 已通过的情况，创建绑定
                SchoolUserBinding schoolUserBinding = new SchoolUserBinding();
                schoolUserBinding.setMiniUserId(LoginUtils.getMiniLoginUser().getUserId());
                schoolUserBinding.setSchoolUserId(existingApply.getId());
                schoolUserBinding.setBindType(1);
                schoolUserBinding.setStatus(1);
                schoolUserBindingService.save(schoolUserBinding);

                return templateId;
            } else {
                // 其他状态，更新申请信息而非创建新记录
                ApplyAuth updateApply = BeanCopyUtils.copy(bo, ApplyAuth.class);
                updateApply.setId(existingApply.getId());
                updateApply.setStatus("1"); // 重置为待审核状态
                updateApply.setCreateTime(LocalDateTime.now());
                this.updateById(updateApply);
                return templateId;
            }
        } else {
            //sendMsgForAssessor(BeanCopyUtils.copy(bo, ApplyAuth.class));
           this.save(BeanCopyUtils.copy(bo, ApplyAuth.class));
           return templateId;
        }
    }

    @Override
    public PageResult<ApplyAuthVo> page(ApplyAuthListBo bo) {
        PageResult<ApplyAuthVo> pageResult = PageUtils.getPageResult(pageAs(PageUtils.getPage(bo), buildQueryWrapper(bo), ApplyAuthVo.class));
        return pageResult;
    }

    @Override
    @Transactional
    public Boolean review(ApplyAuthBo bo) {
        ApplyAuth applyAuth = this.getOne(new QueryWrapper().eq(ApplyAuth::getId, bo.getId()));
        applyAuth.setStatus(bo.getStatus());
        applyAuth.setApproveRemark(bo.getApproveRemark());
        applyAuth.setApproveTime(new Date());
        MiniUser miniUser = miniUserService.getOne(new QueryWrapper()
                .select(MINI_USER.ID, MINI_USER.OPENID, MINI_USER.UNIONID)
                .eq(MiniUser::getId, bo.getUserId()));
        if (bo.getStatus().equals("2")) {
            miniUser.setId(bo.getUserId());
            miniUser.setAuthStatus(1); // 认证通过
            miniUser.setUsername(bo.getName());
            miniUserService.updateById(miniUser);
            SchoolUserBinding schoolUserBinding = new SchoolUserBinding();
            schoolUserBinding.setSchoolUserId(bo.getId());
            schoolUserBinding.setBindType(1);
            schoolUserBinding.setMiniUserId(bo.getUserId());
            schoolUserBindingService.save(schoolUserBinding);
        }
        subscribeMessageService.sendApplyAuthMsgForUser(miniUser.getOpenid(), applyAuth);
        return this.updateById(applyAuth);
    }

    @Override
    public ApplyAutoDetailVo detail(Long id) {
        ApplyAutoDetailVo applyAutoDetailVo = new ApplyAutoDetailVo();
        ApplyAuth applyAuth = this.getById(id);
        applyAutoDetailVo.setApplyAuth(BeanCopyUtils.copy(applyAuth, ApplyAuthVo.class));
        applyAutoDetailVo.setMiniUser(BeanCopyUtils.copy(miniUserService.getById(applyAuth.getUserId()), MiniUserVO.class));
        return applyAutoDetailVo;
    }

    @Override
    public Object checkIsPassAuth() {
        Long miniUserId = Objects.requireNonNull(LoginUtils.getMiniLoginUser()).getUserId();
        ApplyAuth applyAuth = this.getOne(new QueryWrapper()
                .eq(ApplyAuth::getUserId, miniUserId));
                //.in(ApplyAuth::getStatus, 1,3));
        if(applyAuth != null){
            return BeanCopyUtils.copy(applyAuth, ApplyAuthVo.class);
        }
        return null;
    }

    @Override
    public MiniUserVO getUserInfo(String openId, String unionid) {
        QueryWrapper wrapper = QueryWrapper.create().where(MINI_USER.OPENID.eq(openId));
        MiniUser miniUser = miniUserService.getOne(wrapper);
        if (miniUser == null) {
            // 创建新的微信用户信息
            miniUser = new MiniUser();
            miniUser.setOpenid(openId);
            miniUser.setUnionid(unionid);
            miniUserService.save(miniUser);
        }else{
            SchoolUserBinding schoolUserBinding = schoolUserBindingService.getOne(new QueryWrapper()
                    .eq(SchoolUserBinding::getMiniUserId, miniUser.getId()));
            if(ObjectUtil.isNotNull(schoolUserBinding)) {
                ApplyAuth applyAuth = this.getOne(new QueryWrapper()
                        .eq(ApplyAuth::getId, schoolUserBinding.getSchoolUserId()));
                miniUser.setPhone(applyAuth.getPhone());
                miniUser.setUsername(applyAuth.getName());
            }
        }
        return BeanCopyUtils.copy(miniUser, MiniUserVO.class);
    }


    private QueryWrapper buildQueryWrapper(ApplyAuthListBo bo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper
                .eq(ApplyAuth::getIdentity, bo.getIdentity(), ObjectUtil.isNotNull(bo.getIdentity()))
                .eq(ApplyAuth::getIdCard, bo.getIdCard(), ObjectUtil.isNotNull(bo.getIdCard()))
                .eq(ApplyAuth::getStudentId, bo.getStudentId(), ObjectUtil.isNotNull(bo.getStudentId()))
                .eq(ApplyAuth::getYear, bo.getYear(), ObjectUtil.isNotNull(bo.getYear()))
                .eq(ApplyAuth::getClassNo, bo.getClassNo(), ObjectUtil.isNotNull(bo.getClassNo()))
                .eq(ApplyAuth::getTeacherId, bo.getTeacherId(), ObjectUtil.isNotNull(bo.getTeacherId()))
                .eq(ApplyAuth::getStatus, bo.getStatus(), ObjectUtil.isNotNull(bo.getStatus()));
        return queryWrapper;
    }

    @Async
    public void sendMsgForAssessor(ApplyAuth applyAuth){
        List<SysUser> sysUserList = sysUserService.getUserByRole("assessor");
        if(!sysUserList.isEmpty()){
            sysUserList.forEach(sysUser -> {
                if(sysUser.getMiniUserId() != null){
                    MiniUser miniUser = miniUserService.getOne(new QueryWrapper().eq(MiniUser::getId, sysUser.getMiniUserId()));
                    if(miniUser != null){
                        subscribeMessageService.sendApplyAuthMsg(miniUser.getOpenid(),applyAuth);
                    }
                }
            });
        }

    }
}