package com.sz.applet.miniBusiness.controller;

import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationListVO;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.sz.core.common.entity.ApiPageResult;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.constant.GlobalConstant;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationService;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationListDTO;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationVO;

import java.util.List;

/**
 * <p>
 * 校友会表 Controller
 * </p>
 *
 * @author LisPig
 * @since 2025-10-25
 */
@Tag(name =  "校友会表")
@Validated
@RestController
@RequestMapping("applet-alumni-association")
@RequiredArgsConstructor
public class AppletAlumniAssociationController  {

    private final AppletAlumniAssociationService appletAlumniAssociationService;



    @Operation(summary = "新增")
   // @SaCheckPermission(value = "applet.alumni.association.create")
    @PostMapping
    public ApiResult<String> create(@RequestBody AppletAlumniAssociationCreateDTO dto) {
        return ApiResult.success(appletAlumniAssociationService.create(dto));
    }

    @Operation(summary = "修改")
    @SaCheckPermission(value = "applet.alumni.association.update")
    @PutMapping
    public ApiResult<Void> update(@RequestBody AppletAlumniAssociationUpdateDTO dto) {
        appletAlumniAssociationService.update(dto);
        return ApiResult.success();
    }

    @Operation(summary = "删除")
    @SaCheckPermission(value = "applet.alumni.association.remove")
    @DeleteMapping
    public ApiResult<Void> remove(@RequestBody SelectIdsDTO dto) {
        appletAlumniAssociationService.remove(dto);
        return ApiResult.success();
    }


    @Operation(summary = "PC列表查询")
    @SaCheckPermission(value = "applet.alumni.association.query_table")
    @GetMapping
    public ApiResult<PageResult<AppletAlumniAssociationVO>> list(AppletAlumniAssociationListDTO dto) {
        return ApiPageResult.success(appletAlumniAssociationService.page(dto));
    }

    @Operation(summary = "详情")
    //@SaCheckPermission(value = "applet.alumni.association.query_table")
    @GetMapping("/{id}")
    public ApiResult<AppletAlumniAssociationVO> detail(@PathVariable Object id) {
        return ApiResult.success(appletAlumniAssociationService.detail(id));
    }

    @Operation(summary = "PC-审核")
    @PostMapping("/approve")
    public ApiResult<Void> approve(@RequestBody AppletAlumniAssociationUpdateDTO dto) {
        appletAlumniAssociationService.approve(dto);
        return ApiResult.success();
    }


    @Operation(summary = "小程序-列表查询")
   // @SaCheckPermission(value = "applet.alumni.association.query_table")
    @GetMapping("/miniList")
    public ApiResult<List<AppletAlumniAssociationListVO>> miniList(AppletAlumniAssociationListDTO dto) {
        return ApiResult.success(appletAlumniAssociationService.list(dto));
    }

    @Schema(description = "ID值传输对象")
    @Data
    public static class IdDTO {
        @Schema(description = "ID")
        @NotNull(message = "校友会ID不能为空")
        private Long associationId;
    }

    @Operation(summary = "小程序-申请参加入会")
    @PostMapping("/applyJoin")
    public ApiResult<Boolean> join(@Validated @RequestBody IdDTO dto) {
        return ApiResult.success(appletAlumniAssociationService.apply(dto.getAssociationId()));
    }

    @Operation(summary = "小程序-申请会长")
    @PostMapping("/applyPresident")
    public ApiResult<Boolean> applyPresident(@RequestBody IdDTO dto) {
        return ApiResult.success(appletAlumniAssociationService.applyPresident(dto.getAssociationId()));
    }

    @Operation(summary = "小程序-用户所加入的校会列表")
    @GetMapping("/userJoinList/{userId}")
    public ApiResult<List<AppletAlumniAssociationVO>> userJoinList(@PathVariable Long userId) {
        return ApiResult.success(appletAlumniAssociationService.userJoinList(userId));
    }

    @Operation(summary = "小程序-用户退出当前校友会")
    @PostMapping("/userQuit")
    public ApiResult<Boolean> userQuit(@RequestBody IdDTO dto) {
        return ApiResult.success(appletAlumniAssociationService.userQuit(dto.getAssociationId()));
    }
}