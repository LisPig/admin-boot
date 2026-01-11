package com.sz.applet.miniBusiness.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 接收Long类型值的DTO
 * </p>
 *
 * @author sz
 * @since 2026-01-07
 */
@Data
@Schema(description = "Long类型值传输对象")
public class LongValueDTO {

    @Schema(description = "ID值")
    private Long value;
}