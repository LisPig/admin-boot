package com.sz.applet.miniBusiness.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
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
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.MiniLoginUserDTO;
import com.sz.core.common.entity.PageResult;
import com.sz.core.util.BeanCopyUtils;
import com.sz.security.core.util.LoginUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public ApiResult<Boolean> applyAuth(@RequestBody ApplyAuthBo bo) {
        /*SaSession session = StpUtil.getTokenSession();
        com.sz.applet.miniuser.pojo.po.MiniLoginUser loginUser = null;
        if (session != null) {
            Object obj = session.get(LoginUtils.USER_KEY);
            if (obj instanceof com.sz.applet.miniuser.pojo.po.MiniLoginUser) {
                loginUser = (com.sz.applet.miniuser.pojo.po.MiniLoginUser) obj;
            }
        }

        if (loginUser == null) {
            throw new RuntimeException("未找到登录用户信息");
        }*/

        bo.setUserId(Objects.requireNonNull(LoginUtils.getMiniLoginUser()).getUserId());
        // 先校验认证的资料是否已存在 已存在则不需要重新认证，直接通过认证，并更新绑定关系

        return ApiResult.success(applyAuthService.save(BeanCopyUtils.copy(bo, ApplyAuth.class)));
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
     * 检查是否已认证
     */
    @Operation(summary = "检查是否已认证")
    @GetMapping("/checkIsPassAuth")
    public ApiResult<Object> checkAuth() {

        return ApiResult.success(applyAuthService.checkIsPassAuth());
    }
}