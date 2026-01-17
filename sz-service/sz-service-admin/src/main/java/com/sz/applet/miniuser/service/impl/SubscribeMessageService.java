package com.sz.applet.miniuser.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociation;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivity;
import com.sz.applet.miniBusiness.pojo.po.ApplyAuth;
import com.sz.applet.miniuser.mapper.MessageLogMapper;
import com.sz.applet.miniuser.pojo.po.MessageLog;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.common.exception.common.BusinessException;
import com.sz.wechat.WechatProperties;
import com.sz.wechat.mini.MiniWechatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 小程序订阅消息服务
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SubscribeMessageService {

    private final WechatProperties wechatProperties;
    private final MessageLogMapper messageLogMapper;

    //private final RedisTemplate<String, String> redisTemplate;

    private static final String ACCESS_TOKEN_KEY = "wechat:access_token:";
    private static final long TOKEN_EXPIRE = 7000; // 提前过期

    private final ObjectMapper objectMapper;

    private final MiniWechatService miniWechatService;
    private final MiniUserService miniUserService;




    /**
     * 发送订阅消息（Spring风格）
     */
    @Async("messageExecutor")
    @Transactional(rollbackFor = Exception.class)
    public boolean sendSubscribeMessage(String openid, String templateKey,
                                        Map<String, String> params, String pagePath) {

        String templateId = wechatProperties.getMini().getTemplateId(templateKey);
        if (StringUtils.isBlank(templateId)) {
            throw new BusinessException(CommonResponseEnum.NOT_FOUND, null, "模板不存在");
        }

        // 构建请求
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("touser", openid);
        requestBody.put("template_id", templateId);
        requestBody.put("page", pagePath);
        requestBody.put("miniprogram_state", "formal");
        requestBody.put("lang", "zh_CN"); // 重要：必须添加这个字段

        // 构建data
        Map<String, Map<String, String>> data = new HashMap<>();
        params.forEach((key, value) -> {
            Map<String, String> item = new HashMap<>();
            item.put("value", value);
            data.put(key, item);
        });
        requestBody.put("data", data);

        // 发送请求
        String accessToken = miniWechatService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;

        try {
            // 使用Hutool发送POST请求
            String requestBodyJson = cn.hutool.json.JSONUtil.toJsonStr(requestBody);
            
            String responseJson = HttpRequest.post(url)
                    .header(org.springframework.http.HttpHeaders.CONTENT_TYPE, ContentType.JSON.toString())
                    .timeout(30000)
                    .body(requestBodyJson)
                    .execute()
                    .body();
            
            Map<String, Object> result = cn.hutool.json.JSONUtil.toBean(responseJson, Map.class);
            Integer errcode = (Integer) result.get("errcode");

            if (errcode != null && errcode == 0) {
                log.info("订阅消息发送成功: openid={}, template={}", openid, templateKey);

                // 记录发送日志
                saveMessageLog(openid, templateKey, params, true, null);
                return true;
            } else {
                String errmsg = (String) result.get("errmsg");
                log.error("订阅消息发送失败: openid={}, code={}, msg={}",
                        openid, errcode, errmsg);

                saveMessageLog(openid, templateKey, params, false, errmsg);
                return false;
            }
        } catch (Exception e) {
            log.error("发送订阅消息异常", e);
            saveMessageLog(openid, templateKey, params, false, e.getMessage());
            throw new BusinessException(CommonResponseEnum.SEND_MSG_FAIL, null,CommonResponseEnum.SEND_MSG_FAIL.getMessage());
        }
    }


    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求工厂
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(30000);
        factory.setConnectionRequestTimeout(5000);
        restTemplate.setRequestFactory(factory);

        // 添加拦截器记录请求
       // restTemplate.setInterceptors(Collections.singletonList(new RestTemplateInterceptor()));

        return restTemplate;
    }
    /**
     * 保存消息发送记录
     */
    private void saveMessageLog(String openid, String templateKey,
                                Map<String, String> params, boolean success, String error) {
        MessageLog messageLog = new MessageLog();
        messageLog.setOpenid(openid);
        messageLog.setTemplateKey(templateKey);
        messageLog.setContent(JSONUtil.toJsonStr(params));
        messageLog.setSuccess(success);
        messageLog.setErrorMessage(error);
        messageLog.setCreateTime(new Date());

        // 保存到数据库
        messageLogMapper.insert(messageLog);
    }

    /**
     * 发送校友会审核结果通知
     */
    public boolean sendApproveAssociationMsg(String openid, AppletAlumniAssociation appletAlumniAssociation) {
        Map<String, String> params = new HashMap<>();
        String status = appletAlumniAssociation.getStatus();
        // 根据模板字段名设置参数
        params.put("thing1", "校友会审核");  // 审核类型
        params.put("thing9", appletAlumniAssociation.getName()); //校会名称
        params.put("thing4", status.equals("1")?"通过":"未通过"); //审核结果
        params.put("date10", appletAlumniAssociation.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // 申请时间
        params.put("date7",formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));// 审核时间
        String pagePath = "pages/index/index";

        return sendSubscribeMessage(openid, "CHECK_RESULT", params, pagePath);
    }

    /**
     * 发送给管理员校友认证申请通知
     * @return
     */
    public boolean sendApplyAuthMsg(String openid, ApplyAuth applyAuth) {
        Map<String, String> params = new HashMap<>();
        // 根据模板字段名设置参数

        params.put("thing1", applyAuth.getName());
        params.put("time3",
                applyAuth.getCreateTime() != null ?
                        DateUtil.format(applyAuth.getCreateTime(), "yyyy年M月d日") : ""
        );
        params.put("phone_number6", applyAuth.getPhone());
        params.put("thing4", "校友认证待审核");

        String pagePath = null;//String.format("pages/order/detail?orderId=%s", order.getId());

        return sendSubscribeMessage(openid, "ALUMNI_APPLY", params, pagePath);
    }

    /**
     * 发送给用户校友认证申请通知
     * @param openid
     * @param applyAuth
     * @return
     */
    public boolean sendApplyAuthMsgForUser(String openid, ApplyAuth applyAuth) {
        Map<String, String> params = new HashMap<>();
        // 根据模板字段名设置参数
        params.put("thing1", "校友认证审核");  // 审核类型
        params.put("thing9", applyAuth.getName());
        params.put("thing4", applyAuth.getStatus().equals("2")?"通过":"未通过"); //审核结果
        params.put("date10", applyAuth.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // 申请时间
        params.put("date7",formatDate(new Date(), "yyyy-MM-dd HH:mm"));// 审核时间
        String pagePath = "pages/index/index";

        return sendSubscribeMessage(openid, "CHECK_RESULT", params, pagePath);
    }

    public boolean sendAssociationActivityMsgForUser(String openid, AppletAlumniAssociationActivity appletAlumniAssociationActivity) {
        Map<String, String> params = new HashMap<>();
        // 根据模板字段名设置参数
        params.put("thing1", "校友会活动审批");  // 审核类型
        params.put("thing9", appletAlumniAssociationActivity.getTitle());
        params.put("thing4", appletAlumniAssociationActivity.getStatus().equals("2")?"通过":"未通过"); //审核结果
        params.put("date10", appletAlumniAssociationActivity.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // 申请时间
        params.put("date7",formatDate(new Date(), "yyyy-MM-dd HH:mm"));// 审核时间
        String pagePath = "pages/index/index";

        return sendSubscribeMessage(openid, "CHECK_RESULT", params, pagePath);
    }



    // 工具方法
    private String truncate(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }

    private String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}