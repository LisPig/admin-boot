package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import com.sz.core.common.entity.PageQuery;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * <p>
 * AppletAlumniAssociationActivity查询DTO
 * </p>
 *
 * @author sz-admin
 * @since 2025-10-25
 */
@Data
@Accessors(chain = true)
@Schema(description = "AppletAlumniAssociationActivity查询DTO")
public class AppletAlumniAssociationActivityListDTO extends PageQuery {

    @Schema(description =  "校友会ID")
    private Long alumniAssociationId;

    @Schema(description =  "标题")
    private String title;

    @Schema(description =  "头图")
    private String avatar;

    @Schema(description =  "内容")
    private String content;

    @Schema(description =  "时间")
    private String time;

    @Schema(description =  "地点")
    private String location;

    @Schema(description =  "状态（1-正常，2-禁用）")
    private String status;

}