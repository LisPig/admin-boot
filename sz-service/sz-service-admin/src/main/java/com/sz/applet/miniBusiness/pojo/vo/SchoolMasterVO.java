package com.sz.applet.miniBusiness.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sz.applet.miniBusiness.pojo.dto.SchoolMasterCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import com.sz.excel.annotation.DictFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * SchoolMaster返回vo
 * </p>
 *
 * @author LisPig
 * @since 2025-10-23
 */
@Data
@Accessors(chain = true)
@Schema(description = "SchoolMaster返回vo")
public class SchoolMasterVO {

    @ExcelIgnore
    @Schema(description =  "ID")
    private Long id;

    @ExcelProperty(value = "姓名")
    @Schema(description =  "姓名")
    private String name;

    @ExcelProperty(value = "画像")
    @Schema(description =  "画像")
    private String avatar;

    @Schema(description =  "开始时间")
    private String startTime;
    @Schema(description =  "结束时间")
    private String endTime;
    @Schema(description =  "职务")
    private String position;
    @Schema(description =  "描述")
    private String description;


    @ExcelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description =  "创建时间")
    private LocalDateTime createTime;

    @ExcelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description =  "更新时间")
    private LocalDateTime updateTime;

    @ExcelProperty(value = "创建人ID")
    @DictFormat(dictType = "dynamic_user_options")
    @Schema(description =  "创建人ID")
    private Long createId;

    @ExcelProperty(value = "更新人ID")
    @DictFormat(dictType = "dynamic_user_options")
    @Schema(description =  "更新人ID")
    private Long updateId;

}