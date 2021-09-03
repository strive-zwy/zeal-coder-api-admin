alter table blog
    add `explain` varchar(50) null comment '博客描述' after description;

alter table blog modify is_deleted int default 0 null comment '是否已删除 0-false 1-true' after gmt_modified;

