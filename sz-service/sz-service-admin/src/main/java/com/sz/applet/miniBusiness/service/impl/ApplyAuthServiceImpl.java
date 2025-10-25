package com.sz.applet.miniBusiness.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
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
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.PageResult;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.PageUtils;
import com.sz.security.core.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.ApplyAuthService;
import com.sz.applet.miniBusiness.pojo.po.ApplyAuth;
import com.sz.applet.miniBusiness.mapper.ApplyAuthMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public void applyAuth(ApplyAuthBo bo) {
        bo.setUserId(Objects.requireNonNull(LoginUtils.getMiniLoginUser()).getUserId());
        // 判断是否已经有通过验证的相同申请表数据
        ApplyAuth applyAuth = this.getOne(new QueryWrapper()
                .eq(ApplyAuth::getPhone, bo.getPhone(), ObjectUtil.isNotNull(bo.getPhone()))
                .eq(ApplyAuth::getName, bo.getName(), ObjectUtil.isNotNull(bo.getName()))
                .eq(ApplyAuth::getIdCard, bo.getIdCard(), ObjectUtil.isNotNull(bo.getIdCard()))
                .eq(ApplyAuth::getIdentity, bo.getIdentity(), ObjectUtil.isNotNull(bo.getIdentity()))
                .eq(ApplyAuth::getStatus,2));
        if (ObjectUtil.isNotNull(applyAuth)) {
            SchoolUserBinding schoolUserBinding = new SchoolUserBinding();
            schoolUserBinding.setMiniUserId(LoginUtils.getMiniLoginUser().getUserId());
            schoolUserBinding.setSchoolUserId(applyAuth.getId());
            schoolUserBinding.setBindType(1);
            schoolUserBinding.setStatus(1);
            schoolUserBindingService.save(schoolUserBinding);
        } else {
            this.save(BeanCopyUtils.copy(bo, ApplyAuth.class));
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
        ApplyAuth applyAuth = new ApplyAuth();
        applyAuth.setId(bo.getId());
        applyAuth.setStatus(bo.getStatus());
        applyAuth.setApproveRemark(bo.getApproveRemark());
        applyAuth.setApproveTime(new Date());
        if (bo.getStatus().equals("2")) {
            MiniUser miniUser = new MiniUser();
            miniUser.setId(bo.getUserId());
            miniUser.setAuthStatus(1); // 认证通过
            miniUserService.updateById(miniUser);
            SchoolUserBinding schoolUserBinding = new SchoolUserBinding();
            schoolUserBinding.setSchoolUserId(bo.getId());
            schoolUserBinding.setBindType(1);
            schoolUserBinding.setMiniUserId(bo.getUserId());
            schoolUserBindingService.save(schoolUserBinding);
        }
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
                .eq(ApplyAuth::getUserId, miniUserId)
                .in(ApplyAuth::getStatus, 1));
        if(applyAuth != null){
            return BeanCopyUtils.copy(applyAuth, ApplyAuthVo.class);
        }
        return false;
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
}