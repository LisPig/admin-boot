package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import com.sz.core.common.entity.PageQuery;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * <p>
 * SchoolMaster查询DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
@Data
@Accessors(chain = true)
@Schema(description = "SchoolMaster查询DTO")
public class SchoolMasterListDTO extends PageQuery {

    @Schema(description =  "姓名")
    private String name;

    @Schema(description =  "画像")
    private String avatar;

    @Schema(description =  "任期记录(包含开始结束时间和职务描述)")
    private String history;

}