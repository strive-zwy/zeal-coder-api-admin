create table blog
(
    id bigint auto_increment,
    title varchar(50) not null comment '标题',
    cover varchar(200) null comment '封面',
    description text not null comment '博客文章',
    is_deleted int default 0 null comment '是否已删除 0-false 1-true',
    comment_count int default 0 null comment '评论数量',
    view_count int default 0 null comment '浏览数量',
    like_count int default 0 null comment '点赞数量',
    tag varchar(100) not null comment '标签',
    gmt_create bigint not null,
    gmt_modified bigint not null,
    constraint blog_pk
        primary key (id)
)
    comment '博客实体';

create table comment
(
    id bigint auto_increment,
    blog_id bigint not null comment '博客id',
    type int not null comment '评论类型 0-博客的评论 1-评论的评论',
    parent_id int not null comment '父级id',
    commentator bigint not null comment '评论人',
    content varchar(300)  not null comment '评论内容',
    gmt_create bigint not null,
    gmt_modified bigint not null,
    constraint comment_pk
        primary key (id)
)
    comment '评论实体';

create table user
(
    id bigint auto_increment,
    account varchar(100)  not null comment '账号',
    avatar varchar(200)  not null comment '头像',
    jwtToken varchar(300)  not null comment '选项凭证',
    secret varchar(300)  null comment '密码或Github-token',
    type int not null comment '用户类型 0-GitHub 1-普通用户',
    gmt_create bigint not null,
    gmt_modified bigint not null,
    constraint user_pk
        primary key (id)
)
    comment '用户实体';

create table message
(
    id bigint auto_increment,
    creator bigint not null comment '留言人-用户Id',
    content varchar(200)  not null comment '留言内容',
    gmt_create bigint not null,
    gmt_modified bigint not null,
    constraint message_pk
        primary key (id)
)
    comment '留言实体';