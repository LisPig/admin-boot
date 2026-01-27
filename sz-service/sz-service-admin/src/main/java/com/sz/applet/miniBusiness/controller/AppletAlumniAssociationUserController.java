package com.sz.applet.miniBusiness.controller;

import com.mybatisflex.core.paginate.Page;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.core.common.entity.ApiResult;
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
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationUserService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationUser;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 校友会用户表 控制层。
 *
 * @author LisPig
 * @since 1.0
 */
@Tag(name = "校友会用户")
@RestController
@RequestMapping("/appletAlumniAssociationUser")
@RequiredArgsConstructor
public class AppletAlumniAssociationUserController {

    private final AppletAlumniAssociationUserService appletAlumniAssociationUserService;

    /**
     * 添加 校友会用户表
     *
     * @param appletAlumniAssociationUser 校友会用户表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @Operation(summary = "小程序-申请加入校友会")
    @PostMapping("/join")
    public boolean save(@RequestBody AppletAlumniAssociationUser appletAlumniAssociationUser) {
        return appletAlumniAssociationUserService.join(appletAlumniAssociationUser);
    }


    /**
     * 根据主键删除校友会用户表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @Operation(summary = "后台-根据主键删除校友会用户表")
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return appletAlumniAssociationUserService.removeById(id);
    }


    /**
     * 根据主键更新校友会用户表
     *
     * @param appletAlumniAssociationUser 校友会用户表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @Operation(summary = "后台-更新校友会用户表")
    @PutMapping("/update")
    public boolean update(@RequestBody AppletAlumniAssociationUser appletAlumniAssociationUser) {
        return appletAlumniAssociationUserService.updateById(appletAlumniAssociationUser);
    }


    /**
     * 查询所有校友会用户表
     *
     * @return 所有数据
     */
    @Operation(summary = "后台-查询所有校友会用户表")
    @GetMapping("/list")
    public ApiResult<List<AppletAlumniAssociationUser>> list() {
        return ApiResult.success(appletAlumniAssociationUserService.list());
    }


    /**
     * 根据校友会用户表主键获取详细信息。
     *
     * @param id appletAlumniAssociationUser主键
     * @return 校友会用户表详情
     */
    @Operation(summary = "根据校友会用户表主键获取详细信息")
    @GetMapping("/getInfo/{id}")
    public ApiResult<MiniUserVO> getInfo(@PathVariable Long id) {
        return ApiResult.success(appletAlumniAssociationUserService.getUser(id));
    }


    /**
     * 分页查询校友会用户表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询校友会用户表")
    @GetMapping("/page")
    public ApiResult<Page<AppletAlumniAssociationUser>> page(Page<AppletAlumniAssociationUser> page) {
        return ApiResult.success(appletAlumniAssociationUserService.page(page));
    }


    /**
     * 会长转让
     */
    @Operation(summary = "会长转让")
    @PostMapping("/transfer")
    public ApiResult<Boolean> transfer(@RequestBody AppletAlumniAssociationUser appletAlumniAssociationUser) {
        return ApiResult.success(appletAlumniAssociationUserService.transfer(appletAlumniAssociationUser));
    }

}