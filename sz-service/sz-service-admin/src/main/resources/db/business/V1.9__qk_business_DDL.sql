
-- mini_user添加是否展示字段 默认为0
ALTER TABLE `mini_user`
    ADD COLUMN `is_show` tinyint DEFAULT 0 COMMENT '是否显示(0否1是)'  AFTER `auth_status`;
