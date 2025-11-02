package com.sz.wechat.mini.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: tianzhiyuan
 * @Date: 2025-11-01
 * @Description: 微信小程序订阅消息发送结果VO
 */
@Data
@Schema(description = "微信小程序订阅消息发送结果VO")
public class SubscribeMessageSendVO {

    @Schema(description = "错误码")
    private Integer errcode;

    @Schema(description = "错误信息")
    private String errmsg;
}