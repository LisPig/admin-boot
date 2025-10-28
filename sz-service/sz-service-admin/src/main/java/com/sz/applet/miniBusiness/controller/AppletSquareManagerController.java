package com.sz.applet.miniBusiness.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.sz.applet.miniBusiness.pojo.bo.MemoListBO;
import com.sz.applet.miniBusiness.pojo.dto.CommentSaveDTO;
import com.sz.applet.miniBusiness.pojo.dto.MemoCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.MemoLikeDTO;
import com.sz.applet.miniBusiness.pojo.dto.UserFollowDTO;
import com.sz.applet.miniBusiness.pojo.vo.MemoVO;
import com.sz.applet.miniBusiness.service.AppletSquareCommentsService;
import com.sz.applet.miniBusiness.service.AppletSquareFollowsService;
import com.sz.applet.miniBusiness.service.AppletSquareLikesService;
import com.sz.applet.miniBusiness.service.AppletSquareMemosService;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 朋友圈功能 Controller
 * </p>
 *
 * @author your-name
 * @since 2025-10-28
 */
@Tag(name = "广场功能后台管理")
@RestController
@RequestMapping("/manager/square")
@RequiredArgsConstructor
public class AppletSquareManagerController {

    private final AppletSquareMemosService appletSquareMemosService;
    private final AppletSquareLikesService appletSquareLikesService;
    private final AppletSquareCommentsService appletSquareCommentsService;
    private final AppletSquareFollowsService appletSquareFollowsService;


    @Operation(summary = "获取动态列表")
    @SaCheckPermission("applet.square.memo.list")
    @GetMapping("/memo/list")
    public ApiResult<PageResult<MemoVO>> listMemos(MemoListBO bo) {
        // 实现获取动态列表逻辑
        PageResult<MemoVO> page = appletSquareMemosService.listMemos(bo);
        return ApiResult.success(page);
    }

    @Operation(summary = "删除动态")
    @SaCheckPermission("applet.square.memo.remove")
    @PostMapping("/memo/remove")
    public ApiResult<Void> removeMemo(@RequestBody SelectIdsDTO dto) {
        // 实现删除动态逻辑
        appletSquareMemosService.removeByIds(dto.getIds());
        return ApiResult.success();
    }

}