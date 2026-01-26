
-- mini_user添加是否展示字段 默认为0
ALTER TABLE `mini_user`
    ADD COLUMN `profile_prompt_shown` TINYINT(1) DEFAULT 0 COMMENT '是否提示(0=未提示，1=已提示)'  AFTER `auth_status`;
