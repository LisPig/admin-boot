package com.sz.applet.miniBusiness.service;

import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.po.AppletSquareFollows;

/**
 * 关注表 服务层。
 *
 * @author your-name
 * @since 1.0
 */
public interface AppletSquareFollowsService extends IService<AppletSquareFollows> {

    /**
     * 关注/取消关注用户
     * @param followedUserId 被关注用户ID
     * @return true表示关注成功，false表示取消关注成功
     */
    boolean followUser(Long followedUserId);
}