package com.sz.applet.miniBusiness.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.sz.admin.system.pojo.po.SysUser;
import com.sz.admin.system.service.SysUserService;
import com.sz.applet.miniBusiness.pojo.bo.AppletAlumniPassApplicationBo;
import com.sz.applet.miniBusiness.pojo.bo.ApplyAuthBo;
import com.sz.applet.miniBusiness.pojo.bo.ApplyAuthListBo;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniPassApplication;
import com.sz.applet.miniBusiness.pojo.po.ApplyAuth;
import com.sz.applet.miniBusiness.pojo.po.SchoolUser;
import com.sz.applet.miniBusiness.pojo.vo.ApplyAuthVo;
import com.sz.applet.miniBusiness.pojo.vo.ApplyAutoDetailVo;
import com.sz.applet.miniBusiness.service.ApplyAuthService;
import com.sz.applet.miniBusiness.service.MiniUserAuthService;
import com.sz.applet.miniBusiness.service.AppletAlumniPassApplicationService;
import com.sz.applet.miniuser.pojo.dto.UpdateMiniUserInfoDTO;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.applet.miniuser.service.impl.SubscribeMessageService;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.MiniLoginUserDTO;
import com.sz.core.common.entity.PageResult;
import com.sz.core.util.BeanCopyUtils;
import com.sz.security.core.util.LoginUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 小程序用户认证管理
 */
@Tag(name = "小程序用户认证管理")
@RestController
@RequestMapping("/miniUser/auth")
@RequiredArgsConstructor
public class MiniUserAuthController {

    private final MiniUserAuthService adminAuthService;
    private final ApplyAuthService applyAuthService;


    /**
     * 申请认证
     */
    @Operation(summary = "申请校友认证")
    @PostMapping("/apply")
    public ApiResult<String> applyAuth(@RequestBody ApplyAuthBo bo) {
        return ApiResult.success(applyAuthService.applyAuth( bo));
    }

    
    /**
     * 获取认证申请列表（分页）
     */
    @Operation(summary = "获取认证申请列表（分页）")
    @SaCheckPermission("miniUser:auth:applications")
    @GetMapping("/applications")
    public ApiResult<PageResult<ApplyAuthVo>> getAuthApplications(ApplyAuthListBo  bo) {

        return ApiResult.success(applyAuthService.page( bo));
    }

    @Operation(summary = "查看认证详情")
    @GetMapping("/detail/{id}")
    public ApiResult<ApplyAutoDetailVo> detail(@PathVariable Long id) {
        return ApiResult.success(applyAuthService.detail(id));
    }

    /**
     * 审核认证申请
     */
    @Operation(summary = "审核认证申请")
    @SaCheckPermission("miniUser:auth:review")
    @PostMapping("/review")
    public ApiResult< Boolean> reviewAuthApplication(@RequestBody ApplyAuthBo  bo) {
        return ApiResult.success(applyAuthService.review(bo));
    }

    /**
     * 批量审核认证申请
     */
    @Operation(summary = "批量审核认证申请")
    @SaCheckPermission("miniUser:auth:review")
    @PostMapping("/batch-review")
    public ApiResult<Void> batchReviewAuthApplication(@RequestBody ApplyAuthBo bo) {
        applyAuthService.batchReview(bo.getIds(), bo.getStatus(), bo.getApproveRemark());
        return ApiResult.success();
    }

    /**
     * 检查是否已认证
     */
    @Operation(summary = "检查是否已认证")
    @GetMapping("/checkIsPassAuth")
    public ApiResult<Object> checkAuth() {

        return ApiResult.success(applyAuthService.checkIsPassAuth());
    }


    @Operation(summary = "获取用户信息")
    @GetMapping("/info/{openId}")
    public ApiResult<MiniUserVO> info(@PathVariable String openId) {
        MiniUserVO user = applyAuthService.getUserInfo(openId, null);
        return ApiResult.success(user);
    }

}