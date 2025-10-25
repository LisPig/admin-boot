package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.po.SchoolMaster;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.entity.PageResult;
import java.util.List;
import com.sz.applet.miniBusiness.pojo.dto.SchoolMasterCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolMasterUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolMasterListDTO;
import com.sz.applet.miniBusiness.pojo.vo.SchoolMasterVO;
import com.sz.core.common.entity.ImportExcelDTO;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * 校长表 Service
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
public interface SchoolMasterService extends IService<SchoolMaster> {

    void create(SchoolMasterCreateDTO dto);

    void update(SchoolMasterUpdateDTO dto);

    PageResult<SchoolMasterVO> page(SchoolMasterListDTO dto);

    List<SchoolMasterVO> list(SchoolMasterListDTO dto);

    void remove(SelectIdsDTO dto);

    SchoolMasterVO detail(Object id);

    void importExcel(ImportExcelDTO dto);

    void exportExcel(SchoolMasterListDTO dto, HttpServletResponse response);
}