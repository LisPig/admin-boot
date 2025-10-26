package com.sz.applet.miniBusiness.pojo.vo;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniPassApplication;
import com.sz.core.common.entity.PageQuery;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 校友通行证申请表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Accessors(chain = true)
@Data
@AutoMapper(target = AppletAlumniPassApplication.class)
public class AppletAlumniPassApplicationVO extends PageQuery {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户ID（关联用户表的外键）
     */
    @Schema(description = "user_id")
    private Long userId;

    /**
     * 申请人姓名
     */
    @Schema(description = "申请人姓名")
    @NotNull(message = "请填写申请人姓名")
    private String name;

    /**
     * 电话号码
     */
    @Schema(description = "手机号")
    @NotNull(message = "请填写手机号码")
    private String phone;

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
     * 返校原因
     */
    @Schema(description = "返校原因")
    private String reason;

    /**
     * 其他原因详情
     */
    @Schema(description = "其他原因")
    private String otherReason;

    /**
     * 预计返校时间
     */
    @Schema(description = "预计返校时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expectedTime;


    private Integer status;

}