package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.po.DonationProject;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.entity.PageResult;
import java.util.List;
import com.sz.applet.miniBusiness.pojo.dto.DonationProjectCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationProjectUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationProjectListDTO;
import com.sz.applet.miniBusiness.pojo.vo.DonationProjectVO;

/**
 * <p>
 * 捐款项目 Service
 * </p>
 *
 * @author LisPig
 * @since 2025-10-26
 */
public interface DonationProjectService extends IService<DonationProject> {

    void create(DonationProjectCreateDTO dto);

    void update(DonationProjectUpdateDTO dto);

    PageResult<DonationProjectVO> page(DonationProjectListDTO dto);

    List<DonationProjectVO> list(DonationProjectListDTO dto);

    void remove(SelectIdsDTO dto);

    DonationProjectVO detail(Object id);

    PageResult<DonationProjectVO> miniPage(DonationProjectListDTO dto);
}