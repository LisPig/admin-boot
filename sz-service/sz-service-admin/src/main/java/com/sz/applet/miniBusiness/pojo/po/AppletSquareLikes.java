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
 * 点赞表 实体类。
 *
 * @author your-name
 * @since 1.0
 */
@Data
@Table(value = "applet_square_likes", onInsert = WeChatEntityChangeListener.class)
@Schema(description = "点赞表")
public class AppletSquareLikes implements Serializable {

    /**
     * 点赞ID
     */
    @Id(keyType = KeyType.Auto)
    @Schema(description = "点赞ID")
    private Long id;

    /**
     * 动态ID
     */
    @Column(value = "memo_id")
    @Schema(description = "动态ID")
    private Long memoId;

    /**
     * 点赞用户ID
     */
    @Column(value = "user_id")
    @Schema(description = "点赞用户ID")
    private Long userId;

    /**
     * 被点赞用户ID
     */
    @Column(value = "linked_user")
    @Schema(description = "被点赞用户ID")
    private Long linkedUser;

    /**
     * 创建时间
     */
    @Column(value = "create_time")
    @Schema(description = "创建时间")
    private Date createTime;

}