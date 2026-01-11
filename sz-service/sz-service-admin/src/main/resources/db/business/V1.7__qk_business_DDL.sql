
-- sys_user添加部分字段
ALTER TABLE `sys_user`
    ADD COLUMN `mini_user_id` bigint NULL COMMENT '小程序用户ID' AFTER `id`;
