package com.sz.applet.miniBusiness.pojo.bo;

import com.sz.core.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 动态列表BO
 * </p>
 *
 * @author your-name
 * @since 2025-10-28
 */
@Data
@Schema(description = "动态列表BO")
public class MemoListBO extends PageQuery {

    @Schema(description = "话题标签")
    private String tagName;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "我的关注")
    private Boolean myFocus;

}