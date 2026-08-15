package com.sz.applet.miniuser.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sz.admin.system.service.MediaCheckService;
import com.sz.applet.miniuser.pojo.dto.WxaMediaCheckPayload;
import com.sz.wechat.WechatProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * 微信「消息推送」回调接口
 * <p>
 * GET: 配置消息推送时微信发起的服务器验证,验签后原样返回 echostr
 * POST: 接收各类事件推送(明文模式),目前处理 wxa_media_check 图片内容安全校验回调
 *
 * @author sz
 */
@RestController
@RequestMapping("/wechat/callback")
@Slf4j
@RequiredArgsConstructor
public class MessagePushController {

    private final WechatProperties wechatProperties;
    private final MediaCheckService mediaCheckService;
    private final ObjectMapper objectMapper;

    /**
     * GET请求 - 微信服务器验证
     * 配置消息推送时，微信会发送GET请求验证服务器有效性
     */
    @GetMapping
    public String verify(@RequestParam Map<String, String> params) {
        String signature = params.get("signature");
        String timestamp = params.get("timestamp");
        String nonce = params.get("nonce");
        String echostr = params.get("echostr");

        log.info("微信验证请求: signature={}, timestamp={}, nonce={}, echostr={}",
                signature, timestamp, nonce, echostr);

        if (checkSignature(signature, timestamp, nonce)) {
            log.info("微信服务器验证成功，返回echostr: {}", echostr);
            return echostr; // 必须原样返回echostr
        } else {
            log.error("签名验证失败");
            return "verify fail";
        }
    }

    /**
     * POST请求 - 接收推送消息（验证通过后才有）
     * <p>
     * 明文模式下 body 为 XML 或 JSON;安全模式(<Encrypt>节点)首版记录日志跳过。
     * 无论结果如何统一返回 "success",避免微信失败重试。
     */
    @PostMapping
    public String handleMessage(@RequestBody String requestBody,
                                @RequestParam Map<String, String> params) {
        if (StrUtil.isBlank(requestBody)) {
            return "success";
        }
        try {
            // 明文模式验签:失败则忽略该推送(仍返回success避免微信重试)
            if (!checkSignature(params.get("signature"), params.get("timestamp"), params.get("nonce"))) {
                log.error("明文模式签名校验失败,忽略推送: {}", requestBody);
                return "success";
            }
            String body = requestBody.trim();
            if (body.startsWith("<") && body.contains("<Encrypt>")) {
                log.warn("收到安全模式加密推送,首版不处理: {}", requestBody);
                return "success";
            }
            WxaMediaCheckPayload payload = parsePayload(body);
            if (payload == null) {
                return "success";
            }
            boolean isMediaCheck = payload.getTraceId() != null
                    || "wxa_media_check".equals(payload.getEvent());
            if (isMediaCheck) {
                handleWxaMediaCheck(payload);
            } else {
                log.info("忽略非wxa_media_check事件: msgType={}, event={}",
                        payload.getMsgType(), payload.getEvent());
            }
        } catch (Exception e) {
            log.error("处理微信推送异常: {}", requestBody, e);
        }
        return "success";
    }

    /**
     * 解析推送报文为 {@link WxaMediaCheckPayload}
     * <p>
     * 兼容三种形态:明文XML(Content内嵌JSON)、明文XML(字段平铺)、明文JSON
     */
    private WxaMediaCheckPayload parsePayload(String body) throws Exception {
        String json;
        if (body.startsWith("<")) {
            // XML → JSON
            JSONObject xmlJson = XML.toJSONObject(body);
            Object content = xmlJson.get("Content");
            if (content instanceof String s && s.trim().startsWith("{")) {
                json = s.trim(); // XML 内嵌 JSON
            } else {
                json = xmlJson.toString();
            }
        } else if (body.startsWith("{")) {
            json = body; // 明文 JSON 格式推送
        } else {
            return null;
        }
        return objectMapper.readValue(json, WxaMediaCheckPayload.class);
    }

    private void handleWxaMediaCheck(WxaMediaCheckPayload payload) {
        String suggest;
        Integer label = null;
        if (payload.getResult() != null && StrUtil.isNotBlank(payload.getResult().getSuggest())) {
            suggest = payload.getResult().getSuggest();
            label = payload.getResult().getLabel();
        } else if (Boolean.TRUE.equals(payload.getIsrisky())) {
            suggest = "risky"; // v1 格式兜底
        } else {
            suggest = "pass";
        }
        mediaCheckService.handleCallbackResult(payload.getTraceId(), suggest, label);
    }

    /**
     * 微信签名校验:sha1(字典序(token,timestamp,nonce)) == signature
     */
    private boolean checkSignature(String signature, String timestamp, String nonce) {
        String token = wechatProperties.getMessagePush() == null
                ? null : wechatProperties.getMessagePush().getToken();
        if (StrUtil.isBlank(token) || StrUtil.isBlank(signature)
                || StrUtil.isBlank(timestamp) || StrUtil.isBlank(nonce)) {
            log.warn("签名校验参数不完整: token={}, signature={}, timestamp={}, nonce={}",
                    token, signature, timestamp, nonce);
            return false;
        }
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        String localSignature = sha1(String.join("", arr));
        if (!localSignature.equals(signature)) {
            log.error("签名验证失败: local={}, wechat={}", localSignature, signature);
            return false;
        }
        return true;
    }

    /**
     * SHA1加密方法
     */
    private String sha1(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(str.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1算法不支持", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
