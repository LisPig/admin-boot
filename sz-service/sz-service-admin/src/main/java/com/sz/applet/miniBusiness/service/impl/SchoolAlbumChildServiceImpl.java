package com.sz.applet.miniBusiness.service.impl;


import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.bo.SchoolAlbumChildBO;
import com.sz.applet.miniBusiness.pojo.vo.SchoolAlbumChildVO;
import com.sz.core.common.entity.PageResult;
import com.sz.core.util.PageUtils;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.SchoolAlbumChildService;
import com.sz.applet.miniBusiness.pojo.po.SchoolAlbumChild;
import com.sz.applet.miniBusiness.mapper.SchoolAlbumChildMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

/**
 * 相册子集表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class SchoolAlbumChildServiceImpl extends ServiceImpl<SchoolAlbumChildMapper, SchoolAlbumChild> implements SchoolAlbumChildService {

    @Override
    public PageResult<SchoolAlbumChildVO> page(SchoolAlbumChildBO bo) {
        QueryWrapper queryWrapper = this.buildQueryWrapper(bo);
        return PageUtils.getPageResult(pageAs(PageUtils.getPage(bo), queryWrapper, SchoolAlbumChildVO.class));
    }

    private QueryWrapper buildQueryWrapper(SchoolAlbumChildBO bo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper
                .eq(SchoolAlbumChild::getAlbumId, bo.getAlbumId());
        return queryWrapper;
    }
}