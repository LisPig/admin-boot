package com.sz.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.sz.admin.system.mapper.CommonFileMapper;
import com.sz.admin.system.pojo.po.SysFile;
import com.sz.admin.system.service.MediaCheckService;
import com.sz.redis.RedisUtils;
import com.sz.wechat.WechatProperties;
import com.sz.wechat.mini.MiniWechatService;
import com.sz.wechat.pojo.MediaCheckAsyncResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 微信图片内容安全校验(media_check_async)实现
 *
 * <p>sys_file 靠 check_trace_id 与回调匹配;业务表存的是 URL 字符串,
 * 故「违规」以 URL 为单位缓存到 Redis SET(sz:wechat:media:risky),读路径只做 O(1) 判断。</p>
 *
 * @author sz
 * @since 2026-08-15
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MediaCheckServiceImpl implements MediaCheckService {

    /** 违规URL缓存集合 */
    private static final String RISKY_KEY = "wechat:media:risky";
    /** 当日配额超限标志 */
    private static final String QUOTA_EXCEED_KEY = "wechat:media:quota_exceed_today";
    /** 微信「今日接口流量耗尽」错误码 */
    private static final Integer ERRCODE_QUOTA = 45009;

    private final CommonFileMapper sysFileMapper;
    private final MiniWechatService miniWechatService;
    private final WechatProperties wechatProperties;

    @Override
    public boolean shouldCheck(String dirTag, String contentType, String url) {
        WechatProperties.MediaCheckProperties cfg = wechatProperties.getMediaCheck();
        if (cfg == null || !Boolean.TRUE.equals(cfg.getEnabled())) {
            return false;
        }
        if (StrUtil.isBlank(url) || !(url.startsWith("http://") || url.startsWith("https://"))) {
            return false;
        }
        if (StrUtil.isBlank(contentType) || !contentType.startsWith("image/")) {
            return false;
        }
        if (cfg.getDirTags() == null || !cfg.getDirTags().contains(dirTag)) {
            return false;
        }
        return true;
    }

    @Async("mediaCheckExecutor")
    @Override
    public void submitAsyncCheck(Long fileId, String mediaUrl) {
        try {
            if (Boolean.TRUE.equals(RedisUtils.hasKey(QUOTA_EXCEED_KEY))) {
                log.warn("微信内容安全校验当日配额超限,跳过 fileId={}", fileId);
                return;
            }
            String accessToken = miniWechatService.getAccessToken();
            if (StrUtil.isBlank(accessToken)) {
                markError(fileId, "empty access_token");
                return;
            }
            MediaCheckAsyncResult result = miniWechatService.mediaCheckAsync(accessToken, mediaUrl);
            if (result == null) {
                markError(fileId, "null response");
                return;
            }
            if (result.getErrcode() == null || result.getErrcode() == 0) {
                if (StrUtil.isNotBlank(result.getTraceId())) {
                    SysFile file = new SysFile();
                    file.setId(fileId);
                    file.setCheckTraceId(result.getTraceId());
                    file.setCheckStatus("PENDING");
                    sysFileMapper.update(file);
                    log.info("media_check_async 提交成功 fileId={} traceId={}", fileId, result.getTraceId());
                }
            } else if (ERRCODE_QUOTA.equals(result.getErrcode())) {
                RedisUtils.getRestTemplate().opsForValue().set(QUOTA_EXCEED_KEY, "1", 12, TimeUnit.HOURS);
                log.warn("media_check_async 当日配额超限 errcode={}", result.getErrcode());
                markError(fileId, result.getErrmsg());
            } else {
                markError(fileId, result.getErrmsg());
            }
        } catch (Exception e) {
            log.error("media_check_async 提交异常 fileId={}", fileId, e);
            markError(fileId, e.getMessage());
        }
    }

    @Override
    public void handleCallbackResult(String traceId, String suggest, Integer label) {
        if (StrUtil.isBlank(traceId)) {
            log.warn("media_check 回调缺少trace_id");
            return;
        }
        SysFile file = sysFileMapper.selectOneByQuery(
                QueryWrapper.create().from(SysFile.class).eq(SysFile::getCheckTraceId, traceId));
        if (file == null) {
            log.warn("media_check 回调未匹配到文件 traceId={}", traceId);
            return;
        }
        String status = normalizeSuggest(suggest);
        file.setCheckStatus(status);
        file.setCheckLabel(label);
        file.setCheckTime(LocalDateTime.now());
        sysFileMapper.update(file);

        boolean hide = "RISKY".equals(status)
                || ("REVIEW".equals(status) && isHideOnReview());
        if (hide && StrUtil.isNotBlank(file.getUrl())) {
            RedisUtils.getRestTemplate().opsForSet().add(RISKY_KEY, file.getUrl());
        }
        log.info("media_check 回调落库 fileId={} status={} label={}", file.getId(), status, label);
    }

    @Override
    public String resolveAvatarUrl(String url) {
        if (StrUtil.isBlank(url) || !(url.startsWith("http://") || url.startsWith("https://"))) {
            return url;
        }
        try {
            SetOperations<Object, Object> setOps = RedisUtils.getRestTemplate().opsForSet();
            if (Boolean.TRUE.equals(setOps.isMember(RISKY_KEY, url))) {
                return "";
            }
            // 缓存冷启动/被清 → 从DB重建一次,避免每次渲染都查库
            if (!RedisUtils.hasKey(RISKY_KEY)) {
                refreshRiskyCache();
                if (Boolean.TRUE.equals(setOps.isMember(RISKY_KEY, url))) {
                    return "";
                }
            }
            return url;
        } catch (Exception e) {
            log.warn("resolveAvatarUrl 校验异常,回退原始url", e);
            return url;
        }
    }

    @Override
    public void refreshRiskyCache() {
        try {
            List<SysFile> riskyList = sysFileMapper.selectListByQuery(
                    QueryWrapper.create().from(SysFile.class).eq(SysFile::getCheckStatus, "RISKY"));
            List<String> urls = riskyList.stream()
                    .map(SysFile::getUrl)
                    .filter(Objects::nonNull)
                    .distinct()
                    .toList();
            if (!urls.isEmpty()) {
                RedisUtils.getRestTemplate().opsForSet().add(RISKY_KEY, urls.toArray());
            }
        } catch (Exception e) {
            log.error("refreshRiskyCache 重建失败", e);
        }
    }

    private boolean isHideOnReview() {
        WechatProperties.MediaCheckProperties cfg = wechatProperties.getMediaCheck();
        return cfg != null && Boolean.TRUE.equals(cfg.getHideOnReview());
    }

    private void markError(Long fileId, String msg) {
        try {
            SysFile file = new SysFile();
            file.setId(fileId);
            file.setCheckStatus("ERROR");
            sysFileMapper.update(file);
            log.warn("media_check_async 提交失败 fileId={} msg={}", fileId, msg);
        } catch (Exception ex) {
            log.error("markError 失败 fileId={}", fileId, ex);
        }
    }

    private String normalizeSuggest(String suggest) {
        if (suggest == null) {
            return "PENDING";
        }
        return switch (suggest.toLowerCase()) {
            case "pass" -> "PASS";
            case "review" -> "REVIEW";
            case "risky" -> "RISKY";
            default -> "PENDING";
        };
    }
}
