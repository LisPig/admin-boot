package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.bo.MemoListBO;
import com.sz.applet.miniBusiness.pojo.dto.CommentSaveDTO;
import com.sz.applet.miniBusiness.pojo.dto.MemoCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.MemoLikeDTO;
import com.sz.applet.miniBusiness.pojo.po.*;
import com.sz.applet.miniBusiness.pojo.vo.MemoVO;
import com.sz.core.common.entity.PageResult;

/**
 * 动态表 服务层。
 *
 * @author your-name
 * @since 1.0
 */
public interface AppletSquareMemosService extends IService<AppletSquareMemos> {

    void createMemo(MemoCreateDTO dto);

    PageResult<MemoVO> listMemos(MemoListBO bo);

    void likeMemo(MemoLikeDTO dto);
    
    void saveComment(CommentSaveDTO dto);
}