package com.sz.applet.miniBusiness.pojo.bo;

import com.sz.core.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.lang.Long;
import java.util.Date;
import java.lang.Object;
import java.lang.String;

/**
 * 校友卡表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Schema(description = "校友卡表")
public class AppletAlumniCardBo extends PageQuery {

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String name;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 返校理由
     */
    @Schema(description = "返校理由")
    private String reason;

    /**
     * 预计返校时间
     */
    @Schema(description = "预计返校时间")
    private Date returnTime;

    /**
     * 状态（1-待审核，2-已通过，3-未通过）
     */
    @Schema(description = "状态（1-待审核，2-已通过，3-未通过）")
    private String status;

    /**
     * 审核时间
     */
    @Schema(description = "审核时间")
    private Date approveTime;

    /**
     * 审核备注
     */
    @Schema(description = "审核备注")
    private String approveRemark;

    /**
     * 二维码地址
     */
    @Schema(description = "二维码地址")
    private String qrCode;

    /**
     * 删除标识
     */
    @Schema(description = "删除标识")
    private Object delFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createId;

    /**
     * 更新人ID
     */
    @Schema(description = "更新人ID")
    private Long updateId;

    /**
     * 审核人ID
     */
    @Schema(description = "审核人ID")
    private Long approveId;

}