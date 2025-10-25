package com.sz.applet.miniBusiness.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;

/**
 * <p>
 * SchoolAlbumChild返回vo
 * </p>
 *
 * @author sz-admin
 * @since 2025-10-23
 */
@Data
@Accessors(chain = true)
@Schema(description = "SchoolAlbumChild返回vo")
public class SchoolAlbumChildVO {

    @ExcelIgnore
    @Schema(description =  "ID")
    private Long id;

    @ExcelProperty(value = "父相册ID")
    @Schema(description =  "父相册ID")
    private Long albumId;

    @ExcelProperty(value = "标题")
    @Schema(description =  "标题")
    private String title;

    @ExcelProperty(value = "封面图url")
    @Schema(description =  "封面图url")
    private String cover;

}
