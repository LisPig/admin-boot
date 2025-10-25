package com.sz.applet.miniBusiness.service.impl;


import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.bo.AppletAlumniPassApplicationBo;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniPassApplicationCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.AppletAlumniPassApplicationListDTO;
import com.sz.applet.miniBusiness.pojo.vo.AppletAlumniPassApplicationVO;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.PageUtils;
import com.sz.core.util.Utils;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.AppletAlumniPassApplicationService;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniPassApplication;
import com.sz.applet.miniBusiness.mapper.AppletAlumniPassApplicationMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.List;

/**
 * 校友通行证申请表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class AppletAlumniPassApplicationServiceImpl extends ServiceImpl<AppletAlumniPassApplicationMapper, AppletAlumniPassApplication> implements AppletAlumniPassApplicationService {

    @Override
    public void create(AppletAlumniPassApplicationCreateDTO dto){
        AppletAlumniPassApplication appletAlumniPassApplication = BeanCopyUtils.copy(dto, AppletAlumniPassApplication.class);
        save(appletAlumniPassApplication);
    }

    @Override
    public void update(AppletAlumniPassApplicationCreateDTO dto){
        AppletAlumniPassApplication appletAlumniPassApplication = BeanCopyUtils.copy(dto, AppletAlumniPassApplication.class);
        QueryWrapper wrapper;
        // id有效性校验
        wrapper = QueryWrapper.create()
                .eq(AppletAlumniPassApplication::getId, dto.getId());
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);

        saveOrUpdate(appletAlumniPassApplication);
    }

    @Override
    public List<AppletAlumniPassApplicationVO> list(AppletAlumniPassApplicationListDTO dto){
        return listAs(buildQueryWrapper(dto), AppletAlumniPassApplicationVO.class);
    }

    @Override
    public void remove(SelectIdsDTO dto){
        CommonResponseEnum.INVALID_ID.assertTrue(dto.getIds().isEmpty());
        removeByIds(dto.getIds());
    }

    @Override
    public AppletAlumniPassApplicationVO detail(Object id){
        AppletAlumniPassApplication appletAlumniPassApplication = getById((Serializable) id);
        CommonResponseEnum.INVALID_ID.assertNull(appletAlumniPassApplication);
        return BeanCopyUtils.copy(appletAlumniPassApplication, AppletAlumniPassApplicationVO.class);
    }

    @Override
    public PageResult<AppletAlumniPassApplicationVO> page(AppletAlumniPassApplicationBo bo) {
        QueryWrapper queryWrapper = buildQueryWrapper(bo);
        return PageUtils.getPageResult(pageAs(PageUtils.getPage(bo), queryWrapper, AppletAlumniPassApplicationVO.class));
    }


    private QueryWrapper buildQueryWrapper(AppletAlumniPassApplicationBo bo) {
        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.eq(AppletAlumniPassApplication::getId, bo.getId());
        queryWrapper.eq(AppletAlumniPassApplication::getUserId, bo.getUserId());
        queryWrapper.eq(AppletAlumniPassApplication::getName, bo.getName());
        queryWrapper.eq(AppletAlumniPassApplication::getPhone, bo.getPhone());
        queryWrapper.eq(AppletAlumniPassApplication::getYear, bo.getYear())
                .eq(AppletAlumniPassApplication::getClassNo, bo.getClassNo());

        return queryWrapper ;

    }


    private static QueryWrapper buildQueryWrapper(AppletAlumniPassApplicationListDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create().from(AppletAlumniPassApplication.class);
        if (Utils.isNotNull(dto.getUserId())) {
            wrapper.eq(AppletAlumniPassApplication::getUserId, dto.getUserId());
        }
        if (Utils.isNotNull(dto.getName())) {
            wrapper.like(AppletAlumniPassApplication::getName, dto.getName());
        }
        if (Utils.isNotNull(dto.getPhone())) {
            wrapper.eq(AppletAlumniPassApplication::getPhone, dto.getPhone());
        }
        if (Utils.isNotNull(dto.getYear())) {
            wrapper.eq(AppletAlumniPassApplication::getYear, dto.getYear());
        }
        if (Utils.isNotNull(dto.getClassNo())) {
            wrapper.eq(AppletAlumniPassApplication::getClassNo, dto.getClassNo());
        }
        if (Utils.isNotNull(dto.getReason())) {
            wrapper.eq(AppletAlumniPassApplication::getReason, dto.getReason());
        }
        if (Utils.isNotNull(dto.getOtherReason())) {
            wrapper.eq(AppletAlumniPassApplication::getOtherReason, dto.getOtherReason());
        }
        if (Utils.isNotNull(dto.getExpectedTimeStart()) && Utils.isNotNull(dto.getExpectedTimeEnd())) {
            wrapper.between(AppletAlumniPassApplication::getExpectedTime, dto.getExpectedTimeStart(), dto.getExpectedTimeEnd());
        }
        if (Utils.isNotNull(dto.getApplicationTimeStart()) && Utils.isNotNull(dto.getApplicationTimeEnd())) {
            wrapper.between(AppletAlumniPassApplication::getApplicationTime, dto.getApplicationTimeStart(), dto.getApplicationTimeEnd());
        }
        if (Utils.isNotNull(dto.getStatus())) {
            wrapper.eq(AppletAlumniPassApplication::getStatus, dto.getStatus());
        }
        if (Utils.isNotNull(dto.getUseStatus())) {
            wrapper.eq(AppletAlumniPassApplication::getUseStatus, dto.getUseStatus());
        }
        if (Utils.isNotNull(dto.getApproverId())) {
            wrapper.eq(AppletAlumniPassApplication::getApproverId, dto.getApproverId());
        }
        if (Utils.isNotNull(dto.getApproveTimeStart()) && Utils.isNotNull(dto.getApproveTimeEnd())) {
            wrapper.between(AppletAlumniPassApplication::getApproveTime, dto.getApproveTimeStart(), dto.getApproveTimeEnd());
        }
        if (Utils.isNotNull(dto.getApproveRemark())) {
            wrapper.eq(AppletAlumniPassApplication::getApproveRemark, dto.getApproveRemark());
        }
        if (Utils.isNotNull(dto.getQrCode())) {
            wrapper.eq(AppletAlumniPassApplication::getQrCode, dto.getQrCode());
        }
        return wrapper;
    }
}