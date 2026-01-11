package com.sz.applet.miniBusiness.pojo.vo;

import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationUser;
import com.sz.applet.miniBusiness.translation.AppletAlumniAssociationActivityTranslator;
import com.sz.applet.miniBusiness.translation.AppletAlumniAssociationUserTranslator;
import com.sz.applet.miniBusiness.translation.AssociationActivityCountTranslator;
import com.sz.applet.miniBusiness.translation.AssociationMemberTotalTranslator;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.core.common.translate.Translate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.util.List;

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
    @Translate(translator = AssociationMemberTotalTranslator.class,sourceField = "id")
    private Long number;

    @Schema(description =  "活动数")
    @Translate(translator = AssociationActivityCountTranslator.class,sourceField = "id")
    private Long activityNumber;

    @Schema(description =  "当前用户是否是入会会员")
    //@Translate(translator = AssociationMemberTotalTranslator.class,sourceField = "id")
    private Boolean isMember;

    @Schema(description =  "创建时间")
    private LocalDateTime createTime;

    @Schema(description =  "更新时间")
    private LocalDateTime updateTime;

    @Schema(description =  "创建人ID")
    private Long createId;

    @Schema(description =  "更新人ID")
    private Long updateId;

    @Schema(description =  "活动列表")
    @Translate(translator = AppletAlumniAssociationActivityTranslator.class,sourceField = "id")
    List<AppletAlumniAssociationActivityVO> activityList;

    @Schema(description =  "成员列表")
    @Translate(translator = AppletAlumniAssociationUserTranslator.class,sourceField = "id")
    List<MiniUserVO> memberList;
}