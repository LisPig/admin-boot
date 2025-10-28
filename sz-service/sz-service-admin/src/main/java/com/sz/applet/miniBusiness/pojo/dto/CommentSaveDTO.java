package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 发表评论DTO
 * </p>
 *
 * @author your-name
 * @since 2025-10-28
 */
@Data
@Schema(description = "发表评论DTO")
public class CommentSaveDTO {

    @Schema(description = "动态ID")
    private Long memoId;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "回复目标用户名")
    private String replyTo;

    @Schema(description = "回复目标评论ID")
    private Long replyToId;

}