package com.sz.applet.miniBusiness.controller;

import com.sz.applet.miniBusiness.pojo.vo.SchoolClassYearVO;
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
import com.sz.applet.miniBusiness.service.SchoolClassMemoryService;
import com.sz.applet.miniBusiness.pojo.dto.SchoolClassMemoryCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolClassMemoryUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolClassMemoryListDTO;
import com.sz.applet.miniBusiness.pojo.vo.SchoolClassMemoryVO;
import com.sz.core.common.entity.ImportExcelDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * <p>
 * 班级记忆表(定格青春) Controller
 * </p>
 *
 * @author lee
 * @since 2026-01-06
 */
@Tag(name =  "班级记忆表(定格青春)")
@RestController
@RequestMapping("school-class-memory")
@RequiredArgsConstructor
public class SchoolClassMemoryController  {

    private final SchoolClassMemoryService schoolClassMemoryService;

    @Operation(summary = "新增")
    @SaCheckPermission(value = "school.class.memory.create")
    @PostMapping
    public ApiResult<Void> create(@RequestBody SchoolClassMemoryCreateDTO dto) {
        schoolClassMemoryService.create(dto);
        return ApiResult.success();
    }

    @Operation(summary = "修改")
    @SaCheckPermission(value = "school.class.memory.update")
    @PutMapping
    public ApiResult<Void> update(@RequestBody SchoolClassMemoryUpdateDTO dto) {
        schoolClassMemoryService.update(dto);
        return ApiResult.success();
    }

    @Operation(summary = "删除")
    @SaCheckPermission(value = "school.class.memory.remove")
    @DeleteMapping
    public ApiResult<Void> remove(@RequestBody SelectIdsDTO dto) {
        schoolClassMemoryService.remove(dto);
        return ApiResult.success();
    }

    @Operation(summary = "列表查询")
    @SaCheckPermission(value = "school.class.memory.query_table")
    @GetMapping
    public ApiResult<PageResult<SchoolClassMemoryVO>> list(SchoolClassMemoryListDTO dto) {
        return ApiPageResult.success(schoolClassMemoryService.page(dto));
    }

    @Operation(summary = "详情")
    //@SaCheckPermission(value = "school.class.memory.query_table")
    @GetMapping("/{id}")
    public ApiResult<SchoolClassMemoryVO> detail(@PathVariable Object id) {
        return ApiResult.success(schoolClassMemoryService.detail(id));
    }


    @Operation(summary = "列表-小程序端")
    @GetMapping("/app/list")
    public ApiResult<PageResult<SchoolClassMemoryVO>> appList(SchoolClassMemoryListDTO dto) {
        return ApiPageResult.success(schoolClassMemoryService.page(dto));
    }

    @Operation(summary = "届数列表-小程序端")
    @GetMapping("/yearList")
    public ApiResult<PageResult<SchoolClassYearVO>> yearList(SchoolClassMemoryListDTO dto) {
        return ApiResult.success(schoolClassMemoryService.yearList(dto));
    }
}