package com.sz.applet.miniBusiness.service;


import com.sz.applet.miniBusiness.pojo.bo.ApplyAuthBo;
import com.sz.applet.miniBusiness.pojo.bo.ApplyAuthListBo;
import com.sz.applet.miniBusiness.pojo.po.ApplyAuth;
import com.mybatisflex.core.service.IService;
import com.sz.applet.miniBusiness.pojo.vo.ApplyAuthVo;
import com.sz.applet.miniBusiness.pojo.vo.ApplyAutoDetailVo;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.core.common.entity.ApiResult;
import com.sz.core.common.entity.PageResult;

import java.util.List;

/**
 * 校友申请认证表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface ApplyAuthService extends IService<ApplyAuth> {

    String applyAuth(ApplyAuthBo bo);

    PageResult<ApplyAuthVo> page(ApplyAuthListBo bo);

    /**
     * 校友认证审核
     * @param bo
     * @return
     */
    Boolean review(ApplyAuthBo bo);

    ApplyAutoDetailVo detail(Long id);

    /**
     * 检查用户是否通过认证
     * @return
     */
    Object checkIsPassAuth();

    MiniUserVO getUserInfo(String openId, String o);

    /**
     * 批量审核认证申请
     * @param ids 申请ID列表
     * @param status 审核状态（2-通过，3-拒绝）
     * @param remark 审核备注
     */
    void batchReview(List<Long> ids, String status, String remark);
}