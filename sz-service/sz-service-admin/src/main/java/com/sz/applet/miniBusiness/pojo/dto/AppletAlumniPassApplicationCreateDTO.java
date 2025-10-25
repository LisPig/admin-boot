package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Schema(description = "AppletAlumniPassApplication添加DTO")
public class AppletAlumniPassApplicationCreateDTO {

    private Long id;

    @Schema(description =  "用户ID（关联用户表的外键）")
    private Long userId;

    @Schema(description =  "申请人姓名")
    private String name;

    @Schema(description =  "电话号码")
    private String phone;

    @Schema(description =  "毕业年份")
    private Integer year;

    @Schema(description =  "班级编号")
    private String classNo;

    @Schema(description =  "返校原因")
    private String reason;

    @Schema(description =  "其他原因详情")
    private String otherReason;

    @Schema(description =  "预计返校时间")
    private LocalDate expectedTime;

    @Schema(description =  "申请时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applicationTime;

    @Schema(description =  "申请状态（0-待审核，1-已批准，2-已拒绝）")
    private Integer status;

    @Schema(description =  "使用状态（0-已归还，1-待归还，2-未归还，3-待处理）")
    private Integer useStatus;

    @Schema(description =  "审批人ID")
    private Long approverId;

    @Schema(description =  "审批时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approveTime;

    @Schema(description =  "审批备注")
    private String approveRemark;

    @Schema(description =  "二维码标识")
    private String qrCode;

}
