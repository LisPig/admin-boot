package com.sz.applet.miniBusiness.controller;

import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.sz.applet.miniBusiness.service.ApplyAuthService;
import com.sz.applet.miniBusiness.pojo.po.ApplyAuth;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 校友申请认证表 控制层。
 *
 * @author LisPig
 * @since 1.0
 */
@RestController
@RequestMapping("/applyAuth")
public class ApplyAuthController {

    @Autowired
    private ApplyAuthService applyAuthService;

    /**
     * 添加 校友申请认证表
     *
     * @param applyAuth 校友申请认证表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    public boolean save(@RequestBody ApplyAuth applyAuth) {
        return applyAuthService.save(applyAuth);
    }


    /**
     * 根据主键删除校友申请认证表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return applyAuthService.removeById(id);
    }


    /**
     * 根据主键更新校友申请认证表
     *
     * @param applyAuth 校友申请认证表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    public boolean update(@RequestBody ApplyAuth applyAuth) {
        return applyAuthService.updateById(applyAuth);
    }


    /**
     * 查询所有校友申请认证表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    public List<ApplyAuth> list() {
        return applyAuthService.list();
    }


    /**
     * 根据校友申请认证表主键获取详细信息。
     *
     * @param id applyAuth主键
     * @return 校友申请认证表详情
     */
    //@Operation(summary = "根据校友申请认证表主键获取详细信息")
    @GetMapping("/getInfo/{id}")
    public ApplyAuth getInfo(@PathVariable Serializable id) {
        return applyAuthService.getById(id);
    }


    /**
     * 分页查询校友申请认证表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    //@Operation(summary = "分页查询校友申请认证表")
    @GetMapping("/page")
    public Page<ApplyAuth> page(Page<ApplyAuth> page) {
        return applyAuthService.page(page);
    }
}