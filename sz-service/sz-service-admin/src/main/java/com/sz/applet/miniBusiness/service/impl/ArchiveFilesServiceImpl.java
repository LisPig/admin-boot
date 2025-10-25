package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.ArchiveFilesService;
import com.sz.applet.miniBusiness.pojo.po.ArchiveFiles;
import com.sz.applet.miniBusiness.mapper.ArchiveFilesMapper;
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
import com.sz.applet.miniBusiness.pojo.dto.ArchiveFilesCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.ArchiveFilesUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.ArchiveFilesListDTO;
import com.sz.applet.miniBusiness.pojo.vo.ArchiveFilesVO;

/**
 * <p>
 * 档案申请记录表 服务实现类
 * </p>
 *
 * @author LisPig
 * @since 2025-10-25
 */
@Service
@RequiredArgsConstructor
public class ArchiveFilesServiceImpl extends ServiceImpl<ArchiveFilesMapper, ArchiveFiles> implements ArchiveFilesService {
    @Override
    public void create(ArchiveFilesCreateDTO dto){
        ArchiveFiles archiveFiles = BeanCopyUtils.copy(dto, ArchiveFiles.class);
        save(archiveFiles);
    }

    @Override
    public void update(ArchiveFilesUpdateDTO dto){
        ArchiveFiles archiveFiles = BeanCopyUtils.copy(dto, ArchiveFiles.class);
        QueryWrapper wrapper;
        // id有效性校验
        wrapper = QueryWrapper.create()
            .eq(ArchiveFiles::getId, dto.getId());
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);

        saveOrUpdate(archiveFiles);
    }

    @Override
    public PageResult<ArchiveFilesVO> page(ArchiveFilesListDTO dto){
        Page<ArchiveFilesVO> page = pageAs(PageUtils.getPage(dto), buildQueryWrapper(dto), ArchiveFilesVO.class);
        return PageUtils.getPageResult(page);
    }

    @Override
    public List<ArchiveFilesVO> list(ArchiveFilesListDTO dto){
        return listAs(buildQueryWrapper(dto), ArchiveFilesVO.class);
    }

    @Override
    public void remove(SelectIdsDTO dto){
        CommonResponseEnum.INVALID_ID.assertTrue(dto.getIds().isEmpty());
        removeByIds(dto.getIds());
    }

    @Override
    public ArchiveFilesVO detail(Object id){
        ArchiveFiles archiveFiles = getById((Serializable) id);
        CommonResponseEnum.INVALID_ID.assertNull(archiveFiles);
        return BeanCopyUtils.copy(archiveFiles, ArchiveFilesVO.class);
    }

    private static QueryWrapper buildQueryWrapper(ArchiveFilesListDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create().from(ArchiveFiles.class);
        if (Utils.isNotNull(dto.getUserId())) {
            wrapper.eq(ArchiveFiles::getUserId, dto.getUserId());
        }
        if (Utils.isNotNull(dto.getTimeStart()) && Utils.isNotNull(dto.getTimeEnd())) {
            wrapper.between(ArchiveFiles::getTime, dto.getTimeStart(), dto.getTimeEnd());
        }
        if (Utils.isNotNull(dto.getPhone())) {
            wrapper.eq(ArchiveFiles::getPhone, dto.getPhone());
        }
        if (Utils.isNotNull(dto.getName())) {
            wrapper.like(ArchiveFiles::getName, dto.getName());
        }
        if (Utils.isNotNull(dto.getCard())) {
            wrapper.eq(ArchiveFiles::getCard, dto.getCard());
        }
        if (Utils.isNotNull(dto.getCompany())) {
            wrapper.eq(ArchiveFiles::getCompany, dto.getCompany());
        }
        if (Utils.isNotNull(dto.getMark())) {
            wrapper.eq(ArchiveFiles::getMark, dto.getMark());
        }
        if (Utils.isNotNull(dto.getIdea())) {
            wrapper.eq(ArchiveFiles::getIdea, dto.getIdea());
        }
        if (Utils.isNotNull(dto.getUserNum())) {
            wrapper.eq(ArchiveFiles::getUserNum, dto.getUserNum());
        }
        if (Utils.isNotNull(dto.getCardImg())) {
            wrapper.eq(ArchiveFiles::getCardImg, dto.getCardImg());
        }
        if (Utils.isNotNull(dto.getYear())) {
            wrapper.eq(ArchiveFiles::getYear, dto.getYear());
        }
        if (Utils.isNotNull(dto.getClassNo())) {
            wrapper.eq(ArchiveFiles::getClassNo,dto.getClassNo());
        }
        if (Utils.isNotNull(dto.getPass())) {
            wrapper.eq(ArchiveFiles::getPass, dto.getPass());
        }
        if (Utils.isNotNull(dto.getPassMark())) {
            wrapper.eq(ArchiveFiles::getPassMark, dto.getPassMark());
        }
        if (Utils.isNotNull(dto.getState())) {
            wrapper.eq(ArchiveFiles::getState, dto.getState());
        }
        return wrapper;
    }
}