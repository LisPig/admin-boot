package com.sz.applet.miniBusiness.pojo.bo;

import com.sz.core.common.entity.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "评论列表")
public class CommentListBO extends PageQuery {

    @Schema(description = "动态ID")
    private String memoId;
}
