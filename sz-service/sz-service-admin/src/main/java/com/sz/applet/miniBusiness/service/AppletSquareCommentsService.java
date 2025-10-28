package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.bo.CommentListBO;
import com.sz.applet.miniBusiness.pojo.po.AppletSquareComments;
import com.sz.applet.miniBusiness.pojo.vo.CommentVO;
import com.sz.core.common.entity.PageResult;

/**
 * 评论表 服务层。
 *
 * @author your-name
 * @since 1.0
 */
public interface AppletSquareCommentsService extends IService<AppletSquareComments> {

    PageResult<CommentVO> listComments(CommentListBO bo);
}