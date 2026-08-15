package com.sz.applet.miniBusiness.translation;

import com.mybatisflex.core.query.QueryWrapper;
import com.sz.applet.miniBusiness.pojo.po.AppletAlumniAssociationUser;
import com.sz.applet.miniBusiness.service.AppletAlumniAssociationUserService;
import com.sz.applet.miniuser.pojo.po.MiniUser;
import com.sz.applet.miniuser.pojo.vo.MiniUserVO;
import com.sz.applet.miniuser.service.MiniUserService;
import com.sz.core.common.translate.Translator;
import com.sz.admin.system.service.MediaCheckService;
import com.sz.utils.MapstructUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 校友会其下用户翻译
 */
@Service
@AllArgsConstructor
public class AppletAlumniAssociationUserTranslator implements Translator<Long, List<MiniUserVO>> {
    private final AppletAlumniAssociationUserService appletAlumniAssociationUserService;
    private final MiniUserService  miniUserService;
    private final MediaCheckService mediaCheckService;
    @Override
    public List<MiniUserVO> translate(Long sourceValue) {
        List<AppletAlumniAssociationUser> appletAlumniAssociationUsers = appletAlumniAssociationUserService.list(new QueryWrapper()
                .eq(AppletAlumniAssociationUser::getAlumniAssociationId, sourceValue)
                .eq(AppletAlumniAssociationUser::getStatus, 1)
                .orderBy(AppletAlumniAssociationUser::getIdentity, false) // 管理员身份优先（假设管理员值更大）
                .orderBy(AppletAlumniAssociationUser::getCreateTime, true));

        if (appletAlumniAssociationUsers.isEmpty()) {
            return null;
        }

        List<MiniUserVO> miniUsers = miniUserService.listAs(new QueryWrapper()
                .in(MiniUser::getId, appletAlumniAssociationUsers.stream().map(AppletAlumniAssociationUser::getUserId).toList())
                .orderBy(MiniUser::getCreateTime, false), MiniUserVO.class);

        // 设置身份并保持正确的排序
        miniUsers.forEach(miniUserVO -> {
            AppletAlumniAssociationUser associationUser = appletAlumniAssociationUsers.stream()
                    .filter(appletAlumniAssociationUser ->
                            appletAlumniAssociationUser.getUserId().equals(miniUserVO.getId()))
                    .findFirst()
                    .orElse(null);

            if (associationUser != null) {
                miniUserVO.setIdentity(associationUser.getIdentity());
            }
            // 违规头像替换为空串(前端回退占位图)
            miniUserVO.setAvatarUrl(mediaCheckService.resolveAvatarUrl(miniUserVO.getAvatarUrl()));
        });

        // 最后再按身份和创建时间排序，确保管理员在前
        // 按身份排序（管理员优先），再按创建时间排序
        miniUsers.sort((a, b) -> {
            // 假设 "admin" 或 "administrator" 代表管理员身份，具体值需根据实际业务确定
            String adminRole = "2"; // 或者其他表示管理员的字符串值

            boolean isAAdmin = isAdminRole(a.getIdentity(), adminRole);
            boolean isBAdmin = isAdminRole(b.getIdentity(), adminRole);

            if (isAAdmin && !isBAdmin) {
                return -1; // A是管理员，排在前面
            } else if (!isAAdmin && isBAdmin) {
                return 1; // B是管理员，排在前面
            } else if (isAAdmin && isBAdmin) {
                // 如果都是管理员，按创建时间正序（先加入的排前面）
                return a.getCreateTime().compareTo(b.getCreateTime()); // 时间正序
            } else {
                // 如果都不是管理员，按创建时间正序（先加入的排前面）
                return a.getCreateTime().compareTo(b.getCreateTime()); // 时间正序
            }
        });

        return miniUsers;
    }

    // 辅助方法：判断是否为管理员角色
    private boolean isAdminRole(String identity, String adminRole) {
        if (identity == null) {
            return false;
        }
        // 可以扩展支持多种管理员角色标识
        return identity.equalsIgnoreCase(adminRole) ||
                identity.equalsIgnoreCase("2") ; // 根据实际业务需要调整
    }
}
