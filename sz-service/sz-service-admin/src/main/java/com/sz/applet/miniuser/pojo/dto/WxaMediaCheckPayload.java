package com.sz.applet.miniuser.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 微信 media_check_async 异步回调(wxa_media_check 事件)报文
 *
 * <p>支持明文 JSON 推送与 XML 推送(转 JSON)两种形态,字段名与微信推送保持一致。</p>
 *
 * @author sz
 * @since 2026-08-15
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxaMediaCheckPayload {

    @JsonProperty("ToUserName")
    private String toUserName;

    @JsonProperty("FromUserName")
    private String fromUserName;

    @JsonProperty("CreateTime")
    private Long createTime;

    @JsonProperty("MsgType")
    private String msgType;

    @JsonProperty("Event")
    private String event;

    @JsonProperty("appid")
    private String appId;

    /** 任务ID,与提交时返回的 trace_id 对应 */
    @JsonProperty("trace_id")
    private String traceId;

    @JsonProperty("status_code")
    private Integer statusCode;

    /** v1 格式兜底:0=暂未检测到风险,1=风险 */
    @JsonProperty("isrisky")
    private Boolean isrisky;

    @JsonProperty("extra_info_json")
    private String extraInfoJson;

    @JsonProperty("version")
    private Integer version;

    /** 2.0 综合结果 */
    private Result result;

    /** 2.0 详细检测结果(数组/对象两种形态,业务未用,仅兜底保留) */
    private JsonNode detail;

    @JsonProperty("errcode")
    private Integer errcode;

    private String errmsg;

    @Data
    public static class Result {
        /** pass/review/risky */
        private String suggest;
        private Integer label;
    }
}
