package com.sz.applet.miniBusiness.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.paginate.Page;
import com.sz.applet.miniBusiness.pojo.bo.AppletAlumniPassApplicationBo;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniPassApplicationCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniPassApplicationListDTO;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniPassApplicationVO;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.util.PageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.sz.applet.miniBusiness.service.AppletAlumniPassApplicationService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniPassApplication;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 校友通行证申请表 控制层。
 *
 * @author LisPig
 * @since 1.0
 */
@Tag(name = "校友通行证")
@RestController
@RequestMapping("/appletAlumniPassApplication")
public class AppletAlumniPassApplicationController {

    @Autowired
    private AppletAlumniPassApplicationService appletAlumniPassApplicationService;

    @Operation(summary = "新增")
    //@SaCheckPermission(value = "applet.alumni.pass.application.create")
    @PostMapping
    public ApiResult<Void> create(@RequestBody AppletAlumniPassApplicationCreateDTO dto) {
        appletAlumniPassApplicationService.create(dto);
        return ApiResult.success();
    }

    @Operation(summary = "修改")
    //@SaCheckPermission(value = "applet.alumni.pass.application.update")
    @PutMapping
    public ApiResult<Void> update(@RequestBody AppletAlumniPassApplicationCreateDTO dto) {
        appletAlumniPassApplicationService.update(dto);
        return ApiResult.success();
    }

    @Operation(summary = "删除")
    //@SaCheckPermission(value = "applet.alumni.pass.application.remove")
    @DeleteMapping
    public ApiResult<Void> remove(@RequestBody SelectIdsDTO dto) {
        appletAlumniPassApplicationService.remove(dto);
        return ApiResult.success();
    }

    @Operation(summary = "详情")
    //@SaCheckPermission(value = "applet.alumni.pass.application.query_table")
    @GetMapping("/{id}")
    public ApiResult<AppletAlumniPassApplicationVO> detail(@PathVariable Object id) {
        return ApiResult.success(appletAlumniPassApplicationService.detail(id));
    }

    /*@Operation(summary = "导入")
    @Parameters({
            @Parameter(name = "file", description = "上传文件", schema = @Schema(type = "string", format = "binary"), required = true),
    })
    @SaCheckPermission(value = "applet.alumni.pass.application.import")
    @PostMapping("/import")
    public void importExcel(@ModelAttribute ImportExcelDTO dto) {
        appletAlumniPassApplicationService.importExcel(dto);
    }

    @Operation(summary = "导出")
    @SaCheckPermission(value = "applet.alumni.pass.application.export")
    @PostMapping("/export")
    public void exportExcel(@RequestBody AppletAlumniPassApplicationListDTO dto, HttpServletResponse response) {
        appletAlumniPassApplicationService.exportExcel(dto, response);
    }*/


    /**
     * 分页查询校友通行证申请表
     *
     * @param bo 分页对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询校友通行证申请表")
    @GetMapping("/page")
    public ApiResult<PageResult<AppletAlumniPassApplicationVO>> page(AppletAlumniPassApplicationBo bo) {
        return ApiResult.success(appletAlumniPassApplicationService.page(bo));
    }

    @Operation(summary = "小程序-用户通行证列表")
    @GetMapping("/list")
    public ApiResult<List<AppletAlumniPassApplicationVO>> list(AppletAlumniPassApplicationListDTO dto) {
        return ApiResult.success(appletAlumniPassApplicationService.list(dto));
    }

    @Operation(summary = "通行证审批")
    @PostMapping("/approve")
    public ApiResult<Void> approve(@RequestBody AppletAlumniPassApplicationCreateDTO dto) {
        appletAlumniPassApplicationService.approve(dto);
        return ApiResult.success();
    }
}