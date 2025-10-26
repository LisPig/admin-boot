package com.sz.applet.miniBusiness.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * DonationProject返回vo
 * </p>
 *
 * @author LisPig
 * @since 2025-10-26
 */
@Data
@Accessors(chain = true)
@Schema(description = "DonationProject返回vo")
public class DonationProjectVO {

    @Schema(description =  "ID")
    private Long id;

    @Schema(description =  "名称")
    private String name;

    @Schema(description =  "图片")
    private String picture;

    @Schema(description =  "描述")
    private String description;

    @Schema(description =  "金额")
    private BigDecimal amount;

    @Schema(description =  "状态（1-待审核，2-审核通过，3-审核未通过）")
    private String status;

    @Schema(description =  "创建时间")
    private LocalDateTime createTime;

    @Schema(description =  "更新时间")
    private LocalDateTime updateTime;

    @Schema(description =  "创建人ID")
    private Long createId;

    @Schema(description =  "更新人ID")
    private Long updateId;

}