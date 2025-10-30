package com.sz.applet.miniBusiness.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 用户关注VO
 * </p>
 *
 * @author your-name
 * @since 2025-10-29
 */
@Data
@Schema(description = "用户关注VO")
public class UserFollowVO {

    @Schema(description = "关注ID")
    private Long id;

    @Schema(description = "关注者用户ID")
    private Long userId;

    @Schema(description = "被关注者用户ID")
    private Long followedUserId;

    @Schema(description = "关注时间")
    private Date createTime;

}