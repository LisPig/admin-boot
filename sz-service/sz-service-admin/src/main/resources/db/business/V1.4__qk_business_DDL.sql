
-- apply_auth添加部分字段
ALTER TABLE `apply_auth`
    ADD COLUMN `honors` VARCHAR(255) NULL COMMENT '获得荣誉' AFTER `work_time`,
    ADD COLUMN `memory` VARCHAR(255) NULL COMMENT '我的钱高回忆' AFTER `honors`,
    ADD COLUMN `my_suggestion` VARCHAR(255) NULL COMMENT '我的建议' AFTER `memory`;
