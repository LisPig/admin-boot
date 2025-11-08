package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.DonationProjectService;
import com.sz.applet.miniBusiness.pojo.po.DonationProject;
import com.sz.applet.miniBusiness.mapper.DonationProjectMapper;
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
import com.sz.applet.miniBusiness.pojo.dto.DonationProjectCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationProjectUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationProjectListDTO;
import com.sz.applet.miniBusiness.pojo.vo.DonationProjectVO;

/**
 * <p>
 * 捐款项目 服务实现类
 * </p>
 *
 * @author LisPig
 * @since 2025-10-26
 */
@Service
@RequiredArgsConstructor
public class DonationProjectServiceImpl extends ServiceImpl<DonationProjectMapper, DonationProject> implements DonationProjectService {
    @Override
    public void create(DonationProjectCreateDTO dto){
        DonationProject donationProject = BeanCopyUtils.copy(dto, DonationProject.class);
        save(donationProject);
    }

    @Override
    public void update(DonationProjectUpdateDTO dto){
        DonationProject donationProject = BeanCopyUtils.copy(dto, DonationProject.class);
        QueryWrapper wrapper;
        // id有效性校验
        wrapper = QueryWrapper.create()
            .eq(DonationProject::getId, dto.getId());
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);

        saveOrUpdate(donationProject);
    }

    @Override
    public PageResult<DonationProjectVO> page(DonationProjectListDTO dto){
        Page<DonationProjectVO> page = pageAs(PageUtils.getPage(dto), buildQueryWrapper(dto), DonationProjectVO.class);
        return PageUtils.getPageResult(page);
    }

    @Override
    public List<DonationProjectVO> list(DonationProjectListDTO dto){
        return listAs(buildQueryWrapper(dto), DonationProjectVO.class);
    }

    @Override
    public void remove(SelectIdsDTO dto){
        CommonResponseEnum.INVALID_ID.assertTrue(dto.getIds().isEmpty());
        removeByIds(dto.getIds());
    }

    @Override
    public DonationProjectVO detail(Object id){
        DonationProject donationProject = getById((Serializable) id);
        CommonResponseEnum.INVALID_ID.assertNull(donationProject);
        return BeanCopyUtils.copy(donationProject, DonationProjectVO.class);
    }

    @Override
    public PageResult<DonationProjectVO> miniPage(DonationProjectListDTO dto) {
        QueryWrapper wrapper = this.buildQueryWrapper(dto);
        wrapper.eq(DonationProject::getStatus, "2");
        Page<DonationProjectVO> page = pageAs(PageUtils.getPage(dto), buildQueryWrapper(dto), DonationProjectVO.class);
        return PageUtils.getPageResult(page);
    }

    private static QueryWrapper buildQueryWrapper(DonationProjectListDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create().from(DonationProject.class);
        if (Utils.isNotNull(dto.getName())) {
            wrapper.like(DonationProject::getName, dto.getName());
        }
        if (Utils.isNotNull(dto.getPicture())) {
            wrapper.eq(DonationProject::getPicture, dto.getPicture());
        }
        if (Utils.isNotNull(dto.getDescription())) {
            wrapper.eq(DonationProject::getDescription, dto.getDescription());
        }
        if (Utils.isNotNull(dto.getAmount())) {
            wrapper.eq(DonationProject::getAmount, dto.getAmount());
        }
        if (Utils.isNotNull(dto.getStatus())) {
            wrapper.eq(DonationProject::getStatus, dto.getStatus());
        }
        return wrapper;
    }
}