package com.sz.admin.system.service;

/**
 * 微信图片内容安全校验(media_check_async)服务
 *
 * @author sz
 * @since 2026-08-15
 */
public interface MediaCheckService {

    /**
     * 该文件是否应提交内容安全校验
     *
     * @param dirTag      目录标识
     * @param contentType 文件类型
     * @param url         文件URL
     * @return true=需要校验
     */
    boolean shouldCheck(String dirTag, String contentType, String url);

    /**
     * 异步提交图片内容安全校验(不阻塞上传)
     *
     * @param fileId    文件ID
     * @param mediaUrl  图片URL
     */
    void submitAsyncCheck(Long fileId, String mediaUrl);

    /**
     * 处理微信 wxa_media_check 回调结果
     *
     * @param traceId 任务ID
     * @param suggest 建议(pass/review/risky)
     * @param label   违规标签
     */
    void handleCallbackResult(String traceId, String suggest, Integer label);

    /**
     * 读路径统一入口:违规(或配置隐藏的复审)图片返回空串,其余原样返回
     *
     * @param url 头像URL
     * @return 可展示的URL(违规返回 "")
     */
    String resolveAvatarUrl(String url);

    /**
     * 从数据库重建违规URL缓存
     */
    void refreshRiskyCache();

}
