package com.sz.applet.miniBusiness.pojo.vo;

import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import lombok.Data;

@Data
public class ApplyAutoDetailVo {
    private MiniUserVO miniUser;
    private ApplyAuthVo applyAuth;
}
