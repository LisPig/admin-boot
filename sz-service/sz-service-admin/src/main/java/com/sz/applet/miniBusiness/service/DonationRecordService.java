package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.po.DonationRecord;
import com.sz.core.common.entity.SelectIdsDTO;
import com.sz.core.common.entity.PageResult;
import java.util.List;
import com.sz.applet.miniBusiness.pojo.dto.DonationRecordCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationRecordUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationRecordListDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationPayDTO;
import com.sz.applet.miniBusiness.pojo.vo.DonationRecordVO;
import com.sz.core.common.entity.ImportExcelDTO;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <p>
 * 捐款记录 Service
 * </p>
 *
 * @author LisPig
 * @since 2025-10-26
 */
public interface DonationRecordService extends IService<DonationRecord> {

    void create(DonationRecordCreateDTO dto);

    void update(DonationRecordUpdateDTO dto);

    PageResult<DonationRecordVO> page(DonationRecordListDTO dto);

    List<DonationRecordVO> list(DonationRecordListDTO dto);

    void remove(SelectIdsDTO dto);

    DonationRecordVO detail(Object id);

    void importExcel(ImportExcelDTO dto);

    void exportExcel(DonationRecordListDTO dto, HttpServletResponse response);
    
    /**
     * 创建微信支付订单
     * @param dto 支付信息
     * @return packageVal 支付签名信息
     */
    String createWechatPay(DonationPayDTO dto);
}