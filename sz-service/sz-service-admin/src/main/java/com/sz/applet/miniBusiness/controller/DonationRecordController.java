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
import com.sz.applet.miniBusiness.service.DonationRecordService;
import com.sz.applet.miniBusiness.pojo.dto.DonationRecordCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationRecordUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationRecordListDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationPayDTO;
import com.sz.applet.miniBusiness.pojo.vo.DonationRecordVO;
import com.sz.core.common.entity.ImportExcelDTO;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * 捐款记录 Controller
 * </p>
 *
 * @author LisPig
 * @since 2025-10-26
 */
@Tag(name =  "捐款记录")
@RestController
@RequestMapping("donation-record")
@RequiredArgsConstructor
public class DonationRecordController  {

    private final DonationRecordService donationRecordService;

    @Operation(summary = "新增")
    @SaCheckPermission(value = "donation.record.create")
    @PostMapping
    public ApiResult<Void> create(@RequestBody DonationRecordCreateDTO dto) {
        donationRecordService.create(dto);
        return ApiResult.success();
    }

    @Operation(summary = "修改")
    @SaCheckPermission(value = "donation.record.update")
    @PutMapping
    public ApiResult<Void> update(@RequestBody DonationRecordUpdateDTO dto) {
        donationRecordService.update(dto);
        return ApiResult.success();
    }

    @Operation(summary = "删除")
    @SaCheckPermission(value = "donation.record.remove")
    @DeleteMapping
    public ApiResult<Void> remove(@RequestBody SelectIdsDTO dto) {
        donationRecordService.remove(dto);
        return ApiResult.success();
    }

    @Operation(summary = "列表查询")
    @SaCheckPermission(value = "donation.record.query_table")
    @GetMapping
    public ApiResult<PageResult<DonationRecordVO>> list(DonationRecordListDTO dto) {
        return ApiPageResult.success(donationRecordService.page(dto));
    }

    @Operation(summary = "详情")
    @SaCheckPermission(value = "donation.record.query_table")
    @GetMapping("/{id}")
    public ApiResult<DonationRecordVO> detail(@PathVariable Object id) {
        return ApiResult.success(donationRecordService.detail(id));
    }

    @Operation(summary = "导入")
    @Parameters({
      @Parameter(name = "file", description = "上传文件", schema = @Schema(type = "string", format = "binary"), required = true),
    })
    @SaCheckPermission(value = "donation.record.import")
    @PostMapping("/import")
    public void importExcel(@ModelAttribute ImportExcelDTO dto) {
        donationRecordService.importExcel(dto);
    }

    @Operation(summary = "导出")
    @SaCheckPermission(value = "donation.record.export")
    @PostMapping("/export")
    public void exportExcel(@RequestBody DonationRecordListDTO dto, HttpServletResponse response) {
        donationRecordService.exportExcel(dto, response);
    }
    
    @Operation(summary = "创建微信支付订单")
    @PostMapping("/create-pay")
    public ApiResult<String> createWechatPay(@RequestBody DonationPayDTO dto) {
        String packageVal = donationRecordService.createWechatPay(dto);
        return ApiResult.success(packageVal);
    }
}