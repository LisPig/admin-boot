package com.sz.applet.miniuser.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * MiniLoginDTO
 * 
 * @author sz
 * @since 2024/4/26 14:25
 * @version 1.0
 */
@Data
public class UpdateMiniUserInfoDTO {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "小程序用户的唯一标识")
    private String openid;

    @Schema(description = "用户在开放平台的唯一标识符")
    private String unionid;

    private String name;

    @Schema(description = "用户头像URL")
    private String avatarUrl;

    @Schema(description = "认证状态")
    private Integer authStatus;

    @Schema(description = "是否显示")
    private Integer isShow;

    @Schema(description = "工作单位")
    private String workUnit;

    @Schema(description = "职务")
    private String job;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "我的荣誉")
    private String honor;

    @Schema(description = "我的钱高回忆")
    private String memory;

    @Schema(description = "我的建议")
    private String mySuggestion;
}
