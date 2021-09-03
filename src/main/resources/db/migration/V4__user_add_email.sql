alter table user
    add email varchar(50) not null comment '用户邮箱，一个邮箱仅限一个用户注册' after type;

alter table user modify gmt_modified bigint not null after email;

