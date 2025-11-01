package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LikesDto {
    @Schema(description = "用户ID")
    private Long id;

    private String username;

}
