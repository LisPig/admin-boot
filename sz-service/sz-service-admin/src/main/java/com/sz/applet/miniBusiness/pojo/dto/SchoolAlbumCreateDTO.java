package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * SchoolAlbum添加DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
@Data
@Accessors(chain = true)
@Schema(description = "SchoolAlbum添加DTO")
public class SchoolAlbumCreateDTO {

   @Schema(description =  "标题")
   private String title;

   @Schema(description =  "封面图url")
   private String cover;

   @Schema(description =  "分类")
   private String category;

   @Schema(description =  "内容(图片urlJSON数组)")
   private String content;

}