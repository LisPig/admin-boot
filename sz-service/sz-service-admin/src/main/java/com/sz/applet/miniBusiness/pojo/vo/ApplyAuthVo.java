package com.sz.applet.miniBusiness.pojo.vo;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 校友申请认证表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Accessors(chain = true)
@Data
public class ApplyAuthVo {

    /**
     * ID
     */
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
     * 身份（1-校友，2-教师）
     */
    @Schema(description = "身份（1-校友，2-教师）")
    private Integer identity;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String idCard;

    /**
     * 学号
     */
    @Schema(description = "学号")
    private String studentId;

    /**
     * 毕业年份
     */
    @Schema(description = "毕业年份")
    private Integer year;

    /**
     * 班级编号
     */
    @Schema(description = "班级编号")
    private String classNo;

    /**
     * 教师编号
     */
    @Schema(description = "教师编号")
    private String teacherId;

    /**
     * 状态（1-待审核，2-已通过，3-未通过）
     */
    @Schema(description = "状态（1-待审核，2-已通过，3-未通过）")
    private String status;


    /**
     * 审核备注
     */
    @Schema(description = "审核备注")
    private String approveRemark;


    /**
     * 审核时间
     */
    @Schema(description = "审核时间")
    private Date approveTime;

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
    private Date updateTime;

    /**
     * 创建人ID
     */
    private Long createId;

    /**
     * 更新人ID
     */
    private Long updateId;

    /**
     * 审核人ID
     */
    private Long approveId;


}
