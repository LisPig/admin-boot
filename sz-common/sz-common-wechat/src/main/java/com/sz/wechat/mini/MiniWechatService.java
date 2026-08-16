package com.sz.wechat.mini;

import cn.hutool.core.util.StrUtil;
import com.sz.core.util.JsonUtils;
import com.sz.redis.RedisUtils;
import com.sz.wechat.WechatProperties;
import com.sz.wechat.mini.pojo.dto.SubscribeMessageSendDTO;
import com.sz.wechat.mini.pojo.vo.SubscribeMessageSendVO;
import com.sz.wechat.pojo.AccessTokenResult;
import com.sz.wechat.pojo.ErrorMessage;
import com.sz.wechat.pojo.MediaCheckAsyncResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.sz.wechat.WechatApiConstant.WECHAT_MEDIA_CHECK_ASYNC_URL;
import static com.sz.wechat.WechatApiConstant.WECHAT_MINI_LOGIN_URL;
import static com.sz.wechat.WechatApiConstant.WECHAT_MINI_SUBSCRIBE_MESSAGE_SEND_URL;
import static com.sz.wechat.WechatApiConstant.WECHAT_TOKEN_URL;

/**
 * @author sz
 * @since 2024/4/26 10:04
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MiniWechatService {

    private final WechatProperties wechatProperties;

    private static final String WECHAT_MINI_TOKEN = "wechat:mini:token";

    /**
     * 微信服务器走 HTTP/2 会异常(实测返回 412/协议错误),统一强制 HTTP/1.1
     */
    private final RestClient restClient = RestClient.builder()
            .requestFactory(new JdkClientHttpRequestFactory(
                    HttpClient.newBuilder()
                            .version(HttpClient.Version.HTTP_1_1)
                            .build()))
            .build();

    /**
     * 获取accessToken【小程序】
     *
     * @return accessToken
     */
    public String getAccessToken() {
        if (RedisUtils.hasKey(WECHAT_MINI_TOKEN)) {
            return (String) RedisUtils.getValue(WECHAT_MINI_TOKEN);
        } else {
            ResponseEntity<AccessTokenResult> entity = restClient.get()
                    .uri(WECHAT_TOKEN_URL, wechatProperties.getMini().getAppId(), wechatProperties.getMini().getAppSecret()).retrieve()
                    .toEntity(AccessTokenResult.class);
            AccessTokenResult result = entity.getBody();
            assert result != null;
            if (validSuccess(result)) {
                int expireTime = result.getExpiresIn() - 1200;
                RedisUtils.getRestTemplate().opsForValue().set(WECHAT_MINI_TOKEN, result.getAccessToken(), expireTime, TimeUnit.SECONDS);
                return result.getAccessToken();
            } else {
                log.error("【微信小程序】 获取accessToken失败，错误码：{}，错误信息：{}", result.getErrcode(), result.getErrmsg());
                return "";
            }
        }
    }

    /**
     * 微信小程序登录
     *
     * @param code
     *            code
     * @param accessToken
     *            accessToken
     * @return 登录信息
     */
    public LoginInfoResult miniLogin(String code, String accessToken) {
        // 微信小程序登录接口返回content-type是text/plain，因此无法直接映射对象。使用String接收，后续再做转换
        ResponseEntity<String> entity = restClient.get()
                .uri(WECHAT_MINI_LOGIN_URL, wechatProperties.getMini().getAppId(), wechatProperties.getMini().getAppSecret(), code, accessToken).retrieve()
                .toEntity(String.class);
        return JsonUtils.parseObject(entity.getBody(), LoginInfoResult.class);
    }

    /**
     * 发送订阅消息
     *
     * @param accessToken accessToken
     * @param dto 消息内容
     * @return 发送结果
     */
    public SubscribeMessageSendVO sendSubscribeMessage(String accessToken, SubscribeMessageSendDTO dto) {
        ResponseEntity<SubscribeMessageSendVO> entity = restClient.post()
                .uri(WECHAT_MINI_SUBSCRIBE_MESSAGE_SEND_URL, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(JsonUtils.toJsonString(dto))
                .retrieve()
                .toEntity(SubscribeMessageSendVO.class);
        return entity.getBody();
    }

    /**
     * 校验微信返回结果是否成功
     *
     * @param errorMessage
     *            微信返回结果
     * @return 是否成功
     */
    public boolean validSuccess(ErrorMessage errorMessage) {
        return errorMessage.getErrcode() == null || errorMessage.getErrcode() == 0;
    }

    /**
     * 图片内容安全异步校验(media_check_async)
     *
     * @param accessToken accessToken
     * @param mediaUrl    待校验图片URL
     * @param openid      当前小程序用户openid,可空(空则微信按无用户身份评估)
     * @return 提交结果(含trace_id)
     */
    public MediaCheckAsyncResult mediaCheckAsync(String accessToken, String mediaUrl, String openid) {
        Map<String, Object> body = new HashMap<>();
        body.put("media_url", mediaUrl);
        body.put("media_type", 2);
        body.put("version", 2);
        body.put("scene", 1);
        if (StrUtil.isNotBlank(openid)) {
            // openid 用于微信结合用户违规历史评估,需在请求线程捕获后传入(异步线程无登录上下文)
            body.put("openid", openid);
        }
        try {
            ResponseEntity<MediaCheckAsyncResult> entity = restClient.post()
                    .uri(WECHAT_MEDIA_CHECK_ASYNC_URL, accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    // Map 直接作为 body 会被 Jackson 以未知长度流式写出,触发 Transfer-Encoding: chunked,
                    // 微信网关拒绝 chunked 请求返回 412;先序列化为固定长度 JSON 字符串
                    .body(JsonUtils.toJsonString(body))
                    .retrieve()
                    .toEntity(MediaCheckAsyncResult.class);
            return entity.getBody();
        } catch (HttpClientErrorException e) {
            // 微信该接口以 HTTP 412 承载业务失败,响应体(可能为空)内含 errcode/errmsg
            return parseErrorResult(e);
        }
    }

    /**
     * 解析 4xx 响应中的业务失败结果
     */
    private MediaCheckAsyncResult parseErrorResult(HttpClientErrorException e) {
        String responseBody = e.getResponseBodyAsString();
        if (responseBody != null && !responseBody.isBlank()) {
            try {
                MediaCheckAsyncResult result = JsonUtils.parseObject(responseBody, MediaCheckAsyncResult.class);
                if (result != null && result.getErrcode() != null) {
                    return result;
                }
            } catch (Exception ignored) {
                // 响应体非 JSON,退化为 HTTP 状态码
            }
        }
        MediaCheckAsyncResult result = new MediaCheckAsyncResult();
        result.setErrcode(e.getStatusCode().value());
        result.setErrmsg("HTTP " + e.getStatusCode().value());
        return result;
    }

}