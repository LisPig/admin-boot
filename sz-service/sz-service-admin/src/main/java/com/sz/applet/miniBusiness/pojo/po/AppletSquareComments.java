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
import java.util.Date;

/**
 * 评论表 实体类。
 *
 * @author your-name
 * @since 1.0
 */
@Data
@Table(value = "applet_square_comments", onInsert = WeChatEntityChangeListener.class)
@Schema(description = "评论表")
public class AppletSquareComments implements Serializable {

    /**
     * 评论ID
     */
    @Id(keyType = KeyType.Auto)
    @Schema(description = "评论ID")
    private Long id;

    /**
     * 动态ID
     */
    @Column(value = "memo_id")
    @Schema(description = "动态ID")
    private Long memoId;

    /**
     * 评论用户ID
     */
    @Column(value = "user_id")
    @Schema(description = "评论用户ID")
    private Long userId;

    /**
     * 评论者用户名
     */
    @Column(value = "username")
    @Schema(description = "评论者用户名")
    private String username;

    /**
     * 评论内容
     */
    @Column(value = "content")
    @Schema(description = "评论内容")
    private String content;

    /**
     * 回复目标用户名
     */
    @Column(value = "reply_to")
    @Schema(description = "回复目标用户名")
    private String replyTo;

    /**
     * 回复目标评论ID
     */
    @Column(value = "reply_to_id")
    @Schema(description = "回复目标评论ID")
    private Long replyToId;

    /**
     * 创建时间
     */
    @Column(value = "create_time")
    @Schema(description = "创建时间")
    private Date createTime;

}