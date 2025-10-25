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
 * SchoolAlbum导入DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
@Data
@Accessors(chain = true)
@Schema(description = "SchoolAlbum导入DTO")
public class SchoolAlbumImportDTO {

    @ExcelProperty(value = "标题")
    @Schema(description =  "标题")
    private String title;

    @ExcelProperty(value = "封面图url")
    @Schema(description =  "封面图url")
    private String cover;

    @ExcelProperty(value = "分类")
    @Schema(description =  "分类")
    private String category;

    @ExcelProperty(value = "内容(图片urlJSON数组)")
    @Schema(description =  "内容(图片urlJSON数组)")
    private String content;

}