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
 * 校友会表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Accessors(chain = true)
@Data
@Table(value = "applet_alumni_association")
public class AppletAlumniAssociation {

    /**
     * ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 名称
     */
    @Column(value = "name")
    private String name;

    /**
     * 头像
     */
    @Column(value = "avatar")
    private String avatar;

    /**
     * 描述
     */
    @Column(value = "description")
    private String description;

    /**
     * 联系人
     */
    @Column(value = "contract")
    private String contract;

    /**
     * 联系电话
     */
    @Column(value = "phone")
    private String phone;

    /**
     * 状态（1-正常，2-禁用）
     */
    @Column(value = "status")
    private String status;

    @Column(value = "del_flag")
    private Object delFlag;

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
