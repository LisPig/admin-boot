package com.sz.wechat.payment;

import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.model.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 微信支付回调控制器
 *
 * @author sz
 * @since 2025/10/26
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PayCallbackController {

    private final WechatPayService wechatPayService;
    private final WechatPayProperties wechatPayProperties;

    /**
     * 支付结果通知
     * 
     * @param request HttpServletRequest
     * @return 处理结果
     */
    @PostMapping("/wechat/pay/notify")
    public String payNotify(HttpServletRequest request) {
        try {
            // 解析通知数据
            String requestBody = readData(request);
            String requestId = request.getHeader("Request-ID");
            String wechatPaySignature = request.getHeader("Wechatpay-Signature");
            String wechatPaySerial = request.getHeader("Wechatpay-Serial");
            String wechatPayTimestamp = request.getHeader("Wechatpay-Timestamp");
            String wechatPayNonce = request.getHeader("Wechatpay-Nonce");

            RequestParam requestParam = new RequestParam.Builder()
                    .serialNumber(wechatPaySerial)
                    .nonce(wechatPayNonce)
                    .signature(wechatPaySignature)
                    .timestamp(wechatPayTimestamp)
                    .body(requestBody)
                    .build();

            // 验证签名并解密
            // 注意：这里需要根据实际配置初始化NotificationConfig
            // NotificationConfig config = ...;
            // NotificationParser parser = new NotificationParser(config);
            // Transaction transaction = parser.parse(requestParam, Transaction.class);
            
            // 模拟处理支付结果
            // processPayResult(transaction);
            
            // 返回成功响应
            return "{\n" +
                    "  \"code\": \"SUCCESS\",\n" +
                    "  \"message\": \"成功\"\n" +
                    "}";
        } catch (Exception e) {
            log.error("处理微信支付通知失败", e);
            return "{\n" +
                    "  \"code\": \"FAIL\",\n" +
                    "  \"message\": \"" + e.getMessage() + "\"\n" +
                    "}";
        }
    }

    /**
     * 读取请求数据
     * 
     * @param request HttpServletRequest
     * @return 请求数据
     * @throws IOException IO异常
     */
    private String readData(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * 处理支付结果
     * 
     * @param transaction 交易信息
     */
    private void processPayResult(Transaction transaction) {
        // 根据业务需求处理支付结果
        log.info("收到支付结果通知，订单号：{}，状态：{}",
                transaction.getOutTradeNo(), transaction.getTradeState());
        
        // TODO: 根据实际业务需求更新订单状态等操作
        // 例如：更新捐赠记录状态等
    }
}