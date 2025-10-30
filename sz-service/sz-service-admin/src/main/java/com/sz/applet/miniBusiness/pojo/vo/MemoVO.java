package com.sz.applet.miniBusiness.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 动态VO
 * </p>
 *
 * @author your-name
 * @since 2025-10-28
 */
@Data
@Schema(description = "动态VO")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemoVO {

    @Schema(description = "动态ID")
    private Long id;

    @Schema(description = "发布用户ID")
    private Long userId;

    @Schema(description = "发布者用户名")
    private String username;

    @Schema(description = "动态内容")
    private String content;

    @Schema(description = "图片链接列表")
    private List<String> imgs;

    @Schema(description = "话题标签")
    private String tagName;

    @Schema(description = "点赞数")
    private Integer likeCount;

    @Schema(description = "评论数")
    private Integer commentCount;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "是否已点赞")
    private Boolean isLiked;

    @Schema(description = "是否已关注")
    private Boolean isFollowed;

    @Schema(description = "点赞用户列表")
    private List<MiniUserVO> likers;

    @Schema(description = "关注用户列表")
    private List<UserFollowVO> followers;
    
    @Schema(description = "评论列表")
    private List<CommentVO> comments;

}