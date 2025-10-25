package com.sz.applet.miniBusiness.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * AppletAlumniAssociation返回vo
 * </p>
 *
 * @author LisPig
 * @since 2025-10-25
 */
@Data
@Accessors(chain = true)
@Schema(description = "AppletAlumniAssociation返回vo")
public class AppletAlumniAssociationVO {

    @Schema(description =  "ID")
    private Long id;

    @Schema(description =  "名称")
    private String name;

    @Schema(description =  "头像")
    private String avatar;

    @Schema(description =  "描述")
    private String description;

    @Schema(description =  "联系人")
    private String contract;

    @Schema(description =  "联系电话")
    private String phone;

    @Schema(description =  "状态（1-正常，2-禁用）")
    private String status;

    @Schema(description =  "人数")
    private Long number;

    @Schema(description =  "活动数")
    private Long activityNumber;

    @Schema(description =  "创建时间")
    private LocalDateTime createTime;

    @Schema(description =  "更新时间")
    private LocalDateTime updateTime;

    @Schema(description =  "创建人ID")
    private Long createId;

    @Schema(description =  "更新人ID")
    private Long updateId;


    List<AppletAlumniAssociationActivityVO> activityList;
}