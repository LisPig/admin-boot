-- 订阅消息发送日志表
CREATE TABLE `message_log` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `openid` varchar(255) NOT NULL COMMENT '用户openid',
    `template_key` varchar(255) NOT NULL COMMENT '模板key',
    `content` text COMMENT '消息内容(JSON格式)',
    `success` tinyint(1) COMMENT '是否发送成功',
    `error_message` varchar(500) COMMENT '错误信息',
    `create_time` datetime COMMENT '创建时间',
    `update_time` datetime COMMENT '更新时间',
    `create_id` bigint COMMENT '创建人ID',
    `update_id` bigint COMMENT '更新人ID',
    `del_flag` char(1) DEFAULT '0' COMMENT '删除标识',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订阅消息发送日志表';