package com.sz.applet.miniBusiness.pojo.bo;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 校友申请认证表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Accessors(chain = true)
@Data
public class ApplyAuthBo {

    /**
     * ID
     */
    private Long id;

    /**
     * 批量审核ID列表
     */
    @Schema(description = "批量审核ID列表")
    private List<Long> ids;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @NotNull(message = "姓名不能为空")
    private String name;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @NotNull(message = "手机号不能为空")
    private String phone;

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


    @Schema(description = "毕业信息")
    private String graduateInfo;

    @Schema(description = "工作单位")
    private String workUnit;

    @Schema(description = "职务信息")
    private String jobInfo;

    @Schema(description = "在本校工作时间")
    private String workTime;


}
