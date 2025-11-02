package com.sz.applet.miniBusiness.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.sz.mysql.EntityChangeListener;
import com.sz.mysql.WeChatEntityChangeListener;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 动态表 实体类。
 *
 * @author your-name
 * @since 1.0
 */
@Data
@Table(value = "applet_square_memos", onInsert = WeChatEntityChangeListener.class, onUpdate = WeChatEntityChangeListener.class)
@Schema(description = "动态表")
public class AppletSquareMemos implements Serializable {

    /**
     * 动态ID
     */
    @Id(keyType = KeyType.Auto)
    @Schema(description = "动态ID")
    private Long id;

    /**
     * 发布用户ID
     */
    @Column(value = "user_id")
    @Schema(description = "发布用户ID")
    private Long userId;

    /**
     * 动态内容
     */
    @Column(value = "content")
    @Schema(description = "动态内容")
    private String content;

    /**
     * 图片链接，逗号分隔
     */
    @Column(value = "imgs")
    @Schema(description = "图片链接，逗号分隔")
    private String imgs;

    /**
     * 标签ID
     */
    @Column(value = "tag_id")
    private Long tagId;

    /**
     * 话题标签
     */
    @Column(value = "tag_name")
    @Schema(description = "话题标签")
    private String tagName;


    /**
     * 位置
     */
    @Column(value = "position")
    @Schema(description = "位置")
    private String position;

    /**
     * 点赞数
     */
    @Column(value = "like_count")
    @Schema(description = "点赞数")
    private Integer likeCount = 0;

    /**
     * 评论数
     */
    @Column(value = "comment_count")
    @Schema(description = "评论数")
    private Integer commentCount = 0;

    /**
     * 创建时间
     */
    @Column(value = "create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(value = "update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

}