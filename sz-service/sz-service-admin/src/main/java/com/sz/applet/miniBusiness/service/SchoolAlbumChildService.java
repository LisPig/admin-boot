package com.sz.applet.miniBusiness.service;


import com.sz.applet.miniBusiness.pojo.bo.SchoolAlbumChildBO;
import com.sz.applet.miniBusiness.pojo.po.SchoolAlbumChild;
import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.vo.SchoolAlbumChildVO;
import com.sz.core.common.entity.PageResult;

/**
 * 相册子集表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface SchoolAlbumChildService extends IService<SchoolAlbumChild> {

    PageResult<SchoolAlbumChildVO> page(SchoolAlbumChildBO bo);

}