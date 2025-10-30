package com.sz.applet.miniBusiness.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.applet.miniBusiness.mapper.AppletSquareMemosMapper;
import com.sz.applet.miniBusiness.pojo.bo.MemoListBO;
import com.sz.applet.miniBusiness.pojo.dto.CommentSaveDTO;
import com.sz.applet.miniBusiness.pojo.dto.MemoCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.MemoLikeDTO;
import com.sz.applet.miniBusiness.pojo.po.*;
import com.sz.applet.miniBusiness.pojo.vo.CommentVO;
import com.sz.applet.miniBusiness.pojo.vo.MemoVO;
import com.sz.applet.miniBusiness.pojo.vo.UserFollowVO;
import com.sz.applet.miniBusiness.service.AppletSquareCommentsService;
import com.sz.applet.miniBusiness.service.AppletSquareFollowsService;
import com.sz.applet.miniBusiness.service.AppletSquareLikesService;
import com.sz.applet.miniBusiness.service.AppletSquareMemosService;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.common.exception.common.BusinessException;
import com.sz.core.common.sensitive.SensitiveWordUtils;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.PageUtils;
import com.sz.redis.CommonKeyConstants;
import com.sz.redis.RedisLock;
import com.sz.redis.RedisUtils;
import com.sz.security.core.util.LoginUtils;
import com.sz.utils.MapstructUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sz.applet.miniBusiness.pojo.po.table.AppletSquareMemosTableDef.APPLET_SQUARE_MEMOS;

/**
 * 动态表 服务层实现。
 *
 * @author your-name
 * @since 1.0
 */
@Slf4j
@Service
public class AppletSquareMemosServiceImpl extends ServiceImpl<AppletSquareMemosMapper, AppletSquareMemos> implements AppletSquareMemosService {

    @Autowired
    private AppletSquareLikesService appletSquareLikesService;

    @Autowired
    private AppletSquareCommentsService appletSquareCommentsService;

    @Autowired
    private AppletSquareFollowsService appletSquareFollowsService;

    @Autowired
    private MiniUserService miniUserService;

    @Autowired
    private RedisLock redisLock;


    @Override
    public void createMemo(MemoCreateDTO dto) {
        // 检查内容是否包含敏感词
        if (SensitiveWordUtils.containsSensitiveWord(dto.getContent())) {
            List<String> sensitiveWords = SensitiveWordUtils.getSensitiveWords(dto.getContent());
            throw new BusinessException(CommonResponseEnum.PARAM_ERROR, null, "内容违规: " + String.join(", ", sensitiveWords));
        }

        AppletSquareMemos memo = new AppletSquareMemos();
        memo.setUserId(Objects.requireNonNull(LoginUtils.getMiniLoginUser()).getUserId());
        memo.setContent(dto.getContent());
        memo.setImgs(dto.getImgs());
        memo.setTagName(dto.getTagName());
        this.save(memo);
    }

    @Override
    public PageResult<MemoVO> listMemos(MemoListBO bo) {
        // 获取当前登录用户ID
        Long userId = Objects.requireNonNull(LoginUtils.getMiniLoginUser()).getUserId();

        // 构建查询条件
        QueryWrapper queryWrapper = this.buildQueryWrapper(bo);

        // 添加排序条件
        queryWrapper.orderBy(APPLET_SQUARE_MEMOS.CREATE_TIME, false);
        //PageResult<AppletSquareMemos> pageResult = PageUtils.getPageResult(page(PageUtils.getPage(bo), queryWrapper));
        //PageResult<MemoVO> pageResultVO = PageUtils.getPageResult(pageResult, MemoVO.class);
        // 分页查询
        PageResult<MemoVO> pageResult = PageUtils.getPageResult(pageAs(PageUtils.getPage(bo), queryWrapper, MemoVO.class));
        for(MemoVO memoVO : pageResult.getRows()){
            // 获取点赞用户列表
            List<AppletSquareLikes> appletSquareLikes = appletSquareLikesService.list(new QueryWrapper().eq(AppletSquareLikes::getMemoId, memoVO.getId()));
            // 提取appletSquareLikes里的userId为数组
            List<Long> userIds = appletSquareLikes.stream().map(AppletSquareLikes::getUserId).toList();
            List<MiniUser> miniUsers = miniUserService.list(new QueryWrapper().create().select(MiniUser::getId, MiniUser::getName, MiniUser::getAvatarUrl).in(MiniUser::getId, userIds));
            memoVO.setLikers(miniUsers.stream().map(user -> {
                MiniUserVO vo = new MiniUserVO();
                vo.setId(user.getId());
                vo.setName(user.getName());
                vo.setAvatarUrl(user.getAvatarUrl());
                return vo;
            }).toList());

            // 获取关注用户列表
            memoVO.setFollowers(appletSquareFollowsService.listAs(new QueryWrapper().select(AppletSquareFollows::getUserId).eq(AppletSquareFollows::getFollowedUserId, memoVO.getUserId()), UserFollowVO.class));
            
            // 获取评论列表
            memoVO.setComments(appletSquareCommentsService.listAs(new QueryWrapper().eq(AppletSquareComments::getMemoId, memoVO.getId()), CommentVO.class));
            
            // 判断当前用户是否已点赞该动态
            QueryWrapper likeQuery = new QueryWrapper();
            likeQuery.eq(AppletSquareLikes::getMemoId, memoVO.getId());
            likeQuery.eq(AppletSquareLikes::getUserId, userId);
            AppletSquareLikes userLike = appletSquareLikesService.getOne(likeQuery);
            memoVO.setIsLiked(userLike != null);

            // 获取是否关注该用户
            QueryWrapper flowerQuery = new QueryWrapper();
            flowerQuery.eq(AppletSquareFollows::getUserId, memoVO.getUserId());
            AppletSquareFollows flower = appletSquareFollowsService.getOne(flowerQuery);
            memoVO.setIsFollowed(flower != null);
        }
        return pageResult;
    }

    @Override
    @Transactional
    public void likeMemo(MemoLikeDTO dto) {
        //设置分布式锁
        String lockKey = CommonKeyConstants.MEMO_LIKE_LOCK + dto.getMemoId();
        String lockValue = redisLock.tryLock(lockKey);
        // 获取当前登录用户ID
        Long userId = Objects.requireNonNull(LoginUtils.getMiniLoginUser().getUserId());
        try {
            // 检查是否已经点赞
            QueryWrapper likeQuery = new QueryWrapper();
            likeQuery.eq(AppletSquareLikes::getMemoId, dto.getMemoId());
            likeQuery.eq(AppletSquareLikes::getUserId, userId);
            AppletSquareLikes existingLike = appletSquareLikesService.getOne(likeQuery);

            // 获取动态信息
            AppletSquareMemos memo = this.getById(dto.getMemoId());
            if (memo == null) {
                throw new BusinessException(CommonResponseEnum.NOT_EXISTS, null, "动态不存在");
            }

            if (existingLike != null) {
                // 已点赞，执行取消点赞操作
                appletSquareLikesService.removeById(existingLike.getId());
                // 减少点赞数
                memo.setLikeCount(Math.max(0, memo.getLikeCount() - 1));
            } else {
                // 未点赞，执行点赞操作
                AppletSquareLikes like = new AppletSquareLikes();
                like.setMemoId(dto.getMemoId());
                like.setUserId(userId);
                like.setLinkedUser(memo.getUserId());
                appletSquareLikesService.save(like);
                // 增加点赞数
                memo.setLikeCount(memo.getLikeCount() + 1);
            }

            // 更新动态的点赞数
            this.updateById(memo);
        }catch (Exception e){
            log.error("点赞失败", e);
            throw new BusinessException(CommonResponseEnum.FAILURE,null, "点赞失败");
        }finally {
            redisLock.releaseLock(lockKey, lockValue);
        }

    }

    @Override
    @Transactional
    public void saveComment(CommentSaveDTO dto) {
        // 获取当前登录用户信息
        Long userId = Objects.requireNonNull(LoginUtils.getMiniLoginUser().getUserId());
        String username = Objects.requireNonNull(LoginUtils.getMiniLoginUser().getNickname());

        // 检查评论内容是否包含敏感词
        if (SensitiveWordUtils.containsSensitiveWord(dto.getContent())) {
            List<String> sensitiveWords = SensitiveWordUtils.getSensitiveWords(dto.getContent());
            throw new BusinessException(CommonResponseEnum.PARAM_ERROR, null, "评论内容违规: " + String.join(", ", sensitiveWords));
        }

        // 检查回复目标用户名是否包含敏感词
        if (dto.getReplyTo() != null && SensitiveWordUtils.containsSensitiveWord(dto.getReplyTo())) {
            List<String> sensitiveWords = SensitiveWordUtils.getSensitiveWords(dto.getReplyTo());
            throw new BusinessException(CommonResponseEnum.PARAM_ERROR, null, "回复目标用户名违规: " + String.join(", ", sensitiveWords));
        }

        // 检查动态是否存在
        AppletSquareMemos memo = this.getById(dto.getMemoId());
        if (memo == null) {
            throw new BusinessException(CommonResponseEnum.NOT_EXISTS, null, "动态不存在");
        }

        // 保存评论
        AppletSquareComments comment = new AppletSquareComments();
        comment.setMemoId(dto.getMemoId());
        comment.setUserId(userId);
        comment.setUsername(username);
        comment.setContent(dto.getContent());
        comment.setReplyTo(dto.getReplyTo());
        comment.setReplyToId(dto.getReplyToId());
        appletSquareCommentsService.save(comment);

        // 更新动态的评论数
        String lockStr = CommonKeyConstants.MEMO_COMMENT_LOCK + dto.getMemoId();
        String lockValue = redisLock.tryLock(lockStr);

        try {
            memo.setCommentCount(memo.getCommentCount() + 1);
            this.updateById(memo);
        }catch (Exception e){
            log.error("更新动态的评论数异常", e);
            throw new BusinessException(CommonResponseEnum.FAILURE,null, "更新动态的评论数异常");
        }
        finally {
           redisLock.releaseLock(lockStr, lockValue);
        }

    }


    public QueryWrapper buildQueryWrapper(MemoListBO bo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(AppletSquareMemos::getTagName, bo.getTagName(), ObjectUtil.isNotNull(bo.getTagName()));
        // 可以根据需要添加其他查询条件
        queryWrapper.eq(AppletSquareMemos::getUserId, bo.getUserId(), ObjectUtil.isNotNull(bo.getUserId()));
        return queryWrapper;
    }
}