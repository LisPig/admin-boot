package com.sz.applet.miniuser.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * MiniUser查询返回
 * </p>
 *
 * @author sz
 * @since 2024-04-26
 */
@Data
@Schema(description = "MiniUser返回vo")
@AutoMapper(target = MiniUser.class)
//过滤null值和空字符
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiniUserVO implements Serializable {

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

    @Schema(description = "工作单位")
    private String workUnit;

    @Schema(description = "职务")
    private String job;

    @Schema(description = "手机号")
    private String phone;
}