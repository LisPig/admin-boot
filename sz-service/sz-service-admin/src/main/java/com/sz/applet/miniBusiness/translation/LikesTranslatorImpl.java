package com.sz.applet.miniBusiness.translation;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.dto.LikesDto;
import com.sz.applet.miniBusiness.pojo.po.AppletSquareLikes;
import com.sz.applet.miniBusiness.service.AppletSquareLikesService;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.core.common.translate.Translator;
import com.sz.core.util.BeanCopyUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LikesTranslatorImpl implements Translator<Long, List<LikesDto>> {
    private final AppletSquareLikesService appletSquareLikesService;
    private final MiniUserService miniUserService;
    @Override
    public List<LikesDto> translate(Long sourceValue) {
        // 获取点赞用户列表
        List<AppletSquareLikes> appletSquareLikes = appletSquareLikesService.list(new QueryWrapper().eq(AppletSquareLikes::getMemoId, sourceValue));
        // 提取appletSquareLikes里的userId为数组
        if(ObjectUtil.isNotEmpty(appletSquareLikes)) {
            List<Long> userIds = appletSquareLikes.stream().map(AppletSquareLikes::getUserId).toList();
            List<MiniUser> miniUsers = miniUserService.list(new QueryWrapper()
                    .select(MiniUser::getId, MiniUser::getUsername, MiniUser::getAvatarUrl)
                    .in(MiniUser::getId, userIds, ObjectUtil.isNotEmpty(userIds)));

            return BeanCopyUtils.copyList(miniUsers, LikesDto.class,true);
        }
        return null;
    }
}
