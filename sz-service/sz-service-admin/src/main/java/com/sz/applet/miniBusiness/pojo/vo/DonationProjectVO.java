package com.sz.applet.miniBusiness.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sz.applet.miniBusiness.translation.DistanceEndDaysTranslationImpl;
import com.sz.core.common.translate.Translate;
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

    @Schema(description =  "参与人数")
    private Integer joinNum;

    @Schema(description =  "距离结束天数")
    //@Translate(translator = DistanceEndDaysTranslationImpl.class, sourceField = "startAndEndTimeStr")
    private Integer distanceEndDays;

    @Schema(description =  "开始时间")
    private LocalDateTime startTime;

    @Schema(description =  "结束时间")
    private LocalDateTime endTime;

    @JsonIgnore
    private String startAndEndTimeStr;

    @Schema(description =  "创建时间")
    private LocalDateTime createTime;

    @Schema(description =  "更新时间")
    private LocalDateTime updateTime;

    @Schema(description =  "创建人ID")
    private Long createId;

    @Schema(description =  "更新人ID")
    private Long updateId;

    private String getStartAndEndTimeStr(){
        return this.startTime + ","+ this.endTime;
    }

}