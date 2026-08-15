package com.sz.wechat.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信 media_check_async 提交结果
 *
 * @author sz
 * @since 2026-08-15
 */
@Data
public class MediaCheckAsyncResult {

    private Integer errcode;

    private String errmsg;

    @JsonProperty("trace_id")
    private String traceId;

}
