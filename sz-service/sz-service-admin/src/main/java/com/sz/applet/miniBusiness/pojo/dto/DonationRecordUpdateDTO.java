package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * DonationRecord修改DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-26
 */
@Data
@Accessors(chain = true)
@Schema(description = "DonationRecord修改DTO")
public class DonationRecordUpdateDTO {

    @Schema(description =  "ID")
    private Long id;

    @Schema(description =  "用户ID")
    private Long userId;

    @Schema(description =  "项目ID")
    private Long projectId;

    @Schema(description =  "金额")
    private BigDecimal amount;

    @Schema(description =  "状态（1-待处理，2-处理中，3-处理完成）")
    private String status;

}