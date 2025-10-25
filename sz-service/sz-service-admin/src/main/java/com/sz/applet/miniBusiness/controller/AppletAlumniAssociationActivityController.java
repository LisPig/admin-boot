package com.sz.applet.miniBusiness.controller;

import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationActivityService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivity;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 校友会活动表 控制层。
 *
 * @author LisPig
 * @since 1.0
 */
@Tag(name = "校友会活动")
@RestController
@RequestMapping("/appletAlumniAssociationActivity")
public class AppletAlumniAssociationActivityController {

    @Autowired
    private AppletAlumniAssociationActivityService appletAlumniAssociationActivityService;

    /**
     * 添加 校友会活动表
     *
     * @param appletAlumniAssociationActivity 校友会活动表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    public boolean save(@RequestBody AppletAlumniAssociationActivity appletAlumniAssociationActivity) {
        return appletAlumniAssociationActivityService.save(appletAlumniAssociationActivity);
    }


    /**
     * 根据主键删除校友会活动表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return appletAlumniAssociationActivityService.removeById(id);
    }


    /**
     * 根据主键更新校友会活动表
     *
     * @param appletAlumniAssociationActivity 校友会活动表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    public boolean update(@RequestBody AppletAlumniAssociationActivity appletAlumniAssociationActivity) {
        return appletAlumniAssociationActivityService.updateById(appletAlumniAssociationActivity);
    }


    /**
     * 查询所有校友会活动表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    public List<AppletAlumniAssociationActivity> list() {
        return appletAlumniAssociationActivityService.list();
    }


    /**
     * 根据校友会活动表主键获取详细信息。
     *
     * @param id appletAlumniAssociationActivity主键
     * @return 校友会活动表详情
     */
    @GetMapping("/getInfo/{id}")
    public AppletAlumniAssociationActivity getInfo(@PathVariable Serializable id) {
        return appletAlumniAssociationActivityService.getById(id);
    }


    /**
     * 分页查询校友会活动表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public Page<AppletAlumniAssociationActivity> page(Page<AppletAlumniAssociationActivity> page) {
        return appletAlumniAssociationActivityService.page(page);
    }
}