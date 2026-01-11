package com.sz.applet.miniBusiness.pojo.po;

import lombok.Data;
import lombok.experimental.Accessors;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.lang.Long;
import java.util.Date;
import java.lang.Object;
import java.lang.String;

/**
 * 校友会用户表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Accessors(chain = true)
@Data
@Table(value = "applet_alumni_association_user")
public class AppletAlumniAssociationUser {

    /**
     * ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户ID
     */
    @Column(value = "user_id")
    private Long userId;

    /**
     * 校友会ID
     */
    @Column(value = "alumni_association_id")
    private Long alumniAssociationId;

    /**
     * 身份（1-普通成员，2-管理员）
     */
    @Column(value = "identity")
    private String identity;

    /**
     * 状态（1-正常，2-禁用）
     */
    @Column(value = "status")
    private String status;

    @Column(value = "del_flag", isLogicDelete = true)
    private String delFlag;

    /**
     * 创建时间
     */
    @Column(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(value = "update_time")
    private Date updateTime;

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
