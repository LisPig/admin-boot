package com.sz.applet.miniBusiness.pojo.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: tianzhiyuan
 * @Date: 2025-11-01
 * @Description: 清除提醒BO
 */
@Data
@Schema(description = "清除提醒BO")
public class ClearNoticeBO {

    @Schema(description = "动态ID")
    private Long memoId;

    @Schema(description = "提醒类型 1-点赞 2-评论")
    private Integer type;
}