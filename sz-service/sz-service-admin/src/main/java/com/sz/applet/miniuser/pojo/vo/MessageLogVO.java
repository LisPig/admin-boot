package com.sz.applet.miniuser.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 订阅消息发送日志表返回VO
 * </p>
 *
 * @author sz
 * @since 2026-01-07
 */
@Data
@Accessors(chain = true)
@Schema(description = "订阅消息发送日志表返回VO")
public class MessageLogVO {

    @Schema(description = "ID")
    private Long id;

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

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "创建人ID")
    private Long createId;

    @Schema(description = "更新人ID")
    private Long updateId;
}