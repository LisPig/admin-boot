-- `qk-school`.applet_alumni_association definition
DROP TABLE IF EXISTS `applet_alumni_association`;
CREATE TABLE `applet_alumni_association`
(
    `id`          bigint                                 NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
    `avatar`      varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
    `description` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
    `contract`    varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系人',
    `phone`       varchar(20) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '联系电话',
    `status`      varchar(10) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '状态（1-正常，2-禁用）',
    `del_flag`    enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F',
    `create_time` datetime                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`   bigint                                  DEFAULT NULL COMMENT '创建人ID',
    `update_id`   bigint                                  DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校友会表';


-- `qk-school`.applet_alumni_association_activity definition
DROP TABLE IF EXISTS `applet_alumni_association_activity`;
CREATE TABLE `applet_alumni_association_activity`
(
    `id`                    bigint                                  NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `alumni_association_id` bigint                                  NOT NULL COMMENT '校友会ID',
    `title`                 varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
    `avatar`                varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头图',
    `content`               text COLLATE utf8mb4_general_ci COMMENT '内容',
    `time`                  varchar(50) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '时间',
    `location`              varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地点',
    `status`                varchar(10) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '状态（1-正常，2-禁用）',
    `del_flag`              enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F',
    `create_time`           datetime                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`           datetime                                DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`             bigint                                  DEFAULT NULL COMMENT '创建人ID',
    `update_id`             bigint                                  DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校友会活动表';


-- `qk-school`.applet_alumni_association_user definition
DROP TABLE IF EXISTS `applet_alumni_association_user`;
CREATE TABLE `applet_alumni_association_user`
(
    `id`                    bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`               bigint NOT NULL COMMENT '用户ID',
    `alumni_association_id` bigint NOT NULL COMMENT '校友会ID',
    `status`                varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（-1-申请失败，0-申请中，1-正常，2-禁用）',
    `del_flag`              enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F',
    `create_time`           datetime                                                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`           datetime                                                     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`             bigint                                                       DEFAULT NULL COMMENT '创建人ID',
    `update_id`             bigint                                                       DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校友会用户表';


-- `qk-school`.applet_alumni_card definition
DROP TABLE IF EXISTS `applet_alumni_card`;
CREATE TABLE `applet_alumni_card`
(
    `id`             bigint                                                       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`        bigint                                                       NOT NULL COMMENT '用户ID',
    `name`           varchar(50) COLLATE utf8mb4_general_ci                       NOT NULL COMMENT '姓名',
    `phone`          varchar(20) COLLATE utf8mb4_general_ci                       NOT NULL COMMENT '手机号',
    `reason`         varchar(100) COLLATE utf8mb4_general_ci                      NOT NULL COMMENT '返校理由',
    `return_time`    datetime                                                     NOT NULL COMMENT '预计返校时间',
    `status`         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '状态（1-待审核，2-已通过，3-未通过）',
    `approve_time`   datetime                                                              DEFAULT NULL COMMENT '审核时间',
    `approve_remark` varchar(500) COLLATE utf8mb4_general_ci                               DEFAULT NULL COMMENT '审核备注',
    `qr_code`        varchar(500) COLLATE utf8mb4_general_ci                               DEFAULT NULL COMMENT '二维码地址',
    `del_flag`       enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    `create_time`    datetime                                                              DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                                                              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`      bigint                                                                DEFAULT NULL COMMENT '创建人ID',
    `update_id`      bigint                                                                DEFAULT NULL COMMENT '更新人ID',
    `approve_id`     bigint                                                                DEFAULT NULL COMMENT '审核人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校友卡表';


-- `qk-school`.applet_alumni_pass_application definition
DROP TABLE IF EXISTS `applet_alumni_pass_application`;
CREATE TABLE `applet_alumni_pass_application`
(
    `id`               bigint                                  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`          bigint                                  NOT NULL COMMENT '用户ID（关联用户表的外键）',
    `name`             varchar(50) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '申请人姓名',
    `phone`            varchar(20) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '电话号码',
    `year`             int                                              DEFAULT NULL COMMENT '毕业年份',
    `class_no`         varchar(20) COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '班级编号',
    `reason`           varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '其他' COMMENT '返校原因',
    `other_reason`     text COLLATE utf8mb4_general_ci COMMENT '其他原因详情',
    `expected_time`    date                                    NOT NULL COMMENT '预计返校时间',
    `application_time` datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    `status`           tinyint                                 NOT NULL DEFAULT '0' COMMENT '申请状态（0-待审核，1-已批准，2-已拒绝）',
    `use_status`       tinyint                                 NOT NULL DEFAULT '3' COMMENT '使用状态（0-已归还，1-待归还，2-未归还，3-待处理）',
    `approver_id`      bigint                                           DEFAULT NULL COMMENT '审批人ID',
    `approve_time`     datetime                                         DEFAULT NULL COMMENT '审批时间',
    `approve_remark`   text COLLATE utf8mb4_general_ci COMMENT '审批备注',
    `qr_code`          varchar(100) COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '二维码标识',
    PRIMARY KEY (`id`),
    KEY                `idx_user_id` (`user_id`),
    KEY                `idx_status` (`status`),
    KEY                `idx_expected_time` (`expected_time`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校友通行证申请表';


-- `qk-school`.applet_article definition
DROP TABLE IF EXISTS `applet_article`;
CREATE TABLE `applet_article`
(
    `id`           int                                     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `type`         varchar(50) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '文章类型',
    `title`        varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标题',
    `avatar`       varchar(500) COLLATE utf8mb4_general_ci                      DEFAULT NULL COMMENT '文章头图',
    `summary`      varchar(500) COLLATE utf8mb4_general_ci                      DEFAULT NULL COMMENT '文章摘要',
    `time`         varchar(50) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '发布时间',
    `label`        varchar(100) COLLATE utf8mb4_general_ci                      DEFAULT NULL COMMENT '标签',
    `author`       varchar(100) COLLATE utf8mb4_general_ci                      DEFAULT NULL COMMENT '作者',
    `status`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0-草稿1-发布）',
    `content_type` varchar(20) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '内容类型（link-链接, html-富文本）',
    `content`      text COLLATE utf8mb4_general_ci COMMENT '内容（链接地址或富文本内容）',
    `is_top`       tinyint                                                      DEFAULT '0' COMMENT '是否置顶（0-否，1-是）',
    `view_count`   int                                                          DEFAULT '0' COMMENT '浏览量',
    `like_count`   int                                                          DEFAULT '0' COMMENT '点赞数',
    `sort`         int                                                          DEFAULT '0' COMMENT '排序',
    `del_flag`     enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    `create_time`  datetime                                                     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime                                                     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`    bigint                                                       DEFAULT NULL COMMENT '创建人ID',
    `update_id`    bigint                                                       DEFAULT NULL COMMENT '更新人ID',
    `publish_id`   bigint                                                       DEFAULT NULL COMMENT '发布人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='小程序文章表';


-- `qk-school`.applet_banner definition
DROP TABLE IF EXISTS `applet_banner`;
CREATE TABLE `applet_banner`
(
    `id`           int NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `link`         varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '链接',
    `type`         varchar(50) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '类型',
    `names`        varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
    `picture`      varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片地址',
    `status`       varchar(10) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '状态（1-启用）',
    `sort`         int                                     DEFAULT '0' COMMENT '排序',
    `content_type` varchar(20) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '内容类型（link-链接）',
    `content`      text COLLATE utf8mb4_general_ci COMMENT '内容（链接地址）',
    `del_flag`     enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    `create_time`  datetime                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime                                DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`    bigint                                  DEFAULT NULL COMMENT '创建人ID',
    `update_id`    bigint                                  DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='小程序Banner表';


-- `qk-school`.apply_auth definition
DROP TABLE IF EXISTS `apply_auth`;
CREATE TABLE `apply_auth`
(
    `id`             bigint                                                       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`        bigint                                                       NOT NULL COMMENT '用户ID',
    `name`           varchar(50) COLLATE utf8mb4_general_ci                       NOT NULL COMMENT '姓名',
    `identity`       tinyint                                                               DEFAULT NULL COMMENT '身份（1-校友，2-教师）',
    `id_card`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '身份证号',
    `student_id`     varchar(50) COLLATE utf8mb4_general_ci                                DEFAULT NULL COMMENT '学号',
    `year`           int                                                                   DEFAULT NULL COMMENT '毕业年份',
    `class_no`       varchar(20) COLLATE utf8mb4_general_ci                                DEFAULT NULL COMMENT '班级编号',
    `teacher_id`     varchar(50) COLLATE utf8mb4_general_ci                                DEFAULT NULL COMMENT '教师编号',
    `status`         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '状态（1-待审核，2-已通过，3-未通过）',
    `approve_time`   datetime                                                              DEFAULT NULL COMMENT '审核时间',
    `approve_remark` varchar(500) COLLATE utf8mb4_general_ci                               DEFAULT NULL COMMENT '审核备注',
    `qr_code`        varchar(500) COLLATE utf8mb4_general_ci                               DEFAULT NULL COMMENT '二维码地址',
    `del_flag`       enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    `create_time`    datetime                                                              DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                                                              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `approve_id`     bigint                                                                DEFAULT NULL COMMENT '审核人ID',
    `phone`          varchar(50) COLLATE utf8mb4_general_ci                                DEFAULT NULL COMMENT '手机号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校友申请认证表';


-- `qk-school`.archive_files definition
DROP TABLE IF EXISTS `archive_files`;
CREATE TABLE `archive_files`
(
    `id`          int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     int          DEFAULT NULL COMMENT '用户ID',
    `time`        date         DEFAULT NULL COMMENT '查阅日期',
    `phone`       varchar(20)  DEFAULT NULL COMMENT '联系方式',
    `name`        varchar(50)  DEFAULT NULL COMMENT '姓名',
    `card`        varchar(20)  DEFAULT NULL COMMENT '身份证号',
    `company`     varchar(100) DEFAULT NULL COMMENT '工作单位',
    `mark`        text COMMENT '查档内容和用途',
    `idea`        varchar(50)  DEFAULT NULL COMMENT '查档形式',
    `userNum`     int          DEFAULT NULL COMMENT '来访人数',
    `cardImg`     text COMMENT '身份证及相关材料图片URL(逗号分隔)',
    `year`        int          DEFAULT NULL COMMENT '届次',
    `classNo`     int          DEFAULT NULL COMMENT '班级号',
    `pass`        tinyint      DEFAULT '0' COMMENT '审核状态(0:审核中,1:通过,2:不通过)',
    `passMark`    varchar(255) DEFAULT NULL COMMENT '审核不通过原因',
    `state`       tinyint      DEFAULT NULL COMMENT '状态(用于校友通行证)',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY           `idx_user_id` (`user_id`),
    KEY           `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='档案申请记录表';


-- `qk-school`.broadcast definition
DROP TABLE IF EXISTS `broadcast`;
CREATE TABLE `broadcast`
(
    `id`          bigint                                  NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `title`       varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
    `content`     text COLLATE utf8mb4_general_ci         NOT NULL COMMENT '内容',
    `status`      varchar(10) COLLATE utf8mb4_general_ci  NOT NULL COMMENT '状态（1-正常，2-禁用）',
    `del_flag`    enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`   bigint   DEFAULT NULL COMMENT '创建人ID',
    `update_id`   bigint   DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='广播表';


-- `qk-school`.donation_project definition
DROP TABLE IF EXISTS `donation_project`;
CREATE TABLE `donation_project`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        varchar(50) COLLATE utf8mb4_general_ci                       NOT NULL COMMENT '名称',
    `picture`     varchar(500) COLLATE utf8mb4_general_ci                               DEFAULT NULL COMMENT '图片',
    `description` varchar(500) COLLATE utf8mb4_general_ci                               DEFAULT NULL COMMENT '描述',
    `amount`      decimal(10, 2)                                               NOT NULL COMMENT '金额',
    `status`      varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '状态（1-待审核，2-审核通过，3-审核未通过）',
    `start_time`  datetime                                                              DEFAULT NULL COMMENT '项目开始时间',
    `end_time`    datetime                                                              DEFAULT NULL COMMENT '项目结束时间',
    `del_flag`    enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    `create_time` datetime                                                              DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`   bigint                                                                DEFAULT NULL COMMENT '创建人ID',
    `update_id`   bigint                                                                DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='捐款项目';


-- `qk-school`.donation_record definition
DROP TABLE IF EXISTS `donation_record`;
CREATE TABLE `donation_record`
(
    `id`          bigint                                 NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`     bigint                                 NOT NULL COMMENT '用户ID',
    `project_id`  bigint                                 NOT NULL COMMENT '项目ID',
    `amount`      decimal(10, 2)                         NOT NULL COMMENT '金额',
    `status`      varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（1-待处理，2-处理中，3-处理完成）',
    `del_flag`    enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`   bigint   DEFAULT NULL COMMENT '创建人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='捐款记录';


-- `qk-school`.school_album definition
DROP TABLE IF EXISTS `school_album`;
CREATE TABLE `school_album`
(
    `id`          bigint                                 NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `title`       varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
    `cover`       varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '封面图url',
    `category`    varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类',
    `content`     text COLLATE utf8mb4_general_ci COMMENT '内容(图片urlJSON数组)',
    `del_flag`    enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    `create_time` datetime                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`   bigint                                  DEFAULT NULL COMMENT '创建人ID',
    `update_id`   bigint                                  DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='相册表';


-- `qk-school`.school_album_child definition
DROP TABLE IF EXISTS `school_album_child`;
CREATE TABLE `school_album_child`
(
    `id`       bigint                                 NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `album_id` bigint                                 NOT NULL COMMENT '父相册ID',
    `title`    varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
    `cover`    varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '封面图url',
    `del_flag` enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='相册子集表';


-- `qk-school`.school_class_memory definition
DROP TABLE IF EXISTS `school_class_memory`;
CREATE TABLE `school_class_memory`
(
    `id`           bigint                                 NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `year`         int                                    NOT NULL COMMENT '届数',
    `class_no`     varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '班级编号',
    `teacher_list` text COLLATE utf8mb4_general_ci COMMENT '教师列表(json数组)',
    `student_list` text COLLATE utf8mb4_general_ci COMMENT '学生列表(逗号分割)',
    `images`       text COLLATE utf8mb4_general_ci COMMENT '图片列表(逗号分隔)',
    `del_flag`     enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    `create_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`    bigint   DEFAULT NULL COMMENT '创建人ID',
    `update_id`    bigint   DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='班级记忆表';


-- `qk-school`.school_master definition
DROP TABLE IF EXISTS `school_master`;
CREATE TABLE `school_master`
(
    `id`          bigint                                 NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
    `avatar`      varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '画像',
    `history`     text COLLATE utf8mb4_general_ci COMMENT '任期记录(包含开始结束时间和职务描述)',
    `del_flag`    enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    `create_time` datetime                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_id`   bigint                                  DEFAULT NULL COMMENT '创建人ID',
    `update_id`   bigint                                  DEFAULT NULL COMMENT '更新人ID',
    `start_time`  varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任职开始时间',
    `end_time`    varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任职结束时间',
    `position`    varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '职务',
    `description` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校长表';


-- `qk-school`.school_user definition
DROP TABLE IF EXISTS `school_user`;
CREATE TABLE `school_user`
(
    `id`          bigint                                  NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `name`        varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
    `phone`       varchar(255) COLLATE utf8mb4_general_ci                      DEFAULT NULL COMMENT '手机号',
    `identity`    tinyint                                                      DEFAULT NULL COMMENT '身份（1-校友，2-教师）',
    `id_card`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号',
    `student_id`  varchar(50) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '学号',
    `year`        int                                                          DEFAULT NULL COMMENT '毕业年份',
    `class_no`    varchar(20) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '班级编号',
    `teacher_id`  varchar(50) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '教师编号',
    `del_flag`    enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    `create_time` datetime                                                     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                                                     DEFAULT NULL COMMENT '更新时间',
    `create_id`   bigint                                                       DEFAULT NULL COMMENT '创建人ID',
    `update_id`   bigint                                                       DEFAULT NULL COMMENT '更新人ID',
    `status`      tinyint                                 NOT NULL             DEFAULT '0' COMMENT '申请状态：0-待审核，1-审核通过，2-审核拒绝',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学校师生表';


-- `qk-school`.school_user_binding definition
DROP TABLE IF EXISTS `school_user_binding`;
CREATE TABLE `school_user_binding`
(
    `id`             bigint  NOT NULL AUTO_INCREMENT COMMENT '绑定ID',
    `school_user_id` bigint  NOT NULL COMMENT '学校用户ID',
    `mini_user_id`   bigint  NOT NULL COMMENT '小程序用户ID',
    `bind_type`      tinyint NOT NULL DEFAULT '2' COMMENT '绑定类型：1-主绑定（认证），2-辅助绑定（共享）',
    `status`         tinyint NOT NULL DEFAULT '0' COMMENT '绑定状态：0-待审核，1-审核通过，2-审核拒绝',
    `del_flag`       enum('T','F') COLLATE utf8mb4_general_ci DEFAULT 'F' COMMENT '删除标识',
    `create_time`    datetime         DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime         DEFAULT NULL COMMENT '更新时间',
    `create_id`      bigint           DEFAULT NULL COMMENT '创建人ID',
    `update_id`      bigint           DEFAULT NULL COMMENT '更新人ID',
    PRIMARY KEY (`id`),
    KEY              `idx_school_user_id` (`school_user_id`),
    KEY              `idx_mini_user_id` (`mini_user_id`),
    KEY              `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学校用户绑定小程序用户';

-- `qk-school`.applet_square_memos definition
DROP TABLE IF EXISTS `applet_square_memos`;
CREATE TABLE `applet_square_memos`
(
    `id`            bigint NOT NULL AUTO_INCREMENT COMMENT '动态ID',
    `user_id`       bigint NOT NULL COMMENT '发布用户ID',
    `content`       text COLLATE utf8mb4_general_ci COMMENT '动态内容',
    `imgs`          text COLLATE utf8mb4_general_ci COMMENT '图片链接，逗号分隔',
    `tag_name`      varchar(50) COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '话题标签',
    `create_time`   datetime                                DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime                                DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `like_count`    bigint                                  DEFAULT '0' COMMENT '点赞总数',
    `comment_count` bigint                                  DEFAULT '0' COMMENT '评论总数',
    `tag_id`        bigint                                  DEFAULT NULL COMMENT '标签id',
    `position`      varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '位置',
    PRIMARY KEY (`id`),
    KEY             `user_id` (`user_id`),
    CONSTRAINT `applet_square_memos_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `mini_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='动态表';

-- `qk-school`.applet_square_comments definition
DROP TABLE IF EXISTS `applet_square_comments`;
CREATE TABLE `applet_square_comments`
(
    `id`          bigint                                 NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `memo_id`     bigint                                 NOT NULL COMMENT '动态ID',
    `user_id`     bigint                                 NOT NULL COMMENT '评论用户ID',
    `username`    varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论者用户名',
    `content`     text COLLATE utf8mb4_general_ci        NOT NULL COMMENT '评论内容',
    `reply_to`    varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '回复目标用户名',
    `reply_to_id` bigint                                 DEFAULT NULL COMMENT '回复目标评论ID',
    `create_time` datetime                               DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY           `memo_id` (`memo_id`),
    KEY           `user_id` (`user_id`),
    CONSTRAINT `applet_square_comments_ibfk_1` FOREIGN KEY (`memo_id`) REFERENCES `applet_square_memos` (`id`) ON DELETE CASCADE,
    CONSTRAINT `applet_square_comments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `mini_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='评论表';


-- `qk-school`.applet_square_follows definition
DROP TABLE IF EXISTS `applet_square_follows`;
CREATE TABLE `applet_square_follows`
(
    `id`               bigint NOT NULL AUTO_INCREMENT COMMENT '关注ID',
    `user_id`          bigint NOT NULL COMMENT '关注者ID',
    `followed_user_id` bigint NOT NULL COMMENT '被关注者ID',
    `create_time`      datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_follow` (`user_id`,`followed_user_id`),
    KEY                `followed_user_id` (`followed_user_id`),
    CONSTRAINT `applet_square_follows_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `mini_user` (`id`),
    CONSTRAINT `applet_square_follows_ibfk_2` FOREIGN KEY (`followed_user_id`) REFERENCES `mini_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='关注表';


-- `qk-school`.applet_square_likes definition
DROP TABLE IF EXISTS `applet_square_likes`;
CREATE TABLE `applet_square_likes`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
    `memo_id`     bigint NOT NULL COMMENT '动态ID',
    `user_id`     bigint NOT NULL COMMENT '点赞用户ID',
    `linked_user` bigint NOT NULL COMMENT '被点赞用户ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_like` (`memo_id`,`user_id`),
    KEY           `user_id` (`user_id`),
    CONSTRAINT `applet_square_likes_ibfk_1` FOREIGN KEY (`memo_id`) REFERENCES `applet_square_memos` (`id`) ON DELETE CASCADE,
    CONSTRAINT `applet_square_likes_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `mini_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='点赞表';


