package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * SchoolMaster添加DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
@Data
@Accessors(chain = true)
@Schema(description = "SchoolMaster添加DTO")
public class SchoolMasterCreateDTO {

   @Schema(description =  "姓名")
   private String name;

   @Schema(description =  "画像")
   private String avatar;


   @Schema(description =  "开始时间")
   private String startTime;
   @Schema(description =  "结束时间")
   private String endTime;
   @Schema(description =  "职务")
   private String position;
   @Schema(description =  "描述")
   private String description;

}

