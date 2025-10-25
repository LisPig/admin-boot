package com.sz.applet.miniBusiness.pojo.po;

import com.mybatisflex.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.io.Serial;
import com.sz.mysql.EntityChangeListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
* <p>
* 档案申请记录表
* </p>
*
* @author LisPig
* @since 2025-10-25
*/
@Data
@Accessors(chain = true)
@Table(value = "archive_files", onInsert = EntityChangeListener.class, onUpdate = EntityChangeListener.class)
@Schema(description = "档案申请记录表")
public class ArchiveFiles implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @Schema(description ="主键ID")
    private Long id;

    @Schema(description ="用户ID")
    private Integer userId;

    @Schema(description ="查阅日期")
    private LocalDate time;

    @Schema(description ="联系方式")
    private String phone;

    @Schema(description ="姓名")
    private String name;

    @Schema(description ="身份证号")
    private String card;

    @Schema(description ="工作单位")
    private String company;

    @Schema(description ="查档内容和用途")
    private String mark;

    @Schema(description ="查档形式")
    private String idea;

    @Schema(description ="来访人数")
    private Integer userNum;

    @Schema(description ="身份证及相关材料图片URL(逗号分隔)")
    private String cardImg;

    @Schema(description ="届次")
    private Integer year;

    @Schema(description ="班级号")
    private Integer classNo;

    @Schema(description ="审核状态(0:审核中,1:通过,2:不通过)")
    private Integer pass;

    @Schema(description ="审核不通过原因")
    private String passMark;

    @Schema(description ="状态(用于校友通行证)")
    private Integer state;

    @Schema(description ="创建时间")
    private LocalDateTime createTime;

}