package com.sz.applet.miniBusiness.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.sz.applet.miniBusiness.pojo.bo.CommentListBO;
import com.sz.applet.miniBusiness.pojo.bo.MemoListBO;
import com.sz.applet.miniBusiness.pojo.vo.CommentVO;
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
@Deprecated
@Tag(name = "广场评论管理(废弃)")
@RestController
@RequestMapping("/manager/comment")
@RequiredArgsConstructor
public class AppletCommentsManagerController {

    private final AppletSquareMemosService appletSquareMemosService;
    private final AppletSquareLikesService appletSquareLikesService;
    private final AppletSquareCommentsService appletSquareCommentsService;
    private final AppletSquareFollowsService appletSquareFollowsService;


    @Operation(summary = "获取动态下的评论列表")
    @GetMapping("/list")
    public ApiResult<PageResult<CommentVO>> listMemos(@RequestBody CommentListBO bo) {

        PageResult<CommentVO> pageResult = appletSquareCommentsService.listComments(bo);
        return ApiResult.success();
    }

    @Operation(summary = "管理员删除评论")
    @SaCheckPermission("applet.square.comment.remove")
    @PostMapping("/remove")
    public ApiResult<Void> removeMemo(@RequestBody SelectIdsDTO dto) {
        // 实现删除动态逻辑
        appletSquareMemosService.removeByIds(dto.getIds());
        return ApiResult.success();
    }

    @Operation(summary = "用户删除评论")
    @PostMapping("/removeByUser")
    public ApiResult<Void> removeMemoByUser(@RequestBody SelectIdsDTO dto) {
        // 实现删除动态逻辑
        appletSquareMemosService.removeByIds(dto.getIds());
        return ApiResult.success();
    }

}