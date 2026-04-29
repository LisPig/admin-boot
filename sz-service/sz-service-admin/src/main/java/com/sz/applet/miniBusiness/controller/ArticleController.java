package com.sz.applet.miniBusiness.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.paginate.Page;
import com.sz.applet.miniBusiness.pojo.bo.ArticleBo;
import com.sz.applet.miniBusiness.pojo.bo.ArticleListBo;
import com.sz.applet.miniBusiness.pojo.vo.ArticleVO;
import com.sz.applet.miniBusiness.service.ArticleService;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 小程序文章表 Controller
 * </p>
 *
 * @author LisPig
 * @since 2025-09-12
 */
@Tag(name = "小程序文章")
@RestController
@RequestMapping("wechat/mini/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "创建文章")
    @PostMapping
    public ApiResult<Void> create(@RequestBody ArticleBo bo) {
        articleService.create(bo);
        return ApiResult.success();
    }

    @Operation(summary = "审核文章")
    @SaCheckPermission("sys.article.apply_btn")
    @PostMapping("/check")
    public ApiResult<Void> check(@RequestBody ArticleBo bo) {
        articleService.check(bo);
        return ApiResult.success();
    }

    @Operation(summary = "撤稿")
    @SaCheckPermission("sys.article.retract_btn")
    @PostMapping("/retract")
    public ApiResult<Void> retract(@RequestBody SelectIdsDTO dto) {
        articleService.retract(dto);
        return ApiResult.success();
    }

    @Operation(summary = "更新文章")
    @PutMapping
    public ApiResult<Void> update(@RequestBody ArticleBo bo) {
        articleService.update(bo);
        return ApiResult.success();
    }

    @Operation(summary = "删除文章")
    @DeleteMapping
    public ApiResult<Void> remove(@RequestBody SelectIdsDTO dto) {
        articleService.remove(dto);
        return ApiResult.success();
    }

    @Operation(summary = "文章详情")
    @GetMapping("/{id}")
    public ApiResult<ArticleVO> detail(@PathVariable Long id) {
        return ApiResult.success(articleService.detail(id));
    }

    @Operation(summary = "文章列表")
    @GetMapping
    public ApiResult<PageResult<ArticleVO>> page(ArticleListBo bo) {
        return ApiResult.success(articleService.page(bo));
    }

    @Operation(summary = "文章列表(全部)")
    @GetMapping("/list")
    public ApiResult<List<ArticleVO>> list(ArticleListBo bo) {
        return ApiResult.success(articleService.list(bo));
    }

    @Operation(summary = "文章列表-小程序")
    @GetMapping("/listByMini")
    public ApiResult<PageResult<ArticleVO>> listByMini(ArticleListBo bo) {
        return ApiResult.success(articleService.miniList(bo));
    }
}