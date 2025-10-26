-- 捐款项目
DROP TABLE IF EXISTS `donation_project`;
CREATE TABLE `donation_project`
(
    `id`          bigint         NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        varchar(50)    NOT NULL COMMENT '名称',
    `picture`     varchar(500) DEFAULT NULL COMMENT '图片',
    `description` varchar(500) DEFAULT NULL COMMENT '描述',
    `amount`      decimal(10, 2) NOT NULL COMMENT '金额',
    `status`      varchar(10)    NOT NULL COMMENT '状态（1-待审核，2-审核通过，3-审核未通过）',
    `del_flag`    enum('T','F') DEFAULT 'F' COMMENT '删除标识',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`   bigint       DEFAULT NULL COMMENT '创建人ID',
    `update_id`   bigint       DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='捐款项目';

-- 捐款记录
DROP TABLE IF EXISTS `donation_record`;
CREATE TABLE `donation_record`
(
    `id`          bigint         NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`     bigint         NOT NULL COMMENT '用户ID',
    `project_id`  bigint         NOT NULL COMMENT '项目ID',
    `amount`      decimal(10, 2) NOT NULL COMMENT '金额',
    `status`      varchar(10)    NOT NULL COMMENT '状态（1-待处理，2-处理中，3-处理完成）',
    `del_flag`    enum('T','F') DEFAULT 'F' COMMENT '删除标识',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`   bigint   DEFAULT NULL COMMENT '创建人ID',

    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='捐款记录';

