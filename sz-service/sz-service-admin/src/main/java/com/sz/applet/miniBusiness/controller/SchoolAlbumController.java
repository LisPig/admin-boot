package com.sz.applet.miniBusiness.controller;

import com.sz.applet.miniBusiness.pojo.vo.SchoolAlbumVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import com.sz.core.common.entity.ApiPageResult;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.applet.miniBusiness.service.SchoolAlbumService;
import com.sz.applet.miniBusiness.pojo.dto.SchoolAlbumCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolAlbumUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolAlbumListDTO;
import com.sz.core.common.entity.ImportExcelDTO;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * 相册表 Controller
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
@Tag(name =  "相册表")
@RestController
@RequestMapping("school-album")
@RequiredArgsConstructor
public class SchoolAlbumController  {

    private final SchoolAlbumService schoolAlbumService;

    @Operation(summary = "新增")
    @SaCheckPermission(value = "school.album.create")
    @PostMapping
    public ApiResult<Void> create(@RequestBody SchoolAlbumCreateDTO dto) {
        schoolAlbumService.create(dto);
        return ApiResult.success();
    }

    @Operation(summary = "修改")
    @SaCheckPermission(value = "school.album.update")
    @PutMapping
    public ApiResult<Void> update(@RequestBody SchoolAlbumUpdateDTO dto) {
        schoolAlbumService.update(dto);
        return ApiResult.success();
    }

    @Operation(summary = "删除")
    @SaCheckPermission(value = "school.album.remove")
    @DeleteMapping
    public ApiResult<Void> remove(@RequestBody SelectIdsDTO dto) {
        schoolAlbumService.remove(dto);
        return ApiResult.success();
    }

    @Operation(summary = "列表查询")
    @SaCheckPermission(value = "school.album.query_table")
    @GetMapping
    public ApiResult<PageResult<SchoolAlbumVO>> list(SchoolAlbumListDTO dto) {
        return ApiPageResult.success(schoolAlbumService.page(dto));
    }

    @Operation(summary = "列表查询-小程序")
    @GetMapping("/listByMini")
    public ApiResult<PageResult<SchoolAlbumVO>> listByMini(SchoolAlbumListDTO dto) {
        return ApiPageResult.success(schoolAlbumService.page(dto));
    }

    @Operation(summary = "详情")
    @SaCheckPermission(value = "school.album.query_table")
    @GetMapping("/{id}")
    public ApiResult<SchoolAlbumVO> detail(@PathVariable Object id) {
        return ApiResult.success(schoolAlbumService.detail(id));
    }

    @Operation(summary = "导入")
    @Parameters({
      @Parameter(name = "file", description = "上传文件", schema = @Schema(type = "string", format = "binary"), required = true),
    })
    @SaCheckPermission(value = "school.album.import")
    @PostMapping("/import")
    public void importExcel(@ModelAttribute ImportExcelDTO dto) {
        schoolAlbumService.importExcel(dto);
    }

    @Operation(summary = "导出")
    @SaCheckPermission(value = "school.album.export")
    @PostMapping("/export")
    public void exportExcel(@RequestBody SchoolAlbumListDTO dto, HttpServletResponse response) {
        schoolAlbumService.exportExcel(dto, response);
    }
}