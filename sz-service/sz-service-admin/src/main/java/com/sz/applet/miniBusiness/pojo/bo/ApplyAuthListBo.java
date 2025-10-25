package com.sz.applet.miniBusiness.pojo.bo;

import com.sz.core.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * Article查询BO
 * </p>
 *
 * @author sz
 * @since 2025-09-12
 */
@Data
@Schema(description = "Article查询BO")
public class ApplyAuthListBo extends PageQuery {

    @Schema(description = "姓名")
    private String name;

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

}