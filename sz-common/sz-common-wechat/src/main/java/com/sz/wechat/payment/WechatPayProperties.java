package com.sz.wechat.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置类
 *
 * @author sz
 * @since 2025/10/26
 */
@Data
@Component
@ConfigurationProperties(prefix = "sz.wechat.pay")
public class WechatPayProperties {

    @Schema(description = "微信支付商户号")
    private String mchId;

    @Schema(description = "微信支付商户证书序列号")
    private String mchSerialNo;

    @Schema(description = "微信支付商户私钥路径")
    private String privateKeyPath;

    @Schema(description = "微信支付平台证书路径")
    private String platformCertPath;

    @Schema(description = "微信支付APIv3密钥")
    private String apiV3Key;

    @Schema(description = "微信支付通知地址")
    private String notifyUrl;

    @Schema(description = "微信支付应用域名")
    private String domain;

    @Schema(description = "是否启用沙箱环境")
    private boolean sandbox = false;
}