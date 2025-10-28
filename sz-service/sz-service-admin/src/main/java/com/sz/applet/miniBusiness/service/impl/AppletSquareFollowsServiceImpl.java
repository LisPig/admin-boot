package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.applet.miniBusiness.mapper.AppletSquareFollowsMapper;
import com.sz.applet.miniBusiness.pojo.po.AppletSquareFollows;
import com.sz.applet.miniBusiness.service.AppletSquareFollowsService;
import com.sz.security.core.util.LoginUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.sz.applet.miniBusiness.pojo.po.table.AppletSquareFollowsTableDef.APPLET_SQUARE_FOLLOWS;

/**
 * 关注表 服务层实现。
 *
 * @author your-name
 * @since 1.0
 */
@Service
public class AppletSquareFollowsServiceImpl extends ServiceImpl<AppletSquareFollowsMapper, AppletSquareFollows> implements AppletSquareFollowsService {

    @Override
    public boolean followUser(Long followedUserId) {
        // 获取当前登录用户ID
        Long userId = Objects.requireNonNull(LoginUtils.getLoginUser()).getUserInfo().getId();

        // 检查是否已经关注该用户
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(AppletSquareFollows::getUserId, userId);
        queryWrapper.eq(AppletSquareFollows::getFollowedUserId, followedUserId);
        AppletSquareFollows existingFollow = this.getOne(queryWrapper);

        if (existingFollow != null) {
            // 已关注，执行取消关注操作
            this.removeById(existingFollow.getId());
            return false; // 取消关注成功
        } else {
            // 未关注，执行关注操作
            AppletSquareFollows follow = new AppletSquareFollows();
            follow.setUserId(userId);
            follow.setFollowedUserId(followedUserId);
            this.save(follow);
            return true; // 关注成功
        }
    }
}