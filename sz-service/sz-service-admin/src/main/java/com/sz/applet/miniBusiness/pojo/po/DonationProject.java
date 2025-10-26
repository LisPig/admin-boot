package com.sz.applet.miniBusiness.pojo.po;

import com.mybatisflex.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.io.Serial;
import com.sz.mysql.EntityChangeListener;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* <p>
* 捐款项目
* </p>
*
* @author LisPig
* @since 2025-10-26
*/
@Data
@Accessors(chain = true)
@Table(value = "donation_project", onInsert = EntityChangeListener.class, onUpdate = EntityChangeListener.class)
@Schema(description = "捐款项目")
public class DonationProject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @Schema(description ="ID")
    private Long id;

    @Schema(description ="名称")
    private String name;

    @Schema(description ="图片")
    private String picture;

    @Schema(description ="描述")
    private String description;

    @Schema(description ="金额")
    private BigDecimal amount;

    @Schema(description ="状态（1-待审核，2-审核通过，3-审核未通过）")
    private String status;

    @Column(isLogicDelete = true)
    @Schema(description ="删除标识")
    private String delFlag;

    @Schema(description ="创建时间")
    private LocalDateTime createTime;

    @Schema(description ="更新时间")
    private LocalDateTime updateTime;

    @Schema(description ="创建人ID")
    private Long createId;

    @Schema(description ="更新人ID")
    private Long updateId;

}