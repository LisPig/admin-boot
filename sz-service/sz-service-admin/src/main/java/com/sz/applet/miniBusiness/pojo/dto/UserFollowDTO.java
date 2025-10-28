package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 关注/取消关注DTO
 * </p>
 *
 * @author your-name
 * @since 2025-10-28
 */
@Data
@Schema(description = "关注/取消关注DTO")
public class UserFollowDTO {

    @Schema(description = "被关注者ID")
    private Long followedUserId;

}