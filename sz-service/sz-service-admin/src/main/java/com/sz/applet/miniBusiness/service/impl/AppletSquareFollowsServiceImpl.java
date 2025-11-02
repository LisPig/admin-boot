package com.sz.applet.miniBusiness.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.applet.miniBusiness.mapper.AppletSquareFollowsMapper;
import com.sz.applet.miniBusiness.pojo.bo.UserFollowListBO;
import com.sz.applet.miniBusiness.pojo.po.AppletSquareFollows;
import com.sz.applet.miniBusiness.pojo.vo.UserFollowVO;
import com.sz.applet.miniBusiness.service.AppletSquareFollowsService;
import com.sz.applet.miniuser.mapper.MiniUserMapper;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.core.common.entity.PageResult;
import com.sz.core.util.PageUtils;
import com.sz.security.core.util.LoginUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sz.applet.miniBusiness.pojo.po.table.AppletSquareFollowsTableDef.APPLET_SQUARE_FOLLOWS;

/**
 * 关注表 服务层实现。
 *
 * @author your-name
 * @since 1.0
 */
@Service
public class AppletSquareFollowsServiceImpl extends ServiceImpl<AppletSquareFollowsMapper, AppletSquareFollows> implements AppletSquareFollowsService {

    private final MiniUserMapper miniUserMapper;

    public AppletSquareFollowsServiceImpl(MiniUserMapper miniUserMapper) {
        this.miniUserMapper = miniUserMapper;
    }

    @Override
    public boolean followUser(Long followedUserId) {
        // 获取当前登录用户ID
        Long userId = Objects.requireNonNull(LoginUtils.getMiniLoginUser().getUserId());

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

    @Override
    public PageResult<UserFollowVO> getFollowList(UserFollowListBO bo) {
        Long userId = Objects.requireNonNull(LoginUtils.getMiniLoginUser().getUserId());
        bo.setUserId(userId);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(AppletSquareFollows::getUserId, userId);
        // 构建分页对象
        PageResult<AppletSquareFollows> page = PageUtils.getPageResult(page(PageUtils.getPage(bo), queryWrapper));

        
        // 转换为VO对象
        List<UserFollowVO> voList = page.getRows().stream().map(follow -> {
            UserFollowVO vo = new UserFollowVO();
            BeanUtils.copyProperties(follow, vo);
            
            // 查询被关注用户的信息
            MiniUser followedUser = miniUserMapper.selectOneById(follow.getFollowedUserId());
            if (followedUser != null) {
                vo.setUserId(followedUser.getId());
                vo.setFollowedUserNickname(followedUser.getNickname());
                vo.setFollowedUserAvatar(followedUser.getAvatarUrl());
            }
            
            return vo;
        }).collect(Collectors.toList());
        PageResult<UserFollowVO> result = new PageResult<>(page.getCurrent(), page.getTotal(), page.getTotalPage(), page.getLimit(),  voList);
        
        return result;
    }
}