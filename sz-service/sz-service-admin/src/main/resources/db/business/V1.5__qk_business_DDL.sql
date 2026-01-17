
-- apply_auth添加部分字段
ALTER TABLE `mini_user`
    ADD COLUMN `honor` VARCHAR(255) NULL COMMENT '获得荣誉' AFTER `sex`,
    ADD COLUMN `memory` VARCHAR(255) NULL COMMENT '我的钱高回忆' AFTER `honor`,
    ADD COLUMN `my_suggestion` VARCHAR(255) NULL COMMENT '我的建议' AFTER `memory`;
