package com.sz.applet.miniBusiness.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: tianzhiyuan
 * @Date: 2025-11-01
 * @Description: 未读提醒VO
 */
@Data
@Schema(description = "未读提醒VO")
public class UnreadNoticeVO {

    @Schema(description = "未读点赞数")
    private Long unreadLikes = 0L;

    @Schema(description = "未读评论数")
    private Long unreadComments = 0L;
}