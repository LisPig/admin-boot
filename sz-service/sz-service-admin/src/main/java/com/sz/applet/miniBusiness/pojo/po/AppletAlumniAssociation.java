package com.sz.applet.miniBusiness.pojo.po;

import com.mybatisflex.annotation.*;
import com.sz.mysql.WeChatEntityChangeListener;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.io.Serial;
import com.sz.mysql.EntityChangeListener;
import java.time.LocalDateTime;

/**
* <p>
* 校友会表
* </p>
*
* @author LisPig
* @since 2025-10-25
*/
@Data
@Accessors(chain = true)
@Table(value = "applet_alumni_association", onInsert = WeChatEntityChangeListener.class, onUpdate = EntityChangeListener.class)
@Schema(description = "校友会表")
public class AppletAlumniAssociation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    @Schema(description ="ID")
    private Long id;

    @Schema(description ="名称")
    private String name;

    @Schema(description ="头像")
    private String avatar;

    @Schema(description ="描述")
    private String description;

    @Schema(description ="联系人")
    private String contract;

    @Schema(description ="联系电话")
    private String phone;

    @Schema(description ="状态（1-正常，2-禁用）")
    private String status;

    @Column(isLogicDelete = true)
    @Schema(description ="")
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