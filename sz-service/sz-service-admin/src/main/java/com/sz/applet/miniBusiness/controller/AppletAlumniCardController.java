package com.sz.applet.miniBusiness.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.bo.AppletAlumniCardBo;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.LoginUser;
import com.sz.security.core.util.LoginUtils;
import com.sz.security.pojo.LoginInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.sz.applet.miniBusiness.service.IAppletAlumniCardService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniCard;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 校友卡表 控制层。
 *
 * @author LisPig
 * @since 1.0
 */
@Tag(name = "校友卡")
@RestController
@RequestMapping("/appletAlumniCard")
@RequiredArgsConstructor
public class AppletAlumniCardController {

    private final IAppletAlumniCardService appletAlumniCardService;

    /**
     * 添加 校友卡表
     *
     * @param appletAlumniCard 校友卡表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @Operation(summary = "添加校友卡表")
    @PostMapping("/save")
    public ApiResult<Boolean> save(@RequestBody AppletAlumniCardBo appletAlumniCard) {
        return ApiResult.success(appletAlumniCardService.save(appletAlumniCard));
    }


    /**sys_data_role_relation
     * 根据主键删除校友卡表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @Operation(summary = "删除校友卡表")
    @DeleteMapping("/remove/{id}")
    public ApiResult<Boolean> remove(@PathVariable Serializable id) {
        return ApiResult.success(appletAlumniCardService.removeById(id));
    }


    /**
     * 根据主键更新校友卡表
     *
     * @param appletAlumniCard 校友卡表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @Operation(summary = "更新校友卡表")
    @PutMapping("/update")
    public ApiResult<Boolean> update(@RequestBody AppletAlumniCard appletAlumniCard) {
        return ApiResult.success(appletAlumniCardService.updateById(appletAlumniCard));
    }


    /**
     * 小程序端查询所有校友卡表
     *
     * @return 所有数据
     */
    @Operation(summary = "小程序端查询校友卡表(当前用户)")
    @GetMapping("/list")
    public ApiResult<List<AppletAlumniCard>> list()
    {
        LoginUser loginUser = LoginUtils.getLoginUser();
        assert loginUser != null;
        return ApiResult.success(appletAlumniCardService.list(
                new QueryWrapper().eq(AppletAlumniCard::getCreateId, loginUser.getUserInfo().getId())));
    }


    /**
     * 根据校友卡表主键获取详细信息。
     *
     * @param id appletAlumniCard主键
     * @return 校友卡表详情
     */
    @Operation(summary = "小程序端查询校友卡表详情")
    @GetMapping("/getInfo/{id}")
    public ApiResult<AppletAlumniCard> getInfo(@PathVariable Serializable id) {
        return ApiResult.success(appletAlumniCardService.getById(id));
    }


    /**
     * 分页查询校友卡表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询校友卡表")
    @GetMapping("/page")
    public ApiResult<Page<AppletAlumniCard>> page(Page<AppletAlumniCard> page) {
        return ApiResult.success(appletAlumniCardService.page(page));
    }

    /**
     * 审核校园卡
     * @param appletAlumniCardBo
     * @return
     */
    @Operation(summary = "审核校园卡")
    @SaCheckPermission("mini:appletAlumniCard:check")
    @PostMapping("/check")
    public ApiResult<Boolean> check(@RequestBody AppletAlumniCardBo appletAlumniCardBo) {
        return ApiResult.success(appletAlumniCardService.checkAppletAlumniCard(appletAlumniCardBo));
    }
}