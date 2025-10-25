package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * SchoolMaster修改DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
@Data
@Accessors(chain = true)
@Schema(description = "SchoolMaster修改DTO")
public class SchoolMasterUpdateDTO {

    @Schema(description =  "ID")
    private Long id;

    @Schema(description =  "姓名")
    private String name;

    @Schema(description =  "画像")
    private String avatar;

    @Schema(description =  "任期记录(包含开始结束时间和职务描述)")
    private String history;

}