-- 档案申请记录表
DROP TABLE IF EXISTS `archive_files`;
CREATE TABLE `archive_files` (
 `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 `user_id` int DEFAULT NULL COMMENT '用户ID',
 `time` date DEFAULT NULL COMMENT '查阅日期',
 `phone` varchar(20) DEFAULT NULL COMMENT '联系方式',
 `name` varchar(50) DEFAULT NULL COMMENT '姓名',
 `card` varchar(20) DEFAULT NULL COMMENT '身份证号',
 `company` varchar(100) DEFAULT NULL COMMENT '工作单位',
 `mark` text COMMENT '查档内容和用途',
 `idea` varchar(50) DEFAULT NULL COMMENT '查档形式',
 `userNum` int DEFAULT NULL COMMENT '来访人数',
 `cardImg` text COMMENT '身份证及相关材料图片URL(逗号分隔)',
 `year` int DEFAULT NULL COMMENT '届次',
 `classNo` int DEFAULT NULL COMMENT '班级号',
 `pass` tinyint DEFAULT '0' COMMENT '审核状态(0:审核中,1:通过,2:不通过)',
 `passMark` varchar(255) DEFAULT NULL COMMENT '审核不通过原因',
 `state` tinyint DEFAULT NULL COMMENT '状态(用于校友通行证)',
 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 PRIMARY KEY (`id`),
 KEY `idx_user_id` (`user_id`),
 KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='档案申请记录表';