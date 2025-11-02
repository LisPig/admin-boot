package com.sz.applet.miniBusiness.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

/**
 * @Author: tianzhiyuan
 * @Date: 2025-11-01 
 * @Description: 用户关注VO
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

    @Schema(description = "被关注用户昵称")
    private String followedUserNickname;

    @Schema(description = "被关注用户头像")
    private String followedUserAvatar;
}