
-- sys_user添加部分字段
ALTER TABLE `applet_alumni_association_activity`
    ADD COLUMN `start_time` datetime NULL COMMENT '开始时间' AFTER `status`,
    ADD COLUMN `end_time` datetime NULL COMMENT '结束时间' AFTER `start_time`;
