package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import cn.idev.excel.annotation.ExcelProperty;
import com.sz.excel.annotation.DictFormat;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * <p>
 * DonationRecord导入DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-26
 */
@Data
@Accessors(chain = true)
@Schema(description = "DonationRecord导入DTO")
public class DonationRecordImportDTO {

    @ExcelProperty(value = "用户ID")
    @Schema(description =  "用户ID")
    private Long userId;

    @ExcelProperty(value = "项目ID")
    @Schema(description =  "项目ID")
    private Long projectId;

    @ExcelProperty(value = "金额")
    @Schema(description =  "金额")
    private BigDecimal amount;

    @ExcelProperty(value = "状态（1-待处理，2-处理中，3-处理完成）")
    @Schema(description =  "状态（1-待处理，2-处理中，3-处理完成）")
    private String status;

}