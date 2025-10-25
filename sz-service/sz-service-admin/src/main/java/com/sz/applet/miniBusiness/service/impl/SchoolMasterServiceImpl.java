package com.sz.applet.miniBusiness.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.SchoolMasterService;
import com.sz.applet.miniBusiness.pojo.po.SchoolMaster;
import com.sz.applet.miniBusiness.mapper.SchoolMasterMapper;
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
import com.sz.applet.miniBusiness.pojo.dto.SchoolMasterCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolMasterUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolMasterListDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolMasterImportDTO;
import com.sz.core.common.entity.ImportExcelDTO;
import com.sz.excel.core.ExcelResult;
import java.io.OutputStream;
import jakarta.servlet.http.HttpServletResponse;
import com.sz.core.util.FileUtils;
import com.sz.excel.utils.ExcelUtils;
import lombok.SneakyThrows;
import com.sz.applet.miniBusiness.pojo.vo.SchoolMasterVO;

/**
 * <p>
 * 校长表 服务实现类
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
@Service
@RequiredArgsConstructor
public class SchoolMasterServiceImpl extends ServiceImpl<SchoolMasterMapper, SchoolMaster> implements SchoolMasterService {

    private final ObjectMapper objectMapper;
    @Override
    public void create(SchoolMasterCreateDTO dto){
        SchoolMaster schoolMaster = BeanCopyUtils.copy(dto, SchoolMaster.class);
        /*try{
            String history = objectMapper.writeValueAsString(dto.getHistory());
            schoolMaster.setHistory(history);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e.getMessage());
        }*/
        save(schoolMaster);
    }

    @Override
    public void update(SchoolMasterUpdateDTO dto){
        SchoolMaster schoolMaster = BeanCopyUtils.copy(dto, SchoolMaster.class);
        QueryWrapper wrapper;
        // id有效性校验
        wrapper = QueryWrapper.create()
            .eq(SchoolMaster::getId, dto.getId());
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);

        saveOrUpdate(schoolMaster);
    }

    @Override
    public PageResult<SchoolMasterVO> page(SchoolMasterListDTO dto){
        Page<SchoolMasterVO> page = pageAs(PageUtils.getPage(dto), buildQueryWrapper(dto), SchoolMasterVO.class);
        return PageUtils.getPageResult(page);
    }

    @Override
    public List<SchoolMasterVO> list(SchoolMasterListDTO dto){
        List<SchoolMasterVO> list = listAs(buildQueryWrapper(dto), SchoolMasterVO.class);
        /*for (SchoolMasterVO vo : list){
            vo.setHistoryDTO(this.translateHistory(vo.getHistory()));
        }*/
        return list;
    }

    @Override
    public void remove(SelectIdsDTO dto){
        CommonResponseEnum.INVALID_ID.assertTrue(dto.getIds().isEmpty());
        removeByIds(dto.getIds());
    }

    @Override
    public SchoolMasterVO detail(Object id){
        SchoolMaster schoolMaster = getById((Serializable) id);
        CommonResponseEnum.INVALID_ID.assertNull(schoolMaster);
        SchoolMasterVO vo = new SchoolMasterVO();
        BeanCopyUtils.copy(schoolMaster, vo);
        //vo.setHistoryDTO(this.translateHistory(schoolMaster.getHistory()));
        return vo;
    }

    @SneakyThrows
    @Override
    public void importExcel(ImportExcelDTO dto) {
        ExcelResult<SchoolMasterImportDTO> excelResult = ExcelUtils.importExcel(dto.getFile().getInputStream(), SchoolMasterImportDTO.class, true);
        List<SchoolMasterImportDTO> list = excelResult.getList();
        List<String> errorList = excelResult.getErrorList();
        String analysis = excelResult.getAnalysis();
        System.out.println(" analysis : " + analysis);
        System.out.println(" isCover : " + dto.getIsCover());
    }

    @SneakyThrows
    @Override
    public void exportExcel(SchoolMasterListDTO dto, HttpServletResponse response) {
        List<SchoolMasterVO> list = list(dto);
        String fileName = "校长表模板";
        OutputStream os = FileUtils.getOutputStream(response, fileName + ".xlsx");
        ExcelUtils.exportExcel(list, "校长表", SchoolMasterVO.class, os);
    }

    private static QueryWrapper buildQueryWrapper(SchoolMasterListDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create().from(SchoolMaster.class);
        if (Utils.isNotNull(dto.getName())) {
            wrapper.like(SchoolMaster::getName, dto.getName());
        }
        if (Utils.isNotNull(dto.getAvatar())) {
            wrapper.eq(SchoolMaster::getAvatar, dto.getAvatar());
        }
        return wrapper;
    }


    /*private SchoolMasterCreateDTO.HistoryDTO translateHistory(String history) {
        try{
            SchoolMasterCreateDTO.HistoryDTO historyDTO = objectMapper.readValue(history, SchoolMasterCreateDTO.HistoryDTO.class);
            return historyDTO;
        }catch (Exception e){
            throw new RuntimeException("数据转换失败");
        }
    }*/
}