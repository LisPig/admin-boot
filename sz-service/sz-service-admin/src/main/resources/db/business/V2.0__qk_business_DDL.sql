
-- mini_user添加是否展示字段 默认为0
ALTER TABLE `school_class_memory`
    ADD COLUMN `title` varchar(255) DEFAULT '' COMMENT '标题'  AFTER `id`,
    ADD COLUMN `cover` varchar(255) DEFAULT '' COMMENT '封面'  AFTER `title`;
