package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 捐赠支付DTO
 *
 * @author sz
 * @since 2025-10-26
 */
@Data
@Schema(description = "捐赠支付DTO")
public class DonationPayDTO {

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "用户openid")
    private String openid;

    @Schema(description = "支付金额")
    private BigDecimal amount;

    @Schema(description = "项目名称")
    private String projectName;
}