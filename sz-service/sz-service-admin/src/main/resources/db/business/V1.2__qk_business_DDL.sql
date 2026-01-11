-- 创建校友会活动参与关联表
CREATE TABLE `applet_alumni_association_activity_user`
(
    `id`                             bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `alumni_association_id`          bigint NOT NULL COMMENT '校友会ID',
    `alumni_association_activity_id` bigint NOT NULL COMMENT '校友会活动ID',
    `user_id`                        bigint NOT NULL COMMENT '用户ID',
    `status`                         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校友会活动参与关联表';