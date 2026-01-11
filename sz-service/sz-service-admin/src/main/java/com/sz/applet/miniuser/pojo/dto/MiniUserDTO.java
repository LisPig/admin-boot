package com.sz.applet.miniuser.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.sz.core.common.entity.PageQuery;
import com.sz.mysql.EntityChangeListener;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 小程序用户DTO
 * </p>
 *
 * @author sz
 * @since 2024-04-26
 */
@Data
@Schema(description = "小程序用户表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MiniUserDTO extends PageQuery {


    @Id(keyType = KeyType.Auto)
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "关联的系统用户ID")
    private Integer sysUserId;

    @Schema(description = "小程序用户的唯一标识")
    private String openid;

    @Schema(description = "公众号的唯一标识")
    private String unionid;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "真实姓名")
    private String username;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "用户头像URL")
    private String avatarUrl;

    @Schema(description = "是否订阅公众号（1是0否）")
    private Integer subscribe;

    @Schema(description = "性别，0-未知 1-男性，2-女性")
    private Integer sex;

    @Schema(description = "删除标识")
    private String delFlag;

    @Schema(description = "认证状态")
    private Integer authStatus;

    @Schema(description = "工作单位")
    private String workUnit;

    @Schema(description = "职务")
    private String job;

    @Schema(description = "我的荣誉")
    private String honor;

    @Schema(description = "我的钱高回忆")
    private String memory;

    @Schema(description = "我的建议")
    private String mySuggestion;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}