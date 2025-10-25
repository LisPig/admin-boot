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
 * SchoolAlbum返回vo
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
@Data
@Accessors(chain = true)
@Schema(description = "SchoolAlbum返回vo")
public class SchoolAlbumVO {

    @ExcelIgnore
    @Schema(description =  "ID")
    private Long id;

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

    @ExcelProperty(value = "子项数量")
    private Long childCount;

}
