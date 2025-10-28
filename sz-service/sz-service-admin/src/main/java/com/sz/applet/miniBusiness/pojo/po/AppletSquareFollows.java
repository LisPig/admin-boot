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
 * 关注表 实体类。
 *
 * @author your-name
 * @since 1.0
 */
@Data
@Table(value = "applet_square_follows", onInsert = WeChatEntityChangeListener.class)
@Schema(description = "关注表")
public class AppletSquareFollows implements Serializable {

    /**
     * 关注ID
     */
    @Id(keyType = KeyType.Auto)
    @Schema(description = "关注ID")
    private Long id;

    /**
     * 关注者ID
     */
    @Column(value = "user_id")
    @Schema(description = "关注者ID")
    private Long userId;

    /**
     * 被关注者ID
     */
    @Column(value = "followed_user_id")
    @Schema(description = "被关注者ID")
    private Long followedUserId;

    /**
     * 创建时间
     */
    @Column(value = "create_time")
    @Schema(description = "创建时间")
    private Date createTime;

}