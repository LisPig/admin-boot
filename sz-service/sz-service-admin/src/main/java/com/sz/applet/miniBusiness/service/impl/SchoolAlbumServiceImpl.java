package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.applet.miniBusiness.pojo.po.SchoolAlbumChild;
import com.sz.applet.miniBusiness.pojo.vo.SchoolAlbumVO;
import com.sz.applet.miniBusiness.service.SchoolAlbumChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.SchoolAlbumService;
import com.sz.applet.miniBusiness.pojo.po.SchoolAlbum;
import com.sz.applet.miniBusiness.mapper.SchoolAlbumMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.util.PageUtils;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.Utils;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import java.io.Serializable;
import java.util.List;
import com.sz.applet.miniBusiness.pojo.dto.SchoolAlbumCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolAlbumUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolAlbumListDTO;
import com.sz.applet.miniBusiness.pojo.dto.SchoolAlbumImportDTO;
import com.sz.core.common.entity.ImportExcelDTO;
import com.sz.excel.core.ExcelResult;
import java.io.OutputStream;
import jakarta.servlet.http.HttpServletResponse;
import com.sz.core.util.FileUtils;
import com.sz.excel.utils.ExcelUtils;
import lombok.SneakyThrows;

/**
 * <p>
 * 相册表 服务实现类
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
@Service
@RequiredArgsConstructor
public class SchoolAlbumServiceImpl extends ServiceImpl<SchoolAlbumMapper, SchoolAlbum> implements SchoolAlbumService {

    private final SchoolAlbumChildService schoolAlbumChildService;

    @Override
    public void create(SchoolAlbumCreateDTO dto){
        SchoolAlbum schoolAlbum = BeanCopyUtils.copy(dto, SchoolAlbum.class);
        save(schoolAlbum);
    }

    @Override
    public void update(SchoolAlbumUpdateDTO dto){
        SchoolAlbum schoolAlbum = BeanCopyUtils.copy(dto, SchoolAlbum.class);
        QueryWrapper wrapper;
        // id有效性校验
        wrapper = QueryWrapper.create()
            .eq(SchoolAlbum::getId, dto.getId());
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);

        saveOrUpdate(schoolAlbum);
    }

    @Override
    public PageResult<SchoolAlbumVO> page(SchoolAlbumListDTO dto){
        QueryWrapper queryWrapper = buildQueryWrapper(dto);
        // 联表schoolAlbumchild 查询子项数量

        Page<SchoolAlbumVO> page = pageAs(PageUtils.getPage(dto), buildQueryWrapper(dto), SchoolAlbumVO.class);
        for(SchoolAlbumVO vo:page.getRecords()){
            vo.setChildCount(schoolAlbumChildService.count(new QueryWrapper().eq(SchoolAlbumChild::getAlbumId,vo.getId())));
        }
        return PageUtils.getPageResult(page);
    }

    @Override
    public List<SchoolAlbumVO> list(SchoolAlbumListDTO dto){
        return listAs(buildQueryWrapper(dto), SchoolAlbumVO.class);
    }

    @Override
    public void remove(SelectIdsDTO dto){
        CommonResponseEnum.INVALID_ID.assertTrue(dto.getIds().isEmpty());
        removeByIds(dto.getIds());
    }

    @Override
    public SchoolAlbumVO detail(Object id){
        SchoolAlbum schoolAlbum = getById((Serializable) id);
        CommonResponseEnum.INVALID_ID.assertNull(schoolAlbum);
        return BeanCopyUtils.copy(schoolAlbum, SchoolAlbumVO.class);
    }

    @SneakyThrows
    @Override
    public void importExcel(ImportExcelDTO dto) {
        ExcelResult<SchoolAlbumImportDTO> excelResult = ExcelUtils.importExcel(dto.getFile().getInputStream(), SchoolAlbumImportDTO.class, true);
        List<SchoolAlbumImportDTO> list = excelResult.getList();
        List<String> errorList = excelResult.getErrorList();
        String analysis = excelResult.getAnalysis();
        System.out.println(" analysis : " + analysis);
        System.out.println(" isCover : " + dto.getIsCover());
    }

    @SneakyThrows
    @Override
    public void exportExcel(SchoolAlbumListDTO dto, HttpServletResponse response) {
        List<SchoolAlbumVO> list = list(dto);
        String fileName = "相册表模板";
        OutputStream os = FileUtils.getOutputStream(response, fileName + ".xlsx");
        ExcelUtils.exportExcel(list, "相册表", SchoolAlbumVO.class, os);
    }

    private static QueryWrapper buildQueryWrapper(SchoolAlbumListDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create().from(SchoolAlbum.class);
        if (Utils.isNotNull(dto.getTitle())) {
            wrapper.like(SchoolAlbum::getTitle, dto.getTitle());
        }
        if (Utils.isNotNull(dto.getCover())) {
            wrapper.eq(SchoolAlbum::getCover, dto.getCover());
        }
        if (Utils.isNotNull(dto.getCategory())) {
            wrapper.eq(SchoolAlbum::getCategory, dto.getCategory());
        }
        if (Utils.isNotNull(dto.getContent())) {
            wrapper.eq(SchoolAlbum::getContent, dto.getContent());
        }
        return wrapper;
    }
}