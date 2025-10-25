package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import com.sz.core.common.entity.PageQuery;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * <p>
 * AppletAlumniAssociation查询DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-25
 */
@Data
@Accessors(chain = true)
@Schema(description = "AppletAlumniAssociation查询DTO")
public class AppletAlumniAssociationListDTO extends PageQuery {

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

}