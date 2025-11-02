package com.sz.applet.miniBusiness.pojo.bo;

import com.sz.core.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: tianzhiyuan
 * @Date: 2025-11-01
 * @Description: 用户关注列表查询BO
 */
@Data
@Schema(description = "用户关注列表查询BO")
public class UserFollowListBO extends PageQuery {

    @Schema(description = "用户ID")
    private Long userId;
}