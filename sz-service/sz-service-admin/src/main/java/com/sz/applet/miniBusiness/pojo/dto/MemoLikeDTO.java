package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 点赞/取消点赞DTO
 * </p>
 *
 * @author your-name
 * @since 2025-10-28
 */
@Data
@Schema(description = "点赞/取消点赞DTO")
public class MemoLikeDTO {

    @Schema(description = "动态ID")
    private Long memoId;

}