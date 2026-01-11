package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sz.applet.miniBusiness.service.DonationRecordService;
import com.sz.applet.miniBusiness.pojo.po.DonationRecord;
import com.sz.applet.miniBusiness.mapper.DonationRecordMapper;
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
import com.sz.applet.miniBusiness.pojo.dto.DonationRecordCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationRecordUpdateDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationRecordListDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationRecordImportDTO;
import com.sz.applet.miniBusiness.pojo.dto.DonationPayDTO;
import com.sz.core.common.entity.ImportExcelDTO;
import com.sz.excel.core.ExcelResult;
import java.io.OutputStream;
import jakarta.servlet.http.HttpServletResponse;
import com.sz.core.util.FileUtils;
import com.sz.excel.utils.ExcelUtils;
import lombok.SneakyThrows;
import com.sz.applet.miniBusiness.pojo.vo.DonationRecordVO;
import com.sz.wechat.payment.WechatPayService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 捐款记录 服务实现类
 * </p>
 *
 * @author LisPig
 * @since 2025-10-26
 */
@Service
@RequiredArgsConstructor
public class DonationRecordServiceImpl extends ServiceImpl<DonationRecordMapper, DonationRecord> implements DonationRecordService {
    
    private final WechatPayService wechatPayService;
    
    @Override
    public void create(DonationRecordCreateDTO dto){
        DonationRecord donationRecord = BeanCopyUtils.copy(dto, DonationRecord.class);
        save(donationRecord);
    }

    @Override
    public void update(DonationRecordUpdateDTO dto){
        DonationRecord donationRecord = BeanCopyUtils.copy(dto, DonationRecord.class);
        QueryWrapper wrapper;
        // id有效性校验
        wrapper = QueryWrapper.create()
            .eq(DonationRecord::getId, dto.getId());
        CommonResponseEnum.INVALID_ID.assertTrue(count(wrapper) <= 0);

        saveOrUpdate(donationRecord);
    }

    @Override
    public PageResult<DonationRecordVO> page(DonationRecordListDTO dto){
        Page<DonationRecordVO> page = pageAs(PageUtils.getPage(dto), buildQueryWrapper(dto), DonationRecordVO.class);
        return PageUtils.getPageResult(page);
    }

    @Override
    public List<DonationRecordVO> list(DonationRecordListDTO dto){
        return listAs(buildQueryWrapper(dto), DonationRecordVO.class);
    }

    @Override
    public void remove(SelectIdsDTO dto){
        CommonResponseEnum.INVALID_ID.assertTrue(dto.getIds().isEmpty());
        removeByIds(dto.getIds());
    }

    @Override
    public DonationRecordVO detail(Object id){
        DonationRecord donationRecord = getById((Serializable) id);
        CommonResponseEnum.INVALID_ID.assertNull(donationRecord);
        return BeanCopyUtils.copy(donationRecord, DonationRecordVO.class);
    }

    @SneakyThrows
    @Override
    public void importExcel(ImportExcelDTO dto) {
        ExcelResult<DonationRecordImportDTO> excelResult = ExcelUtils.importExcel(dto.getFile().getInputStream(), DonationRecordImportDTO.class, true);
        List<DonationRecordImportDTO> list = excelResult.getList();
        List<String> errorList = excelResult.getErrorList();
        String analysis = excelResult.getAnalysis();
        System.out.println(" analysis : " + analysis);
        System.out.println(" isCover : " + dto.getIsCover());
    }

    @SneakyThrows
    @Override
    public void exportExcel(DonationRecordListDTO dto, HttpServletResponse response) {
        List<DonationRecordVO> list = list(dto);
        String fileName = "捐款记录模板";
        OutputStream os = FileUtils.getOutputStream(response, fileName + ".xlsx");
        ExcelUtils.exportExcel(list, "捐款记录", DonationRecordVO.class, os);
    }

    @Override
    public String createWechatPay(DonationPayDTO dto) {
        // 生成商户订单号
        String outTradeNo = "DONATION_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        
        // 创建微信支付订单
        String packageVal = wechatPayService.createJsapiOrder(
                outTradeNo,
                dto.getAmount(),
                "捐款-" + dto.getProjectName(),
                dto.getOpenid(),
                "projectId=" + dto.getProjectId()
        );
        
        // 返回调起支付所需的参数
        return packageVal;
    }

    private static QueryWrapper buildQueryWrapper(DonationRecordListDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create().from(DonationRecord.class);
        if (Utils.isNotNull(dto.getUserId())) {
            wrapper.eq(DonationRecord::getUserId, dto.getUserId());
        }
        if (Utils.isNotNull(dto.getProjectId())) {
            wrapper.eq(DonationRecord::getProjectId, dto.getProjectId());
        }
        if (Utils.isNotNull(dto.getAmount())) {
            wrapper.eq(DonationRecord::getAmount, dto.getAmount());
        }
        if (Utils.isNotNull(dto.getStatus())) {
            wrapper.eq(DonationRecord::getStatus, dto.getStatus());
        }
        return wrapper;
    }
}