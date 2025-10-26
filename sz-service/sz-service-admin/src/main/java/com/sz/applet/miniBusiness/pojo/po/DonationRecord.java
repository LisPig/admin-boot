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
* 捐款记录
* </p>
*
* @author LisPig
* @since 2025-10-26
*/
@Data
@Accessors(chain = true)
@Table(value = "donation_record", onInsert = EntityChangeListener.class, onUpdate = EntityChangeListener.class)
@Schema(description = "捐款记录")
public class DonationRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @Schema(description ="ID")
    private Long id;

    @Schema(description ="用户ID")
    private Long userId;

    @Schema(description ="项目ID")
    private Long projectId;

    @Schema(description ="金额")
    private BigDecimal amount;

    @Schema(description ="状态（1-待处理，2-处理中，3-处理完成）")
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

}