package com.sz.applet.miniBusiness.pojo.po;

import lombok.Data;
import lombok.experimental.Accessors;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

/**
 * 校友通行证申请表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Accessors(chain = true)
@Data
@Table(value = "applet_alumni_pass_application")
public class AppletAlumniPassApplication {

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户ID（关联用户表的外键）
     */
    @Column(value = "user_id")
    private Long userId;

    /**
     * 申请人姓名
     */
    @Column(value = "name")
    private String name;

    /**
     * 电话号码
     */
    @Column(value = "phone")
    private String phone;

    /**
     * 毕业年份
     */
    @Column(value = "year")
    private Integer year;

    /**
     * 班级编号
     */
    @Column(value = "class_no")
    private String classNo;

    /**
     * 返校原因
     */
    @Column(value = "reason")
    private String reason;

    /**
     * 其他原因详情
     */
    @Column(value = "other_reason")
    private String otherReason;

    /**
     * 预计返校时间
     */
    @Column(value = "expected_time")
    private Date expectedTime;

    /**
     * 申请时间
     */
    @Column(value = "application_time")
    private Date applicationTime;

    /**
     * 申请状态（0-待审核，1-已批准，2-已拒绝）
     */
    @Column(value = "status")
    private Integer status;

    /**
     * 使用状态（0-已归还，1-待归还，2-未归还，3-待处理）
     */
    @Column(value = "use_status")
    private Integer useStatus;

    /**
     * 审批人ID
     */
    @Column(value = "approver_id")
    private Long approverId;

    /**
     * 审批时间
     */
    @Column(value = "approve_time")
    private Date approveTime;

    /**
     * 审批备注
     */
    @Column(value = "approve_remark")
    private String approveRemark;

    /**
     * 二维码标识
     */
    @Column(value = "qr_code")
    private String qrCode;


}
