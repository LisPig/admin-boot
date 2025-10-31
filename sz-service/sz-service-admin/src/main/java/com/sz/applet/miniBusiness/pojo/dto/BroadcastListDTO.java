package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import com.sz.core.common.entity.PageQuery;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * <p>
 * Broadcast查询DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-30
 */
@Data
@Accessors(chain = true)
@Schema(description = "Broadcast查询DTO")
public class BroadcastListDTO extends PageQuery {

    @Schema(description =  "标题")
    private String title;

    @Schema(description =  "内容")
    private String content;

    @Schema(description =  "状态（1-正常，2-禁用）")
    private String status;

}