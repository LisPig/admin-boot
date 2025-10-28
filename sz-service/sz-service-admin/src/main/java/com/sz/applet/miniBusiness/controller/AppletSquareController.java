package com.sz.applet.miniBusiness.controller;

import com.mybatisflex.core.paginate.Page;
import com.sz.applet.miniBusiness.pojo.dto.*;
import com.sz.applet.miniBusiness.pojo.bo.MemoListBO;
import com.sz.applet.miniBusiness.pojo.vo.CommentVO;
import com.sz.applet.miniBusiness.pojo.vo.MemoVO;
import com.sz.applet.miniBusiness.service.AppletSquareCommentsService;
import com.sz.applet.miniBusiness.service.AppletSquareFollowsService;
import com.sz.applet.miniBusiness.service.AppletSquareLikesService;
import com.sz.applet.miniBusiness.service.AppletSquareMemosService;
import com.sz.core.common.entity.ApiPageResult;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.util.PageUtils;
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
@Tag(name = "广场功能")
@RestController
@RequestMapping("/square")
@RequiredArgsConstructor
public class AppletSquareController {

    private final AppletSquareMemosService appletSquareMemosService;
    private final AppletSquareLikesService appletSquareLikesService;
    private final AppletSquareCommentsService appletSquareCommentsService;
    private final AppletSquareFollowsService appletSquareFollowsService;

    @Operation(summary = "发布动态")
    @PostMapping("/memo/create")
    public ApiResult<Void> createMemo(@RequestBody MemoCreateDTO dto) {
        // 实现发布动态逻辑
        appletSquareMemosService.createMemo(dto);
        return ApiResult.success();
    }

    @Operation(summary = "删除动态")
    @PostMapping("/memo/remove")
    public ApiResult<Void> removeMemo(@RequestBody SelectIdsDTO dto) {
        // 实现删除动态逻辑
        appletSquareMemosService.removeByIds(dto.getIds());
        return ApiResult.success();
    }

    @Operation(summary = "获取动态列表")
    @GetMapping("/memo/list")
    public ApiResult<PageResult<MemoVO>> listMemos(MemoListBO bo) {
        // 实现获取动态列表逻辑
        PageResult<MemoVO> page = appletSquareMemosService.listMemos(bo);
        return ApiResult.success(page);
    }

    @Operation(summary = "点赞/取消点赞")
    @PostMapping("/memo/like")
    public ApiResult<Void> likeMemo(@RequestBody MemoLikeDTO dto) {
        // 实现点赞/取消点赞逻辑
        appletSquareMemosService.likeMemo(dto);
        return ApiResult.success();
    }

    @Operation(summary = "发表评论")
    @PostMapping("/comment/save")
    public ApiResult<Void> saveComment(@RequestBody CommentSaveDTO dto) {
        // 实现发表评论逻辑
        appletSquareMemosService.saveComment(dto);
        return ApiResult.success();
    }

    @Operation(summary = "关注/取消关注")
    @PostMapping("/user/follow")
    public ApiResult<Boolean> followUser(@RequestBody UserFollowDTO dto) {
        // 实现关注/取消关注逻辑
        boolean result = appletSquareFollowsService.followUser(dto.getFollowedUserId());
        return ApiResult.success(result);
    }

}