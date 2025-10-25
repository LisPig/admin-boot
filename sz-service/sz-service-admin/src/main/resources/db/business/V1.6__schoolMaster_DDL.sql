-- 校长表
DROP TABLE IF EXISTS `school_master`;
CREATE TABLE `school_master`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        varchar(50) NOT NULL COMMENT '姓名',
    `avatar`      varchar(500) DEFAULT NULL COMMENT '画像',
    `history`     text COMMENT '任期记录(包含开始结束时间和职务描述)',
    `del_flag`    enum('T','F') DEFAULT 'F' COMMENT '删除标识',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`   bigint       DEFAULT NULL COMMENT '创建人ID',
    `update_id`   bigint       DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校长表';

-- 相册表
DROP TABLE IF EXISTS `school_album`;
CREATE TABLE `school_album`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `title`       varchar(50) NOT NULL COMMENT '标题',
    `cover`       varchar(500) DEFAULT NULL COMMENT '封面图url',
    `category`    varchar(50) NOT NULL COMMENT '分类',
    `content`     text COMMENT '内容(图片urlJSON数组)',
    `del_flag`    enum('T','F') DEFAULT 'F' COMMENT '删除标识',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`   bigint       DEFAULT NULL COMMENT '创建人ID',
    `update_id`   bigint       DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='相册表';

-- 相册子集表
DROP TABLE IF EXISTS `school_album_child`;
CREATE TABLE `school_album_child`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `album_id`    bigint      NOT NULL COMMENT '父相册ID',
    `title`       varchar(50) NOT NULL COMMENT '标题',
    `cover`       varchar(500) DEFAULT NULL COMMENT '封面图url',
    `del_flag`    enum('T','F') DEFAULT 'F' COMMENT '删除标识',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='相册子集表';

-- 班级记忆表
DROP TABLE IF EXISTS `school_class_memory`;
CREATE TABLE `school_class_memory`
(
    `id`           bigint      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `year`         int         NOT NULL COMMENT '届数',
    `class_no`     varchar(20) NOT NULL COMMENT '班级编号',
    `teacher_list` text COMMENT '教师列表(json数组)',
    `student_list` text COMMENT '学生列表(逗号分割)',
    `images`       text COMMENT '图片列表(逗号分隔)',
    `del_flag`     enum('T','F') DEFAULT 'F' COMMENT '删除标识',
    `create_time`  datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`    bigint       DEFAULT NULL COMMENT '创建人ID',
    `update_id`    bigint       DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='班级记忆表';