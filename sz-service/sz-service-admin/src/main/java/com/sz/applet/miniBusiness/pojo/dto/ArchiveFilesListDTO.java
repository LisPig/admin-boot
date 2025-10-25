package com.sz.applet.miniBusiness.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import com.sz.core.common.entity.PageQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * <p>
 * ArchiveFiles查询DTO
 * </p>
 *
 * @author LisPig
 * @since 2025-10-25
 */
@Data
@Accessors(chain = true)
@Schema(description = "ArchiveFiles查询DTO")
public class ArchiveFilesListDTO extends PageQuery {

    @Schema(description =  "用户ID")
    private Integer userId;

    @Schema(description =  "查阅日期开始")
    private LocalDate timeStart;

    @Schema(description =  "查阅日期结束")
    private LocalDate timeEnd;

    @Schema(description =  "联系方式")
    private String phone;

    @Schema(description =  "姓名")
    private String name;

    @Schema(description =  "身份证号")
    private String card;

    @Schema(description =  "工作单位")
    private String company;

    @Schema(description =  "查档内容和用途")
    private String mark;

    @Schema(description =  "查档形式")
    private String idea;

    @Schema(description =  "来访人数")
    private Integer userNum;

    @Schema(description =  "身份证及相关材料图片URL(逗号分隔)")
    private String cardImg;

    @Schema(description =  "届次")
    private Integer year;

    @Schema(description =  "班级号")
    private Integer classNo;

    @Schema(description =  "审核状态(0:审核中,1:通过,2:不通过)")
    private Integer pass;

    @Schema(description =  "审核不通过原因")
    private String passMark;

    @Schema(description =  "状态(用于校友通行证)")
    private Integer state;

}