package com.sz.applet.miniuser.controller;

import com.sz.applet.miniuser.pojo.dto.MiniLoginDTO;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.core.common.entity.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

}
