--liquibase formatted sql

--changeset sz:20260815_001
--comment: sys_file 增加微信图片内容安全校验字段
ALTER TABLE `sys_file`
    ADD COLUMN `check_trace_id` varchar(64) NULL COMMENT 'media_check_async返回的trace_id' AFTER `e_tag`,
    ADD COLUMN `check_status` varchar(16) NULL COMMENT '校验状态PENDING/PASS/REVIEW/RISKY/ERROR' AFTER `check_trace_id`,
    ADD COLUMN `check_label` int NULL COMMENT '违规标签label' AFTER `check_status`,
    ADD COLUMN `check_time` datetime NULL COMMENT '校验结果回传时间' AFTER `check_label`;
ALTER TABLE `sys_file` ADD INDEX `idx_check_trace_id` (`check_trace_id`);
