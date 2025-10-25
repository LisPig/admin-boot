package com.sz.applet.miniBusiness.controller;

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
import com.sz.applet.miniBusiness.service.ArchiveFilesService;
import com.sz.applet.miniBusiness.pojo.dto.ArchiveFilesCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.ArchiveFilesUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.ArchiveFilesListDTO;
import com.sz.applet.miniBusiness.pojo.vo.ArchiveFilesVO;

/**
 * <p>
 * 档案申请记录表 Controller
 * </p>
 *
 * @author LisPig
 * @since 2025-10-25
 */
@Tag(name =  "档案申请记录表")
@RestController
@RequestMapping("archive-files")
@RequiredArgsConstructor
public class ArchiveFilesController  {

    private final ArchiveFilesService archiveFilesService;

    @Operation(summary = "新增")
    @PostMapping
    public ApiResult<Void> create(@RequestBody ArchiveFilesCreateDTO dto) {
        archiveFilesService.create(dto);
        return ApiResult.success();
    }

    @Operation(summary = "修改")
    @SaCheckPermission(value = "archive.files.update")
    @PutMapping
    public ApiResult<Void> update(@RequestBody ArchiveFilesUpdateDTO dto) {
        archiveFilesService.update(dto);
        return ApiResult.success();
    }

    @Operation(summary = "删除")
    @SaCheckPermission(value = "archive.files.remove")
    @DeleteMapping
    public ApiResult<Void> remove(@RequestBody SelectIdsDTO dto) {
        archiveFilesService.remove(dto);
        return ApiResult.success();
    }

    @Operation(summary = "列表查询")
    @SaCheckPermission(value = "archive.files.query_table")
    @GetMapping
    public ApiResult<PageResult<ArchiveFilesVO>> list(ArchiveFilesListDTO dto) {
        return ApiPageResult.success(archiveFilesService.page(dto));
    }

    @Operation(summary = "详情")
    @SaCheckPermission(value = "archive.files.query_table")
    @GetMapping("/{id}")
    public ApiResult<ArchiveFilesVO> detail(@PathVariable Object id) {
        return ApiResult.success(archiveFilesService.detail(id));
    }
}