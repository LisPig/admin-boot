package com.sz.applet.miniBusiness.pojo.po;

import com.mybatisflex.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.io.Serial;
import com.sz.mysql.EntityChangeListener;
import java.time.LocalDateTime;

/**
* <p>
* 广播表
* </p>
*
* @author LisPig
* @since 2025-10-30
*/
@Data
@Accessors(chain = true)
@Table(value = "broadcast", onInsert = EntityChangeListener.class, onUpdate = EntityChangeListener.class)
@Schema(description = "广播表")
public class Broadcast implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @Schema(description ="ID")
    private Long id;

    @Schema(description ="标题")
    private String title;

    @Schema(description ="内容")
    private String content;

    @Schema(description ="状态（1-正常，2-禁用）")
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