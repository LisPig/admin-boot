package com.sz.applet.miniBusiness.controller;

import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityService;
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
@RestController
@RequestMapping("applet-alumni-association")
@RequiredArgsConstructor
public class AppletAlumniAssociationController  {

    private final AppletAlumniAssociationService appletAlumniAssociationService;



    @Operation(summary = "新增")
    @SaCheckPermission(value = "applet.alumni.association.create")
    @PostMapping
    public ApiResult<Void> create(@RequestBody AppletAlumniAssociationCreateDTO dto) {
        appletAlumniAssociationService.create(dto);
        return ApiResult.success();
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

    @Operation(summary = "列表查询")
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


    @Operation(summary = "小程序-列表查询")
   // @SaCheckPermission(value = "applet.alumni.association.query_table")
    @GetMapping("/miniList")
    public ApiResult<List<AppletAlumniAssociationVO>> miniList(AppletAlumniAssociationListDTO dto) {
        return ApiResult.success(appletAlumniAssociationService.list(dto));
    }

    /*@Operation(summary = "小程序-申请参加入会")
    @PostMapping("/join")
    public ApiResult<String> join(@RequestBody Long associationId) {

    }*/
}