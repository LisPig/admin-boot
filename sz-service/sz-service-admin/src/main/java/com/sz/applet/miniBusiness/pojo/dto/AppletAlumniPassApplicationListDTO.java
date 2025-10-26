package com.sz.applet.miniBusiness.pojo.dto;

import com.sz.core.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Schema(description = "AppletAlumniPassApplication查询DTO")
public class AppletAlumniPassApplicationListDTO extends PageQuery {

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

    @Schema(description =  "预计返校时间开始")
    private Instant expectedTimeStart;

    @Schema(description =  "预计返校时间结束")
    private Instant expectedTimeEnd;

    @Schema(description =  "申请时间开始")
    private Instant applicationTimeStart;

    @Schema(description =  "申请时间结束")
    private Instant applicationTimeEnd;

    @Schema(description =  "申请状态（0-待审核，1-已批准，2-已拒绝）")
    private Integer status;

    @Schema(description =  "使用状态（0-已归还，1-待归还，2-未归还，3-待处理）")
    private Integer useStatus;

    @Schema(description =  "审批人ID")
    private Long approverId;

    @Schema(description =  "审批时间开始")
    private Instant approveTimeStart;

    @Schema(description =  "审批时间结束")
    private Instant approveTimeEnd;

    @Schema(description =  "审批备注")
    private String approveRemark;

    @Schema(description =  "二维码标识")
    private String qrCode;

}