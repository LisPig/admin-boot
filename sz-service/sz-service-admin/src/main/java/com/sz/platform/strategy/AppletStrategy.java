package com.sz.platform.strategy;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.po.ApplyAuth;
import com.sz.applet.miniBusiness.pojo.po.SchoolUserBinding;
import com.sz.applet.miniBusiness.pojo.vo.ApplyAuthVo;
import com.sz.applet.miniBusiness.service.ApplyAuthService;
import com.sz.applet.miniBusiness.service.SchoolUserBindingService;
import com.sz.applet.miniuser.pojo.po.MiniLoginUser;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.core.common.entity.MiniLoginUserDTO;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.JsonUtils;
import com.sz.core.util.Utils;
import com.sz.security.core.util.LoginUtils;
import com.sz.security.pojo.ClientVO;
import com.sz.security.pojo.LoginInfo;
import com.sz.security.pojo.LoginVO;
import com.sz.security.service.IAuthStrategy;
import com.sz.wechat.mini.MiniWechatService;
import com.sz.wechat.mini.LoginInfoResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 小程序认证策略
 *
 * AppletStrategy
 * 
 * @author sz
 * @since 2024/4/26 16:08
 * @version 1.0
 */

@Slf4j
@Service("applet" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class AppletStrategy implements IAuthStrategy {

    private final MiniWechatService miniWechatService;

    private final MiniUserService miniUserService;

    private final ApplyAuthService applyAuthService;

    private final SchoolUserBindingService schoolUserBindingService;

    @Override
    public LoginVO login(LoginInfo info, ClientVO client) {
        String clientId = client.getClientId();
        String code = info.getCode();
        log.info("小程序登录code：{}", code);
        CommonResponseEnum.INVALID.message("无效的小程序code").assertFalse(Utils.isNotNull(code));

        String accessToken = miniWechatService.getAccessToken();
        LoginInfoResult result = miniWechatService.miniLogin(code, accessToken);
        log.info(" 小程序登录返回信息：{}", JsonUtils.toJsonString(result));
        String openid = result.getOpenid();
        String unionid = result.getUnionId();
        String sessionKey = result.getSessionKey(); // 小程序登录凭证

        MiniLoginUser miniLoginUser = miniUserService.getUserByOpenId(openid, unionid);
        MiniLoginUserDTO miniLoginUserDTO = new MiniLoginUserDTO();
        BeanCopyUtils.copy(miniLoginUser, miniLoginUserDTO);

        // 设置登录模型
        SaLoginModel model = createLoginModel(client);
        Long userId = miniLoginUser.getUserId();
        // 设置jwt额外数据
        Map<String, Object> extraData = createExtraData(clientId, userId);
        // 执行登录
        LoginUtils.performMiniLogin(userId, miniLoginUserDTO, model, extraData);
        //log.info("小程序登录成功，用户ID：{}", Objects.requireNonNull(LoginUtils.getLoginUser()).getUserInfo());
        // 构造返回对象
        return createLoginVO(miniLoginUser);
    }

    private SaLoginModel createLoginModel(ClientVO client) {
        SaLoginModel model = new SaLoginModel();
        model.setDevice(client.getDeviceTypeCd());
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        return model;
    }

    private Map<String, Object> createExtraData(String clientId, Long userId) {
        Map<String, Object> extraData = new HashMap<>();
        extraData.put("clientId", clientId);
        extraData.put("userId", userId);
        return extraData;
    }

    private LoginVO createLoginVO(MiniLoginUser miniLoginUser) {
        LoginVO loginVo = new LoginVO();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setUserInfo(miniLoginUser);
        loginVo.setApplyAuthInfo(createApplyAuth(miniLoginUser));
        return loginVo;
    }


    private ApplyAuthVo createApplyAuth(MiniLoginUser miniLoginUser) {
        SchoolUserBinding schoolUserBinding = schoolUserBindingService.getOne(new QueryWrapper()
                .eq(SchoolUserBinding::getMiniUserId, miniLoginUser.getUserId()));
        if(ObjectUtil.isNotNull(schoolUserBinding)) {
            ApplyAuth applyAuth = applyAuthService.getOne(new QueryWrapper()
                    .eq(ApplyAuth::getUserId, schoolUserBinding.getSchoolUserId()));
            return BeanCopyUtils.copy(applyAuth, ApplyAuthVo.class);
        }
        return null;
    }

}
