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
import com.sz.applet.miniBusiness.service.DonationProjectService;
import com.sz.applet.miniBusiness.pojo.dto.DonationProjectCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationProjectUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationProjectListDTO;
import com.sz.applet.miniBusiness.pojo.vo.DonationProjectVO;

/**
 * <p>
 * 捐款项目 Controller
 * </p>
 *
 * @author LisPig
 * @since 2025-10-26
 */
@Tag(name =  "捐款项目")
@RestController
@RequestMapping("donation-project")
@RequiredArgsConstructor
public class DonationProjectController  {

    private final DonationProjectService donationProjectService;

    @Operation(summary = "新增")
    @SaCheckPermission(value = "donation.project.create")
    @PostMapping
    public ApiResult<Void> create(@RequestBody DonationProjectCreateDTO dto) {
        donationProjectService.create(dto);
        return ApiResult.success();
    }

    @Operation(summary = "修改")
    @SaCheckPermission(value = "donation.project.update")
    @PutMapping
    public ApiResult<Void> update(@RequestBody DonationProjectUpdateDTO dto) {
        donationProjectService.update(dto);
        return ApiResult.success();
    }

    @Operation(summary = "删除")
    @SaCheckPermission(value = "donation.project.remove")
    @DeleteMapping
    public ApiResult<Void> remove(@RequestBody SelectIdsDTO dto) {
        donationProjectService.remove(dto);
        return ApiResult.success();
    }

    @Operation(summary = "列表查询")
    @SaCheckPermission(value = "donation.project.query_table")
    @GetMapping
    public ApiResult<PageResult<DonationProjectVO>> list(DonationProjectListDTO dto) {
        return ApiPageResult.success(donationProjectService.page(dto));
    }

    @Operation(summary = "详情")
    @SaCheckPermission(value = "donation.project.query_table")
    @GetMapping("/{id}")
    public ApiResult<DonationProjectVO> detail(@PathVariable Object id) {
        return ApiResult.success(donationProjectService.detail(id));
    }

    @Operation(summary = "列表查询-小程序")
    @GetMapping("/listByMini")
    public ApiResult<PageResult<DonationProjectVO>> listByMini(DonationProjectListDTO dto) {
        return ApiPageResult.success(donationProjectService.miniPage(dto));
    }
}