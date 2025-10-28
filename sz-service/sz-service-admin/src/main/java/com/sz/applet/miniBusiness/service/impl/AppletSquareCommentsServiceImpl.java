package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.applet.miniBusiness.mapper.AppletSquareCommentsMapper;
import com.sz.applet.miniBusiness.pojo.bo.CommentListBO;
import com.sz.applet.miniBusiness.pojo.po.AppletSquareComments;
import com.sz.applet.miniBusiness.pojo.vo.CommentVO;
import com.sz.applet.miniBusiness.service.AppletSquareCommentsService;
import com.sz.core.common.entity.PageResult;
import com.sz.core.util.PageUtils;
import org.springframework.stereotype.Service;

/**
 * 评论表 服务层实现。
 *
 * @author your-name
 * @since 1.0
 */
@Service
public class AppletSquareCommentsServiceImpl extends ServiceImpl<AppletSquareCommentsMapper, AppletSquareComments> implements AppletSquareCommentsService {

    @Override
    public PageResult<CommentVO> listComments(CommentListBO bo) {

        return PageUtils.getPageResult(this.pageAs(PageUtils.getPage(bo), buildQueryWrapper(bo), CommentVO.class));
    }


    private QueryWrapper buildQueryWrapper(CommentListBO bo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(AppletSquareComments::getMemoId, bo.getMemoId());
        queryWrapper.orderBy(AppletSquareComments::getCreateTime, false);
        return queryWrapper;
    }
}