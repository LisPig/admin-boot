package com.sz.applet.miniBusiness.pojo.vo;

import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationActivity;
import com.sz.applet.miniBusiness.translation.AppletAlumniAssociationActivityUserTranslator;
import com.sz.core.common.translate.Translate;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * AppletAlumniAssociationActivity返回vo
 * </p>
 *
 * @author sz-admin
 * @since 2025-10-25
 */
@Data
@Accessors(chain = true)
@AutoMapper(target = AppletAlumniAssociationActivity.class)
@Schema(description = "AppletAlumniAssociationActivity返回vo")
public class AppletAlumniAssociationActivityVO {

    @Schema(description =  "ID")
    private Long id;

    @Schema(description =  "校友会ID")
    private Long alumniAssociationId;

    @Schema(description =  "标题")
    private String title;

    @Schema(description =  "头图")
    private String avatar;

    @Schema(description =  "内容")
    private String content;

    @Schema(description =  "时间")
    private String time;

    @Schema(description =  "地点")
    private String location;

    @Schema(description =  "状态（1-正常，2-禁用）")
    private String status;

    @Schema(description =  "是否已报名")
    //@Translate(translator = AppletAlumniAssociationActivityUserTranslator.class,sourceField = "id")
    private Boolean isJoin;

    @Schema(description =  "报名人数")
    @Translate(translator = AppletAlumniAssociationActivityUserTranslator.class,sourceField = "alumniAssociationId")
    private Long joinNum;

    @Schema(description =  "创建时间")
    private LocalDateTime createTime;

    @Schema(description =  "更新时间")
    private LocalDateTime updateTime;

    @Schema(description =  "创建人ID")
    private Long createId;

    @Schema(description =  "更新人ID")
    private Long updateId;

}