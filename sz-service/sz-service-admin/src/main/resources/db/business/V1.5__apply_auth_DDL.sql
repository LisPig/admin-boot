-- 申请认证表
DROP TABLE IF EXISTS `apply_auth`;
CREATE TABLE `apply_auth` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `identity` tinyint DEFAULT NULL COMMENT '身份（1-校友，2-教师）',
  `id_card` VARCHAR(50) NOT NULL COMMENT '身份证号',
  `student_id` VARCHAR(50) COMMENT '学号',
  `year` INT COMMENT '毕业年份',
  `class_no` VARCHAR(20) COMMENT '班级编号',
  `teacher_id` VARCHAR(50) COMMENT '教师编号',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校友申请认证表';

