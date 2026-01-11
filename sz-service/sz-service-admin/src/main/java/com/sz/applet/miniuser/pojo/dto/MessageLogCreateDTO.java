package com.sz.applet.miniuser.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 订阅消息发送日志表新增DTO
 * </p>
 *
 * @author sz
 * @since 2026-01-07
 */
@Data
@Schema(description = "订阅消息发送日志表新增DTO")
public class MessageLogCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户openid")
    private String openid;

    @Schema(description = "模板key")
    private String templateKey;

    @Schema(description = "消息内容(JSON格式)")
    private String content;

    @Schema(description = "是否发送成功")
    private Boolean success;

    @Schema(description = "错误信息")
    private String errorMessage;
}