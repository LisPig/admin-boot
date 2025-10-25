-- 校友卡
CREATE TABLE `applet_alumni_card` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `reason` varchar(100) NOT NULL COMMENT '返校理由',
  `return_time` datetime NOT NULL COMMENT '预计返校时间',
  `status` varchar(10) NOT NULL COMMENT '状态（1-待审核，2-已通过，3-未通过）',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `approve_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `qr_code` varchar(500) DEFAULT NULL COMMENT '二维码地址',
  `del_flag` enum('T','F') DEFAULT 'F' COMMENT '删除标识',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `update_id` bigint DEFAULT NULL COMMENT '更新人ID',
  `approve_id` bigint DEFAULT NULL COMMENT '审核人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校友卡表';

-- 校友会
CREATE TABLE `applet_alumni_association`(
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `avatar` varchar(500) DEFAULT NULL COMMENT '头像',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `contract` varchar(500) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `status` varchar(10) DEFAULT NULL COMMENT '状态（1-正常，2-禁用）',
  `del_flag` enum('T','F') DEFAULT 'F',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `update_id` bigint DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校友会表';

-- 校友会用户表
CREATE TABLE `applet_alumni_association_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `alumni_association_id` bigint NOT NULL COMMENT '校友会ID',
  `status` varchar(10) DEFAULT NULL COMMENT '状态（1-正常，2-禁用）',
  `del_flag` enum('T','F') DEFAULT 'F',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `update_id` bigint DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校友会用户表';

-- 校友会活动表
CREATE TABLE `applet_alumni_association_activity` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `alumni_association_id` bigint NOT NULL COMMENT '校友会ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `avatar` varchar(500) DEFAULT NULL COMMENT '头图',
  `content` text COMMENT '内容',
  `time` varchar(50) DEFAULT NULL COMMENT '时间',
  `location` varchar(100) DEFAULT NULL COMMENT '地点',
  `status` varchar(10) DEFAULT NULL COMMENT '状态（1-正常，2-禁用）',
  `del_flag` enum('T','F') DEFAULT 'F',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `update_id` bigint DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校友会活动表';