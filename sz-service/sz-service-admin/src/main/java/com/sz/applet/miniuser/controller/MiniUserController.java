package com.sz.applet.miniuser.controller;

import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniuser.pojo.dto.MiniLoginDTO;
import com.sz.applet.miniuser.pojo.dto.MiniUserDTO;
import com.sz.applet.miniuser.pojo.dto.UpdateMiniUserInfoDTO;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.LoginUser;
import com.sz.core.common.entity.PageResult;
import com.sz.security.core.util.LoginUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 小程序用户表 Controller
 * </p>
 *
 * @author sz
 * @since 2024-04-26
 */
@Tag(name = "微信小程序用户")
@RestController
@RequestMapping("wechat/mini/user")
@RequiredArgsConstructor
public class MiniUserController {

    private final MiniUserService miniUserService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public ApiResult<MiniUserVO> login(@RequestBody MiniLoginDTO dto) {
        return ApiResult.success(miniUserService.doLogin(dto));
    }

    @Operation(summary = "检查认证状态")
    @GetMapping("/check/{openId}")
    public ApiResult<Boolean> check(@PathVariable String openId) {
        return ApiResult.success(miniUserService.checkAuthStatus(openId));
    }

    @Operation(summary = "检验是否需要完善资料")
    @GetMapping("/check/completeProfile")
    public ApiResult<Boolean> checkComplete() {
        String openId = Objects.requireNonNull(LoginUtils.getMiniLoginUser()).getOpenid();
        if(miniUserService.checkAuthStatus(openId)){
            if(miniUserService.exists(new QueryWrapper().eq(MiniUser::getOpenid,openId).eq(MiniUser::getProfilePromptShown,0))){
                return ApiResult.success(true);
            }
        }
        return ApiResult.success(false);
    }

    @Operation(summary = "更新完善资料状态")
    @GetMapping("/check/completeProfileStatus")
    public ApiResult<Boolean> checkCompleteStatus() {
        String openId = Objects.requireNonNull(LoginUtils.getMiniLoginUser()).getOpenid();
        if(miniUserService.checkAuthStatus(openId)){
            if(miniUserService.exists(new QueryWrapper().eq(MiniUser::getOpenid,openId).eq(MiniUser::getProfilePromptShown,0))){
                MiniUser miniUser = miniUserService.getOne(new QueryWrapper().eq(MiniUser::getOpenid,openId));
                miniUser.setProfilePromptShown(1);
                return ApiResult.success(miniUserService.updateById(miniUser));
            }
        }
        return ApiResult.success(false);
    }


    @Operation(summary = "小程序-修改微信用户信息")
    @PostMapping("/update")
    public ApiResult<Boolean> updateInfo(@RequestBody UpdateMiniUserInfoDTO dto) {
        return ApiResult.success(miniUserService.updateInfo(dto));
    }

    @Operation(summary = "小程序用户列表-PC")
    @GetMapping("/list")
    public ApiResult<PageResult<MiniUserVO>> list(@RequestBody MiniUserDTO dto) {
        return ApiResult.success(miniUserService.page(dto));
    }

}
