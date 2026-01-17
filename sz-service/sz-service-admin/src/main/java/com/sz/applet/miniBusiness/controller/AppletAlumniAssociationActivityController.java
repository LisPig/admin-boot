package com.sz.applet.miniBusiness.controller;

import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import com.sz.core.common.entity.ApiPageResult;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.constant.GlobalConstant;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityService;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniAssociationActivityListDTO;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniAssociationActivityVO;

/**
 * <p>
 * 校友会活动表 Controller
 * </p>
 *
 * @author sz-admin
 * @since 2025-10-25
 */
@Tag(name =  "校友会活动表")
@RestController
@RequestMapping("applet-alumni-association-activity")
@RequiredArgsConstructor
public class AppletAlumniAssociationActivityController  {

    private final AppletAlumniAssociationActivityService appletAlumniAssociationActivityService;
    private final AppletAlumniAssociationActivityUserService appletAlumniAssociationActivityUserService;

    @Operation(summary = "新增")
    //@SaCheckPermission(value = "applet.alumni.association.activity.create")
    @PostMapping
    public ApiResult<String> create(@RequestBody AppletAlumniAssociationActivityCreateDTO dto) {
        appletAlumniAssociationActivityService.create(dto);
        return ApiResult.success();
    }

    @Operation(summary = "修改")
    @SaCheckPermission(value = "applet.alumni.association.activity.update")
    @PutMapping
    public ApiResult<Void> update(@RequestBody AppletAlumniAssociationActivityUpdateDTO dto) {
        appletAlumniAssociationActivityService.update(dto);
        return ApiResult.success();
    }

    @Operation(summary = "删除")
    @SaCheckPermission(value = "applet.alumni.association.activity.remove")
    @DeleteMapping
    public ApiResult<Void> remove(@RequestBody SelectIdsDTO dto) {
        appletAlumniAssociationActivityService.remove(dto);
        return ApiResult.success();
    }

    @Operation(summary = "列表查询")
    @SaCheckPermission(value = "applet.alumni.association.activity.query_table")
    @GetMapping
    public ApiResult<PageResult<AppletAlumniAssociationActivityVO>> list(AppletAlumniAssociationActivityListDTO dto) {
        return ApiPageResult.success(appletAlumniAssociationActivityService.page(dto));
    }

    @Operation(summary = "详情")
   // @SaCheckPermission(value = "applet.alumni.association.activity.query_table")
    @GetMapping("/{id}")
    public ApiResult<AppletAlumniAssociationActivityVO> detail(@PathVariable Object id) {
        return ApiResult.success(appletAlumniAssociationActivityService.detail(id));
    }

    @Operation(summary = "当前校会下的活动列表")
    @GetMapping("/listByAssociationId")
    public ApiResult<PageResult<AppletAlumniAssociationActivityVO>> listByAssociationId(@Schema(description = "当前用户所在校友会ID") @RequestParam Long associationId) {
        return ApiPageResult.success(appletAlumniAssociationActivityService.page(new AppletAlumniAssociationActivityListDTO().setAlumniAssociationId(associationId).setStatus("1")));
    }


    @Operation(summary = "活动审批")
    @SaCheckPermission(value = "applet.alumni.association.activity.approve")
    @PostMapping("/approve")
    public ApiResult<Boolean> approve(@RequestBody AppletAlumniAssociationActivityUpdateDTO dto) {
        return ApiResult.success(appletAlumniAssociationActivityService.approve(dto));
    }

    @Operation(summary = "活动报名")
    @GetMapping("/apply/{id}")
    public ApiResult<Boolean> apply(@PathVariable Long id) {
        return ApiResult.success(appletAlumniAssociationActivityUserService.apply(id));
    }

    //@Operation(summary = "判断是否")
}