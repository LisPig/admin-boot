package com.sz.applet.miniBusiness.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import com.sz.excel.annotation.DictFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * SchoolClassMemory返回vo
 * </p>
 *
 * @author lee
 * @since 2026-01-06
 */
@Data
@Accessors(chain = true)
@Schema(description = "SchoolClassMemory返回vo")
public class SchoolClassMemoryVO {

    @ExcelIgnore
    @Schema(description =  "ID")
    private Long id;

    @Schema(description =  "标题")
    private String title;

    @Schema(description =  "封面")
    private String cover;

    @ExcelProperty(value = "届数")
    @Schema(description =  "届数")
    private String year;

    @ExcelProperty(value = "班级编号")
    @Schema(description =  "班级编号")
    private String classNo;

    @ExcelProperty(value = "教师列表(json数组)")
    @Schema(description =  "教师列表(json数组)")
    private String teacherList;

    @ExcelProperty(value = "学生列表(逗号分割)")
    @Schema(description =  "学生列表(逗号分割)")
    private String studentList;

    @ExcelProperty(value = "图片列表(逗号分隔)")
    @Schema(description =  "图片列表(逗号分隔)")
    private String images;

    @ExcelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description =  "创建时间")
    private LocalDateTime createTime;

    @ExcelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description =  "更新时间")
    private LocalDateTime updateTime;

    @ExcelProperty(value = "创建人ID")
    @DictFormat(dictType = "dynamic_user_options")
    @Schema(description =  "创建人ID")
    private Long createId;

    @ExcelProperty(value = "更新人ID")
    @DictFormat(dictType = "dynamic_user_options")
    @Schema(description =  "更新人ID")
    private Long updateId;

}