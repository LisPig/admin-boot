package com.sz.applet.miniBusiness.pojo.po;

import com.sz.mysql.EntityChangeListener;
import com.sz.mysql.WeChatEntityChangeListener;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.io.Serial;
import java.io.Serializable;
import java.lang.Long;
import java.time.LocalDateTime;
import java.util.Date;
import java.lang.Object;
import java.lang.String;

/**
 * 校友会活动表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Accessors(chain = true)
@Data
@Table(value = "applet_alumni_association_activity", onInsert = WeChatEntityChangeListener.class, onUpdate = EntityChangeListener.class)
public class AppletAlumniAssociationActivity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 校友会ID
     */
    @Column(value = "alumni_association_id")
    private Long alumniAssociationId;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @Column(value = "title")
    private String title;

    /**
     * 头图
     */
    @Schema(description = "头图")
    @Column(value = "avatar")
    private String avatar;

    /**
     * 内容
     */
    @Schema(description = "内容")
    @Column(value = "content")
    private String content;

    /**
     * 时间
     */
    @Column(value = "time")
    private String time;

    /**
     * 地点
     */
    @Schema(description = "地点")
    @Column(value = "location")
    private String location;

    /**
     * 状态（1-正常，2-禁用）
     */
    @Schema(description = "状态")
    @Column(value = "status")
    private String status;

    @Schema(description = "开始时间")
    @Column(value = "start_time")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @Column(value = "end_time")
    private LocalDateTime endTime;

    @Column(value = "del_flag")
    private Object delFlag;

    /**
     * 创建时间
     */
    @Column(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 创建人ID
     */
    @Column(value = "create_id")
    private Long createId;

    /**
     * 更新人ID
     */
    @Column(value = "update_id")
    private Long updateId;


}
