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
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociation;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 校友会表 控制层。
 *
 * @author LisPig
 * @since 1.0
 */
@Tag(name = "校友会")
@RestController
@RequestMapping("/appletAlumniAssociation")
public class AppletAlumniAssociationController {

    @Autowired
    private AppletAlumniAssociationService appletAlumniAssociationService;

    /**
     * 添加 校友会表
     *
     * @param appletAlumniAssociation 校友会表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    public boolean save(@RequestBody AppletAlumniAssociation appletAlumniAssociation) {
        return appletAlumniAssociationService.save(appletAlumniAssociation);
    }


    /**
     * 根据主键删除校友会表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return appletAlumniAssociationService.removeById(id);
    }


    /**
     * 根据主键更新校友会表
     *
     * @param appletAlumniAssociation 校友会表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    public boolean update(@RequestBody AppletAlumniAssociation appletAlumniAssociation) {
        return appletAlumniAssociationService.updateById(appletAlumniAssociation);
    }


    /**
     * 查询所有校友会表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    public List<AppletAlumniAssociation> list() {
        return appletAlumniAssociationService.list();
    }


    /**
     * 根据校友会表主键获取详细信息。
     *
     * @param id appletAlumniAssociation主键
     * @return 校友会表详情
     */
    @GetMapping("/getInfo/{id}")
    public AppletAlumniAssociation getInfo(@PathVariable Serializable id) {
        return appletAlumniAssociationService.getById(id);
    }


    /**
     * 分页查询校友会表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public Page<AppletAlumniAssociation> page(Page<AppletAlumniAssociation> page) {
        return appletAlumniAssociationService.page(page);
    }
}