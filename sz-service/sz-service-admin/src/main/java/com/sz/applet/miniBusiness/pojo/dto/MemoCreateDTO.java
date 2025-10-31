package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 发布动态DTO
 * </p>
 *
 * @author your-name
 * @since 2025-10-28
 */
@Data
@Schema(description = "发布动态DTO")
public class MemoCreateDTO {

    @Schema(description = "动态内容")
    private String content;

    @Schema(description = "图片链接，逗号分隔")
    private String imgs;

    @Schema(description = "话题标签id")
    private Long tagId;

    @Schema(description = "话题标签")
    private String tagName;


    @Schema(description = "位置")
    private String position;

}