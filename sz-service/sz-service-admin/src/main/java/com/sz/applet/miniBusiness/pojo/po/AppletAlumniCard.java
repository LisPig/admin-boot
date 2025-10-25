package com.sz.applet.miniBusiness.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.sz.platform.listener.TableSysUserListener;
import lombok.Data;

import java.lang.Long;
import java.util.Date;
import java.lang.Object;
import java.lang.String;

/**
 * 校友卡表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "applet_alumni_card", onInsert = TableSysUserListener.class, onUpdate = TableSysUserListener.class)
public class AppletAlumniCard {

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
     * 姓名
     */
    @Column(value = "name")
    private String name;

    /**
     * 手机号
     */
    @Column(value = "phone")
    private String phone;

    /**
     * 返校理由
     */
    @Column(value = "reason")
    private String reason;

    /**
     * 预计返校时间
     */
    @Column(value = "return_time")
    private Date returnTime;

    /**
     * 状态（1-待审核，2-已通过，3-未通过）
     */
    @Column(value = "status")
    private String status;

    /**
     * 审核时间
     */
    @Column(value = "approve_time")
    private Date approveTime;

    /**
     * 审核备注
     */
    @Column(value = "approve_remark")
    private String approveRemark;

    /**
     * 二维码地址
     */
    @Column(value = "qr_code")
    private String qrCode;

    /**
     * 删除标识
     */
    @Column(value = "del_flag",isLogicDelete = true)
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

    /**
     * 审核人ID
     */
    @Column(value = "approve_id")
    private Long approveId;

}
