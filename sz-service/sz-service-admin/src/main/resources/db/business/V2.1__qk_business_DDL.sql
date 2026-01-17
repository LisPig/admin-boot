
-- school_class_memory 修改表里year字段类型为varchar
ALTER TABLE `school_class_memory`
    MODIFY COLUMN `year` varchar(10) DEFAULT '' COMMENT '年份'  AFTER `cover`;

