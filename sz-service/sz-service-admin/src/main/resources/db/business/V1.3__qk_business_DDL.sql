
-- apply_auth添加部分字段 graduate_info work_unit job_info work_time
ALTER TABLE `apply_auth`
    ADD COLUMN `graduate_info` VARCHAR(255) NULL COMMENT '毕业信息' AFTER `qr_code`,
    ADD COLUMN `work_unit` VARCHAR(255) NULL COMMENT '工作单位' AFTER `graduate_info`,
    ADD COLUMN `job_info` VARCHAR(255) NULL COMMENT '职位信息' AFTER `work_unit`,
    ADD COLUMN `work_time` VARCHAR(255) NULL COMMENT '工作时间' AFTER `job_info`;

-- 校友会会员表添加身份字段 分为会长和会员
ALTER TABLE `applet_alumni_association_user`
    ADD COLUMN `identity` VARCHAR(255) NULL COMMENT '身份' AFTER `status`;