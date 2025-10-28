package com.sz.applet.miniBusiness.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 评论VO
 * </p>
 *
 * @author your-name
 * @since 2025-10-28
 */
@Data
@Schema(description = "评论VO")
public class CommentVO {

    @Schema(description = "评论ID")
    private Long id;

    @Schema(description = "动态ID")
    private Long memoId;

    @Schema(description = "评论用户ID")
    private Long userId;

    @Schema(description = "评论者用户名")
    private String username;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "回复目标用户名")
    private String replyTo;

    @Schema(description = "回复目标评论ID")
    private Long replyToId;

    @Schema(description = "创建时间")
    private Date createTime;

}