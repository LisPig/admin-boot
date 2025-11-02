package com.sz.wechat.mini.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

/**
 * @Author: tianzhiyuan
 * @Date: 2025-11-01
 * @Description: 微信小程序订阅消息发送DTO
 */
@Data
@Schema(description = "微信小程序订阅消息发送DTO")
public class SubscribeMessageSendDTO {

    @Schema(description = "接收者（用户）的 openid")
    private String touser;

    @Schema(description = "所需下发的订阅模板id")
    private String template_id;

    @Schema(description = "点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转")
    private String page;

    @Schema(description = "模板内容")
    private Map<String, TemplateData> data;

    @Data
    public static class TemplateData {
        private String value;

        public TemplateData() {
        }

        public TemplateData(String value) {
            this.value = value;
        }
    }
}