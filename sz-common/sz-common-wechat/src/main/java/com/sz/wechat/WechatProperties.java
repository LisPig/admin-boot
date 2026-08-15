package com.sz.wechat;

import com.sz.wechat.payment.WechatPayProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sz
 * @since 2024/4/26 9:23
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "sz.wechat")
public class WechatProperties {

    @Schema(description = "小程序开发者配置")
    private MiniProgramProperties mini;

    @Schema(description = "企业微信开发者配置")
    private WorkProgramProperties work;
    
    @Schema(description = "微信支付配置")
    private WechatPayProperties pay;

    @Schema(description = "消息推送配置")
    private MessagePushProperties messagePush;

    @Schema(description = "媒体内容安全校验配置")
    private MediaCheckProperties mediaCheck;

    @Data
    public static class MiniProgramProperties {

        @Schema(description = "小程序应用ID")
        private String appId;

        @Schema(description = "小程序应用密钥")
        private String appSecret;

        // 模板ID配置
        @NestedConfigurationProperty
        private Map<String, String> templates = new HashMap<>();

        public String getTemplateId(String templateKey) {
            return templates.get(templateKey);
        }
    }

    @Data
    public static class WorkProgramProperties {

        @Schema(description = "企业ID; 登录企业微信管理后台,位于【我的企业-应用信息】-> 企业ID处获取; 文档：https://developer.work.weixin.qq.com/document/path/90665#corpid")
        private String corpId;

        @Schema(description = "企业微信应用密钥; 登录企业微信管理后台，位于【应用管理-应用-自建】-> 创建应用; 文档：https://developer.work.weixin.qq.com/document/path/90665#secret")
        private String corpSecret;

        @Schema(description = "企业微信应用凭证; 同上述corpSecret，位于“应用”secret属性的下方; 文档：https://developer.work.weixin.qq.com/document/path/90665#agentid")
        private Integer agentId;

    }

    @Data
    public static class MessagePushProperties {

        @Schema(description = "消息推送Token(与小程序后台一致)")
        private String token;

        @Schema(description = "EncodingAESKey(安全模式必填,明文留空)")
        private String aesKey;
    }

    @Data
    public static class MediaCheckProperties {

        @Schema(description = "是否启用图片内容安全校验")
        private Boolean enabled = true;

        @Schema(description = "参与校验的dirTag集合")
        private List<String> dirTags = List.of("avatar", "files");

        @Schema(description = "REVIEW结果是否也隐藏(默认false,仅RISKY隐藏)")
        private Boolean hideOnReview = false;
    }
}