package com.sz.admin.system.pojo.dto.sysuser;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "系统用户绑定wx_user_ID")
@Data
public class SysUserBindWxUserDTO {

    private Long sysUserId;

    private Long wxUserId;
}
