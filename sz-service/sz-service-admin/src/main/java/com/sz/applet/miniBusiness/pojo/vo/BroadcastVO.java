package com.sz.applet.miniBusiness.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * Broadcast返回vo
 * </p>
 *
 * @author LisPig
 * @since 2025-10-30
 */
@Data
@Accessors(chain = true)
@Schema(description = "Broadcast返回vo")
public class BroadcastVO {

    @Schema(description =  "ID")
    private Long id;

    @Schema(description =  "标题")
    private String title;

    @Schema(description =  "内容")
    private String content;

    @Schema(description =  "状态（1-正常，2-禁用）")
    private String status;

    @Schema(description =  "创建时间")
    private LocalDateTime createTime;

    @Schema(description =  "更新时间")
    private LocalDateTime updateTime;

    @Schema(description =  "创建人ID")
    private Long createId;

    @Schema(description =  "更新人ID")
    private Long updateId;

}