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
import java.lang.Integer;

/**
 * 校友申请认证表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Accessors(chain = true)
@Data
@Table(value = "apply_auth")
public class ApplyAuth {

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
     * 身份（1-校友，2-教师）
     */
    @Column(value = "identity")
    private Integer identity;

    /**
     * 身份证号
     */
    @Column(value = "id_card")
    private String idCard;

    /**
     * 学号
     */
    @Column(value = "student_id")
    private String studentId;

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
     * 教师编号
     */
    @Column(value = "teacher_id")
    private String teacherId;

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

    /**
     * 审核人ID
     */
    @Column(value = "approve_id")
    private Long approveId;


}
