package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

import cn.idev.excel.annotation.ExcelProperty;
import com.sz.excel.annotation.DictFormat;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * <p>
 * SchoolClassMemory导入DTO
 * </p>
 *
 * @author lee
 * @since 2026-01-06
 */
@Data
@Accessors(chain = true)
@Schema(description = "SchoolClassMemory导入DTO")
public class SchoolClassMemoryImportDTO {

    @ExcelProperty(value = "届数")
    @Schema(description =  "届数")
    private Integer year;

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

}