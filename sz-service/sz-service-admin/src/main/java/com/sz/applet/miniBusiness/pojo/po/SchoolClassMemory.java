package com.sz.applet.miniBusiness.pojo.po;

import com.mybatisflex.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.io.Serial;
import com.sz.mysql.EntityChangeListener;
import java.time.LocalDateTime;

/**
* <p>
* 班级记忆表(定格青春)
* </p>
*
* @author lee
* @since 2026-01-06
*/
@Data
@Accessors(chain = true)
@Table(value = "school_class_memory", onInsert = EntityChangeListener.class, onUpdate = EntityChangeListener.class)
@Schema(description = "班级记忆表(定格青春)")
public class SchoolClassMemory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @Schema(description ="ID")
    private Long id;

    @Schema(description ="标题")
    @Column(value = "title")
    private String title;

    @Schema(description ="封面")
    @Column(value = "cover")
    private String cover;

    @Schema(description ="届数")
    private String year;

    @Schema(description ="班级编号")
    private String classNo;

    @Schema(description ="教师列表(json数组)")
    private String teacherList;

    @Schema(description ="学生列表(逗号分割)")
    private String studentList;

    @Schema(description ="图片列表(逗号分隔)")
    private String images;

    @Column(isLogicDelete = true)
    @Schema(description ="删除标识")
    private String delFlag;

    @Schema(description ="创建时间")
    private LocalDateTime createTime;

    @Schema(description ="更新时间")
    private LocalDateTime updateTime;

    @Schema(description ="创建人ID")
    private Long createId;

    @Schema(description ="更新人ID")
    private Long updateId;

}