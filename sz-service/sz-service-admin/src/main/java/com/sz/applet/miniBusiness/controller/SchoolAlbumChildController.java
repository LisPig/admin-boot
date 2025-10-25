package com.sz.applet.miniBusiness.controller;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.bo.SchoolAlbumChildBO;
import com.sz.applet.miniBusiness.pojo.vo.SchoolAlbumChildVO;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.PageResult;
import com.sz.core.util.PageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.sz.applet.miniBusiness.service.SchoolAlbumChildService;
import com.sz.applet.miniBusiness.pojo.po.SchoolAlbumChild;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 相册子集表 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Tag(name = "相册子集表")
@RestController
@RequestMapping("/schoolAlbumChild")
public class SchoolAlbumChildController {

    @Autowired
    private SchoolAlbumChildService schoolAlbumChildService;

    /**
     * 添加 相册子集表
     *
     * @param schoolAlbumChild 相册子集表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @Operation(summary = "添加")
    @PostMapping("/save")
    public boolean save(@RequestBody SchoolAlbumChild schoolAlbumChild) {
        return schoolAlbumChildService.save(schoolAlbumChild);
    }


    /**
     * 根据主键删除相册子集表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @Operation(summary = "删除")
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return schoolAlbumChildService.removeById(id);
    }


    /**
     * 根据主键更新相册子集表
     *
     * @param schoolAlbumChild 相册子集表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @Operation(summary = "更新")
    @PutMapping("/update")
    public boolean update(@RequestBody SchoolAlbumChild schoolAlbumChild) {
        return schoolAlbumChildService.updateById(schoolAlbumChild);
    }


    /**
     * 查询所有相册子集表
     *
     * @return 所有数据
     */
    @Operation(summary = "查询所有")
    @GetMapping("/list")
    public List<SchoolAlbumChild> list() {
        return schoolAlbumChildService.list();
    }


    /**
     * 根据相册子集表主键获取详细信息。
     *
     * @param id schoolAlbumChild主键
     * @return 相册子集表详情
     */
    @Operation(summary = "根据id查询")
    @GetMapping("/getInfo/{id}")
    public SchoolAlbumChild getInfo(@PathVariable Serializable id) {
        return schoolAlbumChildService.getById(id);
    }


    /**
     * 分页查询相册子集表
     *
     * @param
     * @return 分页对象
     */
    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public ApiResult<PageResult<SchoolAlbumChildVO>> page(SchoolAlbumChildBO bo) {
        return ApiResult.success(schoolAlbumChildService.page(bo));
    }



    @Operation(summary = "列表-小程序")
    @GetMapping("/list/{albumId}")
    public List<SchoolAlbumChild> list(@PathVariable Long albumId) {
        return schoolAlbumChildService.list(new QueryWrapper()
                .eq(SchoolAlbumChild::getAlbumId, albumId));
    }
}