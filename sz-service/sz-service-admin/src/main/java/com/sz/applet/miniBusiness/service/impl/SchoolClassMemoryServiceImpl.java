package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.SchoolClassMemoryService;
import com.sz.applet.miniBusiness.pojo.po.SchoolClassMemory;
import com.sz.applet.miniBusiness.mapper.SchoolClassMemoryMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.query.QueryChain;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.util.PageUtils;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.Utils;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import java.io.Serializable;
import java.util.List;
import com.sz.applet.miniBusiness.pojo.dto.SchoolClassMemoryCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolClassMemoryUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolClassMemoryListDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolClassMemoryImportDTO;
import com.sz.core.common.entity.ImportExcelDTO;
import com.sz.excel.core.ExcelResult;
import java.io.OutputStream;
import jakarta.servlet.http.HttpServletResponse;
import com.sz.core.util.FileUtils;
import com.sz.excel.utils.ExcelUtils;
import lombok.SneakyThrows;
import com.sz.applet.miniBusiness.pojo.vo.SchoolClassMemoryVO;

/**
 * <p>
 * 班级记忆表(定格青春) 服务实现类
 * </p>
 *
 * @author lee
 * @since 2026-01-06
 */
@Service
@RequiredArgsConstructor
public class SchoolClassMemoryServiceImpl extends ServiceImpl<SchoolClassMemoryMapper, SchoolClassMemory> implements SchoolClassMemoryService {
    @Override
    public void create(SchoolClassMemoryCreateDTO dto){
        SchoolClassMemory schoolClassMemory = BeanCopyUtils.copy(dto, SchoolClassMemory.class);
        save(schoolClassMemory);
    }

    @Override
    public void update(SchoolClassMemoryUpdateDTO dto){
        SchoolClassMemory schoolClassMemory = BeanCopyUtils.copy(dto, SchoolClassMemory.class);
        QueryWrapper wrapper;
        // id有效性校验
        wrapper = QueryWrapper.create()
            .eq(SchoolClassMemory::getId, dto.getId());
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);

        saveOrUpdate(schoolClassMemory);
    }

    @Override
    public PageResult<SchoolClassMemoryVO> page(SchoolClassMemoryListDTO dto){
        Page<SchoolClassMemoryVO> page = pageAs(PageUtils.getPage(dto), buildQueryWrapper(dto), SchoolClassMemoryVO.class);
        return PageUtils.getPageResult(page);
    }

    @Override
    public List<SchoolClassMemoryVO> list(SchoolClassMemoryListDTO dto){
        return listAs(buildQueryWrapper(dto), SchoolClassMemoryVO.class);
    }

    @Override
    public void remove(SelectIdsDTO dto){
        CommonResponseEnum.INVALID_ID.assertTrue(dto.getIds().isEmpty());
        removeByIds(dto.getIds());
    }

    @Override
    public SchoolClassMemoryVO detail(Object id){
        SchoolClassMemory schoolClassMemory = getById((Serializable) id);
        CommonResponseEnum.INVALID_ID.assertNull(schoolClassMemory);
        return BeanCopyUtils.copy(schoolClassMemory, SchoolClassMemoryVO.class);
    }

    @SneakyThrows
    @Override
    public void importExcel(ImportExcelDTO dto) {
        ExcelResult<SchoolClassMemoryImportDTO> excelResult = ExcelUtils.importExcel(dto.getFile().getInputStream(), SchoolClassMemoryImportDTO.class, true);
        List<SchoolClassMemoryImportDTO> list = excelResult.getList();
        List<String> errorList = excelResult.getErrorList();
        String analysis = excelResult.getAnalysis();
        System.out.println(" analysis : " + analysis);
        System.out.println(" isCover : " + dto.getIsCover());
    }

    @SneakyThrows
    @Override
    public void exportExcel(SchoolClassMemoryListDTO dto, HttpServletResponse response) {
        List<SchoolClassMemoryVO> list = list(dto);
        String fileName = "班级记忆表模板";
        OutputStream os = FileUtils.getOutputStream(response, fileName + ".xlsx");
        ExcelUtils.exportExcel(list, "班级记忆表", SchoolClassMemoryVO.class, os);
    }

    private static QueryWrapper buildQueryWrapper(SchoolClassMemoryListDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create().from(SchoolClassMemory.class);
        if (Utils.isNotNull(dto.getYear())) {
            wrapper.eq(SchoolClassMemory::getYear, dto.getYear());
        }
        if (Utils.isNotNull(dto.getClassNo())) {
            wrapper.eq(SchoolClassMemory::getClassNo, dto.getClassNo());
        }
        if (Utils.isNotNull(dto.getTeacherList())) {
            wrapper.eq(SchoolClassMemory::getTeacherList, dto.getTeacherList());
        }
        if (Utils.isNotNull(dto.getStudentList())) {
            wrapper.eq(SchoolClassMemory::getStudentList, dto.getStudentList());
        }
        if (Utils.isNotNull(dto.getImages())) {
            wrapper.eq(SchoolClassMemory::getImages, dto.getImages());
        }
        return wrapper;
    }
}