package com.sz.applet.miniuser.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.applet.miniuser.mapper.MiniUserMapper;
import com.sz.applet.miniuser.pojo.dto.MiniLoginDTO;
import com.sz.applet.miniuser.pojo.po.MiniLoginUser;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.core.util.JsonUtils;
import com.sz.core.util.Utils;
import com.sz.utils.MapstructUtils;
import com.sz.wechat.mini.MiniWechatService;
import com.sz.wechat.mini.LoginInfoResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sz.applet.miniuser.pojo.po.table.MiniUserTableDef.MINI_USER;

/**
 * <p>
 * 小程序用户表 服务实现类
 * </p>
 *
 * @author sz
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MiniUserServiceImpl extends ServiceImpl<MiniUserMapper, MiniUser> implements MiniUserService {

    private final MiniWechatService miniWechatService;

    @Override
    public MiniUserVO doLogin(MiniLoginDTO dto) {
        String accessToken = miniWechatService.getAccessToken();
        LoginInfoResult loginInfoResult = miniWechatService.miniLogin(dto.getCode(), accessToken);
        log.info(" 小程序登录返回信息：{}", JsonUtils.toJsonString(loginInfoResult));
        // 存储用户信息
        this.savaMiniUser(loginInfoResult);
        MiniUserVO miniUser = new MiniUserVO();
        miniUser.setOpenid(loginInfoResult.getOpenid());
        miniUser.setUnionid(loginInfoResult.getUnionId());
        return miniUser;
    }

    @Override
    public MiniLoginUser getUserByOpenId(String openId, String unionid) {
        QueryWrapper wrapper = QueryWrapper.create().where(MINI_USER.OPENID.eq(openId));
        MiniUser miniUser = getOne(wrapper);
        if (miniUser == null) {
            // 创建新的微信用户信息
            miniUser = new MiniUser();
            miniUser.setOpenid(openId);
            miniUser.setUnionid(unionid);
            save(miniUser);
        }
        
        // 如果绑定了sys_user账户
        if (Utils.isNotNull(miniUser.getSysUserId())) {
            // 返回包含完整登录信息的对象
            return MapstructUtils.convert(miniUser, MiniLoginUser.class);
        } else {
            // 未绑定sys_user账户，但仍需返回MiniLoginUser对象
            MiniLoginUser loginUser = new MiniLoginUser();
            loginUser.setUserId(miniUser.getId());
            loginUser.setOpenid(miniUser.getOpenid());
            loginUser.setNickname(miniUser.getNickname());
            loginUser.setPhone(miniUser.getPhone());
            return loginUser;
        }
    }

    public void savaMiniUser(LoginInfoResult loginInfoResult) {
        //检测是否已存在
        if(!this.exists(new QueryWrapper()
                .eq(MiniUser::getOpenid,loginInfoResult.getOpenid(), ObjectUtil.isNotNull(loginInfoResult.getOpenid()))
                .eq(MiniUser::getUnionid,loginInfoResult.getUnionId(), ObjectUtil.isNotNull(loginInfoResult.getUnionId())))){
            MiniUser miniUser = new MiniUser();
            miniUser.setOpenid(loginInfoResult.getOpenid());
            miniUser.setUnionid(loginInfoResult.getUnionId());
            this.save(miniUser);
        }
    }


    @Override
    public boolean isBoundToSchoolUser(Long miniUserId) {
        return false;
    }

    @Override
    public Object getBoundSchoolUser(Long miniUserId) {
        return null;
    }

    @Override
    public Boolean checkAuthStatus(String openId) {
        if(this.exists(new QueryWrapper().eq(MiniUser::getOpenid,openId).eq(MiniUser::getAuthStatus,1))){
            return true;
        }
        return false;
    }

}