package com.sz.applet.miniBusiness.controller;

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
import com.sz.core.common.constant.GlobalConstant;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.applet.miniBusiness.service.SchoolMasterService;
import com.sz.applet.miniBusiness.pojo.dto.SchoolMasterCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolMasterUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolMasterListDTO;
import com.sz.applet.miniBusiness.pojo.vo.SchoolMasterVO;
import com.sz.core.common.entity.ImportExcelDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * <p>
 * 校长表 Controller
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
@Tag(name =  "校长表")
@RestController
@RequestMapping("school-master")
@RequiredArgsConstructor
public class SchoolMasterController  {

    private final SchoolMasterService schoolMasterService;

    @Operation(summary = "新增")
    @SaCheckPermission(value = "school.master.create")
    @PostMapping
    public ApiResult<Void> create(@RequestBody SchoolMasterCreateDTO dto) {
        schoolMasterService.create(dto);
        return ApiResult.success();
    }

    @Operation(summary = "修改")
    @SaCheckPermission(value = "school.master.update")
    @PutMapping
    public ApiResult<Void> update(@RequestBody SchoolMasterUpdateDTO dto) {
        schoolMasterService.update(dto);
        return ApiResult.success();
    }

    @Operation(summary = "删除")
    @SaCheckPermission(value = "school.master.remove")
    @DeleteMapping
    public ApiResult<Void> remove(@RequestBody SelectIdsDTO dto) {
        schoolMasterService.remove(dto);
        return ApiResult.success();
    }

    @Operation(summary = "列表查询")
    @SaCheckPermission(value = "school.master.query_table")
    @GetMapping
    public ApiResult<PageResult<SchoolMasterVO>> list(SchoolMasterListDTO dto) {
        return ApiPageResult.success(schoolMasterService.page(dto));
    }

    @Operation(summary = "小程序-列表查询")
    @GetMapping("/miniList")
    public ApiResult<List<SchoolMasterVO>> miniList(SchoolMasterListDTO dto) {
        return ApiResult.success(schoolMasterService.list(dto));
    }

    @Operation(summary = "详情")
    @SaCheckPermission(value = "school.master.query_table")
    @GetMapping("/{id}")
    public ApiResult<SchoolMasterVO> detail(@PathVariable Object id) {
        return ApiResult.success(schoolMasterService.detail(id));
    }

    @Operation(summary = "导入")
    @Parameters({
      @Parameter(name = "file", description = "上传文件", schema = @Schema(type = "string", format = "binary"), required = true),
    })
    @SaCheckPermission(value = "school.master.import")
    @PostMapping("/import")
    public void importExcel(@ModelAttribute ImportExcelDTO dto) {
        schoolMasterService.importExcel(dto);
    }

    @Operation(summary = "导出")
    @SaCheckPermission(value = "school.master.export")
    @PostMapping("/export")
    public void exportExcel(@RequestBody SchoolMasterListDTO dto, HttpServletResponse response) {
        schoolMasterService.exportExcel(dto, response);
    }
}