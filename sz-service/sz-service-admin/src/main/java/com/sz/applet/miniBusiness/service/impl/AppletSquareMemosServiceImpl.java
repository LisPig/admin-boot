package com.sz.applet.miniBusiness.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.admin.system.service.SysConfigService;
import com.sz.applet.miniBusiness.mapper.AppletSquareMemosMapper;
import com.sz.applet.miniBusiness.pojo.bo.MemoListBO;
import com.sz.applet.miniBusiness.pojo.dto.CommentSaveDTO;
import com.sz.applet.miniBusiness.pojo.dto.MemoCreateDTO;
import com.sz.applet.miniBusiness.pojo.dto.MemoLikeDTO;
import com.sz.applet.miniBusiness.pojo.po.*;
import com.sz.applet.miniBusiness.pojo.vo.CommentVO;
import com.sz.applet.miniBusiness.pojo.vo.MemoVO;
import com.sz.applet.miniBusiness.pojo.vo.UnreadNoticeVO;
import com.sz.applet.miniBusiness.pojo.vo.UserFollowVO;
import com.sz.applet.miniBusiness.service.AppletSquareCommentsService;
import com.sz.applet.miniBusiness.service.AppletSquareFollowsService;
import com.sz.applet.miniBusiness.service.AppletSquareLikesService;
import com.sz.applet.miniBusiness.service.AppletSquareMemosService;
import com.sz.applet.miniuser.mapper.MiniUserMapper;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.core.common.entity.MiniLoginUserDTO;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.common.exception.common.BusinessException;
import com.sz.core.common.sensitive.SensitiveWordUtils;
import com.sz.core.common.service.ConfService;
import com.sz.core.common.translate.TranslateUtil;
import com.sz.core.util.PageUtils;
import com.sz.redis.CommonKeyConstants;
import com.sz.redis.RedisLock;
import com.sz.redis.RedisUtils;
import com.sz.security.core.util.LoginUtils;
import com.sz.wechat.mini.MiniSubscribeTemplateConstants;
import com.sz.wechat.mini.MiniWechatService;
import com.sz.wechat.mini.pojo.dto.SubscribeMessageSendDTO;
import com.sz.wechat.mini.pojo.vo.SubscribeMessageSendVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private MiniUserMapper miniUserMapper;
    
    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private ConfService confService;

    @Autowired
    private RedisLock redisLock;
    
    @Autowired
    private MiniWechatService miniWechatService;

    @Autowired
    private TranslateUtil translateUtil;


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
        memo.setPosition(dto.getPosition());
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
        // 先分页获取实体对象数据集
        //PageResult<AppletSquareMemos> pageResult = PageUtils.getPageResult(page(PageUtils.getPage(bo), queryWrapper));
        //然后再转为VO
        //PageResult<MemoVO> pageResultVO = PageUtils.getPageResult(pageResult, MemoVO.class);
        // 分页查询
        PageResult<MemoVO> pageResult = PageUtils.getPageResult(pageAs(PageUtils.getPage(bo), queryWrapper, MemoVO.class));
        for(MemoVO memoVO : pageResult.getRows()){

            MiniUser miniUser = miniUserService.getOne(new QueryWrapper().select(MiniUser::getUsername, MiniUser::getAvatarUrl).eq(MiniUser::getId, memoVO.getUserId()));
            memoVO.setUsername(miniUser.getUsername());
            memoVO.setAvatar_url(miniUser.getAvatarUrl());

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

            // 获取是否已关注该用户
            QueryWrapper flowerQuery = new QueryWrapper();
            flowerQuery.eq(AppletSquareFollows::getFollowedUserId, memoVO.getUserId())
                    .eq(AppletSquareFollows::getUserId, userId);
            AppletSquareFollows flower = appletSquareFollowsService.getOne(flowerQuery);
            memoVO.setIsFollowed(flower != null);
        }
        translateUtil.translate(pageResult.getRows());
        return pageResult;
    }

    @Override
    public PageResult<MemoVO> list(MemoListBO bo) {
        // 构建查询条件
        QueryWrapper queryWrapper = this.buildQueryWrapper(bo);

        // 添加排序条件
        queryWrapper.orderBy(APPLET_SQUARE_MEMOS.CREATE_TIME, false);
        // 先分页获取实体对象数据集
        //PageResult<AppletSquareMemos> pageResult = PageUtils.getPageResult(page(PageUtils.getPage(bo), queryWrapper));
        //然后再转为VO
        //PageResult<MemoVO> pageResultVO = PageUtils.getPageResult(pageResult, MemoVO.class);
        // 分页查询
        PageResult<MemoVO> pageResult = PageUtils.getPageResult(pageAs(PageUtils.getPage(bo), queryWrapper, MemoVO.class));
        translateUtil.translate(pageResult.getRows());
        for(MemoVO memoVO : pageResult.getRows()){
            MiniUser miniUser = miniUserService.getOne(new QueryWrapper().select(MiniUser::getUsername, MiniUser::getAvatarUrl).eq(MiniUser::getId, memoVO.getUserId()));
            memoVO.setUsername(miniUser.getUsername());
            memoVO.setAvatar_url(miniUser.getAvatarUrl());
            // 获取评论列表
            memoVO.setComments(appletSquareCommentsService.listAs(new QueryWrapper().eq(AppletSquareComments::getMemoId, memoVO.getId()), CommentVO.class));
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
                
                // 增加被点赞用户的未读点赞数
                String unreadLikesKey = RedisUtils.getKey(CommonKeyConstants.USER_UNREAD_LIKES, memo.getUserId().toString());
                RedisUtils.getRestTemplate().opsForValue().increment(unreadLikesKey);
                
                // 发送微信订阅消息通知
                sendLikeNotice(memo);
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
        MiniLoginUserDTO loginUser = LoginUtils.getMiniLoginUser();
        Long userId = Objects.requireNonNull(LoginUtils.getMiniLoginUser().getUserId());
        String username = Objects.requireNonNull(LoginUtils.getMiniLoginUser().getUsername());

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
            
            // 增加被评论用户的未读评论数
            String unreadCommentsKey = RedisUtils.getKey(CommonKeyConstants.USER_UNREAD_COMMENTS, memo.getUserId().toString());
            RedisUtils.getRestTemplate().opsForValue().increment(unreadCommentsKey);
            
            // 发送微信订阅消息通知
            sendCommentNotice(memo, comment);
        }catch (Exception e){
            log.error("更新动态的评论数异常", e);
            throw new BusinessException(CommonResponseEnum.FAILURE,null, "更新动态的评论数异常");
        }
        finally {
           redisLock.releaseLock(lockStr, lockValue);
        }

    }
    
    /**
     * 发送点赞通知
     * @param memo 动态
     */
    private void sendLikeNotice(AppletSquareMemos memo) {
        try {
            // 检查是否开启微信通知
            String wechatNoticeEnabled = confService.getConfValue("wechat.notice");
            if (!"true".equals(wechatNoticeEnabled)) {
                log.info("微信通知未开启，跳过发送点赞通知");
                return;
            }
            
            // 获取被点赞用户的信息
            MiniUser targetUser = miniUserMapper.selectOneById(memo.getUserId());
            if (targetUser == null || targetUser.getOpenid() == null) {
                log.warn("被点赞用户信息不完整，无法发送通知");
                return;
            }
            
            // 获取当前登录用户信息
            MiniLoginUserDTO loginUser = LoginUtils.getMiniLoginUser();
            MiniUser currentUser = miniUserMapper.selectOneById(loginUser.getUserId());
            
            // 构造订阅消息
            SubscribeMessageSendDTO messageDTO = new SubscribeMessageSendDTO();
            messageDTO.setTouser(targetUser.getOpenid());
            messageDTO.setTemplate_id(MiniSubscribeTemplateConstants.LIKE_NOTICE_TEMPLATE_ID);
            messageDTO.setPage("/pages/square/index"); // 点击跳转到广场页面
            
            // 设置消息内容
            Map<String, SubscribeMessageSendDTO.TemplateData> data = new HashMap<>();
            data.put("thing1", new SubscribeMessageSendDTO.TemplateData(currentUser.getNickname()));
            data.put("thing2", new SubscribeMessageSendDTO.TemplateData("点赞了你的动态"));
            data.put("time3", new SubscribeMessageSendDTO.TemplateData(java.time.LocalDateTime.now().toString()));
            messageDTO.setData(data);
            
            // 发送订阅消息
            String accessToken = miniWechatService.getAccessToken();
            SubscribeMessageSendVO result = miniWechatService.sendSubscribeMessage(accessToken, messageDTO);
            
            if (result.getErrcode() != null && result.getErrcode() != 0) {
                log.error("发送点赞通知失败，错误码：{}，错误信息：{}", result.getErrcode(), result.getErrmsg());
            } else {
                log.info("发送点赞通知成功");
            }
        } catch (Exception e) {
            log.error("发送点赞通知异常", e);
        }
    }
    
    /**
     * 发送评论通知
     * @param memo 动态
     * @param comment 评论
     */
    private void sendCommentNotice(AppletSquareMemos memo, AppletSquareComments comment) {
        try {
            // 检查是否开启微信通知
            String wechatNoticeEnabled = confService.getConfValue("wechat.notice");
            if (!"true".equals(wechatNoticeEnabled)) {
                log.info("微信通知未开启，跳过发送评论通知");
                return;
            }
            
            // 获取被评论用户的信息
            MiniUser targetUser = miniUserMapper.selectOneById(memo.getUserId());
            if (targetUser == null || targetUser.getOpenid() == null) {
                log.warn("被评论用户信息不完整，无法发送通知");
                return;
            }
            
            // 获取当前登录用户信息
            MiniLoginUserDTO loginUser = LoginUtils.getMiniLoginUser();
            MiniUser currentUser = miniUserMapper.selectOneById(loginUser.getUserId());
            
            // 构造订阅消息
            SubscribeMessageSendDTO messageDTO = new SubscribeMessageSendDTO();
            messageDTO.setTouser(targetUser.getOpenid());
            messageDTO.setTemplate_id(MiniSubscribeTemplateConstants.COMMENT_NOTICE_TEMPLATE_ID);
            messageDTO.setPage("/pages/square/index"); // 点击跳转到广场页面
            
            // 设置消息内容
            Map<String, SubscribeMessageSendDTO.TemplateData> data = new HashMap<>();
            data.put("thing1", new SubscribeMessageSendDTO.TemplateData(currentUser.getNickname()));
            data.put("thing2", new SubscribeMessageSendDTO.TemplateData(comment.getContent()));
            data.put("time3", new SubscribeMessageSendDTO.TemplateData(java.time.LocalDateTime.now().toString()));
            messageDTO.setData(data);
            
            // 发送订阅消息
            String accessToken = miniWechatService.getAccessToken();
            SubscribeMessageSendVO result = miniWechatService.sendSubscribeMessage(accessToken, messageDTO);
            
            if (result.getErrcode() != null && result.getErrcode() != 0) {
                log.error("发送评论通知失败，错误码：{}，错误信息：{}", result.getErrcode(), result.getErrmsg());
            } else {
                log.info("发送评论通知成功");
            }
        } catch (Exception e) {
            log.error("发送评论通知异常", e);
        }
    }
    
    /**
     * 获取用户未读提醒数
     * @return 未读提醒VO
     */
    public UnreadNoticeVO getUnreadNotice() {
        Long userId = Objects.requireNonNull(LoginUtils.getMiniLoginUser().getUserId());
        
        UnreadNoticeVO vo = new UnreadNoticeVO();
        
        // 获取未读点赞数
        String unreadLikesKey = RedisUtils.getKey(CommonKeyConstants.USER_UNREAD_LIKES, userId.toString());
        Object unreadLikesObj = RedisUtils.getValue(unreadLikesKey);
        if (unreadLikesObj != null) {
            vo.setUnreadLikes(Long.valueOf(unreadLikesObj.toString()));
        }
        
        // 获取未读评论数
        String unreadCommentsKey = RedisUtils.getKey(CommonKeyConstants.USER_UNREAD_COMMENTS, userId.toString());
        Object unreadCommentsObj = RedisUtils.getValue(unreadCommentsKey);
        if (unreadCommentsObj != null) {
            vo.setUnreadComments(Long.valueOf(unreadCommentsObj.toString()));
        }
        
        return vo;
    }
    
    /**
     * 清除用户未读提醒数
     * @param type 提醒类型 1-点赞 2-评论
     */
    public void clearUnreadNotice(Integer type) {
        Long userId = Objects.requireNonNull(LoginUtils.getMiniLoginUser().getUserId());
        
        if (type == 1) {
            // 清除未读点赞数
            String unreadLikesKey = RedisUtils.getKey(CommonKeyConstants.USER_UNREAD_LIKES, userId.toString());
            RedisUtils.removeKey(unreadLikesKey);
        } else if (type == 2) {
            // 清除未读评论数
            String unreadCommentsKey = RedisUtils.getKey(CommonKeyConstants.USER_UNREAD_COMMENTS, userId.toString());
            RedisUtils.removeKey(unreadCommentsKey);
        }
    }


    public QueryWrapper buildQueryWrapper(MemoListBO bo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(AppletSquareMemos::getTagName, bo.getTagName(), ObjectUtil.isNotNull(bo.getTagName()));
        // 可以根据需要添加其他查询条件
        queryWrapper.eq(AppletSquareMemos::getUserId, bo.getUserId(), ObjectUtil.isNotNull(bo.getUserId()));
        if(ObjectUtil.isNotNull(bo.getMyFocus())) {
            if (bo.getMyFocus()) {
                List<Long> focusUserIds = appletSquareFollowsService.list(new QueryWrapper()
                                .eq(AppletSquareFollows::getUserId, LoginUtils.getMiniLoginUser().getUserId()))
                        .stream().map(AppletSquareFollows::getFollowedUserId).toList();
                
                // 如果用户没有任何关注，则返回空结果
                if (focusUserIds.isEmpty()) {
                    // 添加一个永远不成立的条件，确保返回空结果
                    queryWrapper.eq("1", "0");
                } else {
                    queryWrapper.in(AppletSquareMemos::getUserId, focusUserIds);
                }
            }
        }
        return queryWrapper;
    }
}