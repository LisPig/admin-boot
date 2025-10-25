package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.po.ArchiveFiles;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.entity.PageResult;
import java.util.List;
import com.sz.applet.miniBusiness.pojo.dto.ArchiveFilesCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.ArchiveFilesUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.ArchiveFilesListDTO;
import com.sz.applet.miniBusiness.pojo.vo.ArchiveFilesVO;

/**
 * <p>
 * 档案申请记录表 Service
 * </p>
 *
 * @author LisPig
 * @since 2025-10-25
 */
public interface ArchiveFilesService extends IService<ArchiveFiles> {

    void create(ArchiveFilesCreateDTO dto);

    void update(ArchiveFilesUpdateDTO dto);

    PageResult<ArchiveFilesVO> page(ArchiveFilesListDTO dto);

    List<ArchiveFilesVO> list(ArchiveFilesListDTO dto);

    void remove(SelectIdsDTO dto);

    ArchiveFilesVO detail(Object id);
}