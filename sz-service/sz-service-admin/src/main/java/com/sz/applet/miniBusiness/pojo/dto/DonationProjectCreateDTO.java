package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * DonationProject添加DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-26
 */
@Data
@Accessors(chain = true)
@Schema(description = "DonationProject添加DTO")
public class DonationProjectCreateDTO {

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

   @Schema(description =  "开始时间")
   private LocalDateTime startTime;

   @Schema(description =  "结束时间")
    private LocalDateTime endTime;

}