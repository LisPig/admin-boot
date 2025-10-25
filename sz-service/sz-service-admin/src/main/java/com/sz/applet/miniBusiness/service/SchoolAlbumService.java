package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.po.SchoolAlbum;
import com.sz.applet.miniBusiness.pojo.vo.SchoolAlbumVO;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.entity.PageResult;
import java.util.List;
import com.sz.applet.miniBusiness.pojo.dto.SchoolAlbumCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolAlbumUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolAlbumListDTO;
import com.sz.core.common.entity.ImportExcelDTO;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * 相册表 Service
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
public interface SchoolAlbumService extends IService<SchoolAlbum> {

    void create(SchoolAlbumCreateDTO dto);

    void update(SchoolAlbumUpdateDTO dto);

    PageResult<SchoolAlbumVO> page(SchoolAlbumListDTO dto);

    List<SchoolAlbumVO> list(SchoolAlbumListDTO dto);

    void remove(SelectIdsDTO dto);

    SchoolAlbumVO detail(Object id);

    void importExcel(ImportExcelDTO dto);

    void exportExcel(SchoolAlbumListDTO dto, HttpServletResponse response);
}