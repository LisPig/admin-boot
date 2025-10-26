package com.sz.wechat.payment;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 微信支付服务类
 *
 * @author sz
 * @since 2025/10/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WechatPayService {

    private final WechatPayProperties wechatPayProperties;

    /**
     * 创建微信支付配置
     *
     * @return Config
     */
    private Config createConfig() {
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(wechatPayProperties.getMchId())
                .privateKeyFromPath(wechatPayProperties.getPrivateKeyPath())
                .merchantSerialNumber(wechatPayProperties.getMchSerialNo())
                .apiV3Key(wechatPayProperties.getApiV3Key())
                .build();
    }

    /**
     * 创建JSAPI支付订单
     *
     * @param outTradeNo 商户订单号
     * @param amount     金额(元)
     * @param description 商品描述
     * @param openid     用户openid
     * @param attach     附加数据
     * @return 预支付交易会话标识
     */
    public String createJsapiOrder(String outTradeNo, BigDecimal amount, String description, String openid, String attach) {
        JsapiService service = new JsapiService.Builder().config(createConfig()).build();

        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        PrepayRequest request = new PrepayRequest();
        Amount a = new Amount();
        a.setTotal(amount.multiply(new BigDecimal(100)).intValue()); // 转换为分
        a.setCurrency("CNY");
        request.setAmount(a);
        request.setAppid(null);// (wechatPayProperties.getMini().getAppId());
        request.setMchid(wechatPayProperties.getMchId());
        request.setDescription(description);
        request.setOutTradeNo(outTradeNo);
        request.setNotifyUrl(wechatPayProperties.getNotifyUrl());
        Payer payer = new Payer();
        payer.setOpenid(openid);
        request.setPayer(payer);
        request.setAttach(attach);

        try {
            PrepayWithRequestPaymentResponse response = null;//service.prepayWithRequestPayment(request);
            return response.getPackageVal(); // 在0.2.17版本中，返回的是packageVal而不是prepayId
        } catch (Exception e) {
            //log.error("创建微信支付订单失败", e);
            throw new RuntimeException("创建微信支付订单失败: " + e.getMessage());
        }
    }

    /**
     * 生成调起支付所需的参数 (已废弃，使用prepayWithRequestPayment方法替代)
     *
     * @param prepayId 预支付交易会话标识
     * @return 调起支付所需的参数
     */
    @Deprecated
    public Object generatePayInfo(String prepayId) {
        // 在0.2.17版本中，这个方法已经不再需要，因为prepayWithRequestPayment已经返回了所有需要的参数
        return null;
    }

    /**
     * 查询订单
     *
     * @param outTradeNo 商户订单号
     * @return 订单信息
     */
    public Transaction queryOrder(String outTradeNo) {
        JsapiService service = new JsapiService.Builder().config(createConfig()).build();
        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(wechatPayProperties.getMchId());
        request.setOutTradeNo(outTradeNo);
        try {
            return service.queryOrderByOutTradeNo(request);
        } catch (Exception e) {
            log.error("查询微信支付订单失败", e);
            throw new RuntimeException("查询微信支付订单失败: " + e.getMessage());
        }
    }

    /**
     * 关闭订单
     *
     * @param outTradeNo 商户订单号
     */
    public void closeOrder(String outTradeNo) {
        JsapiService service = new JsapiService.Builder().config(createConfig()).build();
        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(wechatPayProperties.getMchId());
        request.setOutTradeNo(outTradeNo);
        try {
            service.closeOrder(request);
        } catch (Exception e) {
            log.error("关闭微信支付订单失败", e);
            throw new RuntimeException("关闭微信支付订单失败: " + e.getMessage());
        }
    }
}