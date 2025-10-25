package com.sz.applet.miniBusiness.controller;

import com.mybatisflex.core.paginate.Page;
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
    @Operation(summary = "添加校友会用户表")
    @PostMapping("/save")
    public boolean save(@RequestBody AppletAlumniAssociationUser appletAlumniAssociationUser) {
        return appletAlumniAssociationUserService.save(appletAlumniAssociationUser);
    }


    /**
     * 根据主键删除校友会用户表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @Operation(summary = "根据主键删除校友会用户表")
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
    @Operation(summary = "更新校友会用户表")
    @PutMapping("/update")
    public boolean update(@RequestBody AppletAlumniAssociationUser appletAlumniAssociationUser) {
        return appletAlumniAssociationUserService.updateById(appletAlumniAssociationUser);
    }


    /**
     * 查询所有校友会用户表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    public List<AppletAlumniAssociationUser> list() {
        return appletAlumniAssociationUserService.list();
    }


    /**
     * 根据校友会用户表主键获取详细信息。
     *
     * @param id appletAlumniAssociationUser主键
     * @return 校友会用户表详情
     */
    @Operation(summary = "根据校友会用户表主键获取详细信息")
    @GetMapping("/getInfo/{id}")
    public AppletAlumniAssociationUser getInfo(@PathVariable Serializable id) {
        return appletAlumniAssociationUserService.getById(id);
    }


    /**
     * 分页查询校友会用户表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询校友会用户表")
    @GetMapping("/page")
    public Page<AppletAlumniAssociationUser> page(Page<AppletAlumniAssociationUser> page) {
        return appletAlumniAssociationUserService.page(page);
    }
}