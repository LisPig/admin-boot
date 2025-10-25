package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * AppletAlumniAssociation添加DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-25
 */
@Data
@Accessors(chain = true)
@Schema(description = "AppletAlumniAssociation添加DTO")
public class AppletAlumniAssociationCreateDTO {

   @Schema(description =  "名称")
   private String name;

   @Schema(description =  "头像")
   private String avatar;

   @Schema(description =  "描述")
   private String description;

   @Schema(description =  "联系人")
   private String contract;

   @Schema(description =  "联系电话")
   private String phone;

   @Schema(description =  "状态（1-正常，2-禁用）")
   private String status;

}