package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.po.SchoolClassMemory;
import com.sz.applet.miniBusiness.pojo.vo.SchoolClassYearVO;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.entity.PageResult;
import java.util.List;
import com.sz.applet.miniBusiness.pojo.dto.SchoolClassMemoryCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolClassMemoryUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolClassMemoryListDTO;
import com.sz.applet.miniBusiness.pojo.vo.SchoolClassMemoryVO;
import com.sz.core.common.entity.ImportExcelDTO;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * 班级记忆表(定格青春) Service
 * </p>
 *
 * @author lee
 * @since 2026-01-06
 */
public interface SchoolClassMemoryService extends IService<SchoolClassMemory> {

    void create(SchoolClassMemoryCreateDTO dto);

    void update(SchoolClassMemoryUpdateDTO dto);

    PageResult<SchoolClassMemoryVO> page(SchoolClassMemoryListDTO dto);

    List<SchoolClassMemoryVO> list(SchoolClassMemoryListDTO dto);

    void remove(SelectIdsDTO dto);

    SchoolClassMemoryVO detail(Object id);

    void importExcel(ImportExcelDTO dto);

    void exportExcel(SchoolClassMemoryListDTO dto, HttpServletResponse response);

    List<SchoolClassYearVO> yearList(SchoolClassMemoryListDTO dto);
}