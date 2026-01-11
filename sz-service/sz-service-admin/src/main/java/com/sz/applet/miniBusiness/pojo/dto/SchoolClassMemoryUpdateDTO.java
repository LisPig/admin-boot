package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * SchoolClassMemory修改DTO
 * </p>
 *
 * @author lee
 * @since 2026-01-06
 */
@Data
@Accessors(chain = true)
@Schema(description = "SchoolClassMemory修改DTO")
public class SchoolClassMemoryUpdateDTO {

    @Schema(description =  "ID")
    private Long id;

    @Schema(description =  "届数")
    private Integer year;

    @Schema(description =  "班级编号")
    private String classNo;

    @Schema(description =  "教师列表(json数组)")
    private String teacherList;

    @Schema(description =  "学生列表(逗号分割)")
    private String studentList;

    @Schema(description =  "图片列表(逗号分隔)")
    private String images;

}