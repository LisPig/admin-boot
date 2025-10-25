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
 * SchoolMaster导入DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
@Data
@Accessors(chain = true)
@Schema(description = "SchoolMaster导入DTO")
public class SchoolMasterImportDTO {

    @ExcelProperty(value = "姓名")
    @Schema(description =  "姓名")
    private String name;

    @ExcelProperty(value = "画像")
    @Schema(description =  "画像")
    private String avatar;

    @ExcelProperty(value = "任期记录(包含开始结束时间和职务描述)")
    @Schema(description =  "任期记录(包含开始结束时间和职务描述)")
    private String history;

}