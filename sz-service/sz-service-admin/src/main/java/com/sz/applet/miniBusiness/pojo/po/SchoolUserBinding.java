package com.sz.applet.miniBusiness.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.sz.mysql.EntityChangeListener;
import lombok.Data;

import java.util.Date;

@Data
@Table(value = "school_user_binding", onInsert = EntityChangeListener.class, onUpdate = EntityChangeListener.class)
public class SchoolUserBinding {
    
    @Id(keyType = KeyType.Auto)
    private Long id;

    @Column(value = "school_user_id")
    private Long schoolUserId; // 学校用户ID

    @Column(value = "mini_user_id")
    private Long miniUserId; // 小程序用户ID

    @Column(value = "bind_type")
    private Integer bindType; // 绑定类型：1-主绑定（认证），2-辅助绑定（共享）

    @Column(value = "status")
    private Integer status; // 绑定状态：0-待审核，1-审核通过，2-审核拒绝

    @Column(value = "del_flag",isLogicDelete = true)
    private String delFlag;

    @Column(value = "create_time")
    private Date createTime;

    @Column(value = "update_time")
    private Date updateTime;

    @Column(value = "create_id")
    private Long createId;

    @Column(value = "update_id")
    private Long updateId;
}