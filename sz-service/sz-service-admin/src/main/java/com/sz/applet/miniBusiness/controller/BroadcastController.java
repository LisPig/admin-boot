package com.sz.applet.miniBusiness.controller;

import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.po.Broadcast;
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
import com.sz.applet.miniBusiness.service.BroadcastService;
import com.sz.applet.miniBusiness.pojo.dto.BroadcastCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.BroadcastUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.BroadcastListDTO;
import com.sz.applet.miniBusiness.pojo.vo.BroadcastVO;

import java.util.List;

/**
 * <p>
 * 广播表 Controller
 * </p>
 *
 * @author LisPig
 * @since 2025-10-30
 */
@Tag(name =  "广播信息表")
@RestController
@RequestMapping("broadcast")
@RequiredArgsConstructor
public class BroadcastController  {

    private final BroadcastService broadcastService;

    @Operation(summary = "新增")
    @SaCheckPermission(value = "broadcast.create")
    @PostMapping
    public ApiResult<Void> create(@RequestBody BroadcastCreateDTO dto) {
        broadcastService.create(dto);
        return ApiResult.success();
    }

    @Operation(summary = "修改")
    @SaCheckPermission(value = "broadcast.update")
    @PutMapping
    public ApiResult<Void> update(@RequestBody BroadcastUpdateDTO dto) {
        broadcastService.update(dto);
        return ApiResult.success();
    }

    @Operation(summary = "删除")
    @SaCheckPermission(value = "broadcast.remove")
    @DeleteMapping
    public ApiResult<Void> remove(@RequestBody SelectIdsDTO dto) {
        broadcastService.remove(dto);
        return ApiResult.success();
    }

    @Operation(summary = "列表查询")
    @SaCheckPermission(value = "broadcast.query_table")
    @GetMapping
    public ApiResult<PageResult<BroadcastVO>> list(BroadcastListDTO dto) {
        return ApiPageResult.success(broadcastService.page(dto));
    }

    @Operation(summary = "详情")
    @SaCheckPermission(value = "broadcast.query_table")
    @GetMapping("/{id}")
    public ApiResult<BroadcastVO> detail(@PathVariable Object id) {
        return ApiResult.success(broadcastService.detail(id));
    }


    @Operation(summary = "列表-小程序端")
    @GetMapping("/list")
    public ApiResult<List<BroadcastVO>> list() {
        return ApiResult.success(broadcastService.listAs(new QueryWrapper().eq(Broadcast::getStatus, 1), BroadcastVO.class));
    }
}