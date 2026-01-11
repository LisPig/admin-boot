package com.sz.applet.miniuser.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

// MessagePushController.java - 服务器验证接口
@RestController
@RequestMapping("/wechat/callback")
@Slf4j
public class MessagePushController {

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

        // 1. 验证Token（必须和后台配置一致）
        String token = "qiankugaojizhongxue"; // 从配置读取

        // 2. 按字典序排序
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);

        // 3. SHA1加密
        String tempStr = String.join("", arr);
        String localSignature = sha1(tempStr);

        // 4. 验证签名
        if (localSignature.equals(signature)) {
            log.info("微信服务器验证成功，返回echostr: {}", echostr);
            return echostr; // 必须原样返回echostr
        } else {
            log.error("签名验证失败: local={}, wechat={}", localSignature, signature);
            return "verify fail";
        }
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

    /**
     * POST请求 - 接收推送消息（验证通过后才有）
     */
    @PostMapping
    public String handleMessage(@RequestBody String requestBody,
                                @RequestParam Map<String, String> params) {
        // 这里处理微信推送的各类消息
        log.info("接收到微信推送消息: {}", requestBody);
        return "success";
    }
}
