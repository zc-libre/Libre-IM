
create table im_user
(
    id        bigint not null
        constraint im_user_pk
            primary key,
    nike_name varchar(16),
    username  varchar(16),
    avatar    varchar(512),
    password  varchar(256),
    chat_code varchar(16),
    signature varchar(256),
    phone     varchar(16),
    address   varchar(256),
    email     varchar(16),
    age       integer,
    gender    smallint,
    locked    smallint default 0,
    enabled   smallint default 1
);

create unique index im_user_username_uindex
    on im_user (username);

create unique index im_user_phone_uindex
    on im_user (phone);

create unique index im_user_chat_code_uindex
    on im_user (chat_code);

-- auto-generated definition
create table im_message
(
    id             bigint not null
        constraint im_message_pk
            primary key,
    message        varchar(64),
    send_user_id   bigint,
    accept_user_id bigint,
    create_time    timestamp(6),
    type           smallint,
    status         smallint
);

create index im_message_status_index
    on im_message (status);

-- auto-generated definition
create table im_chat_group
(
    id             bigint,
    group_name     bigint,
    create_time    timestamp,
    update_time    timestamp,
    group_admin_id timestamp
);


-- auto-generated definition
create table im_user_role
(
    id      bigint not null
        constraint im_user_role_pk
            primary key,
    user_id bigint,
    role_id bigint
);

-- auto-generated definition
create table im_friend
(
    id          bigint not null
        constraint im_friend_pk
            primary key,
    friend_id   bigint,
    user_id     bigint,
    add_time    timestamp(6),
    is_top      smallint,
    update_time timestamp(6)
);

-- auto-generated definition
create table im_conversation
(
    id        bigint not null
        constraint im_conversation_pk
            primary key,
    user_id   bigint,
    friend_id bigint
);


-- auto-generated definition
create table im_role
(
    id        bigint not null
        constraint im_role_pk
            primary key,
    role_name varchar(64),
    title     varchar(64)
);

-- auto-generated definition
create table sys_error_log
(
    id             bigint not null
        constraint im_error_log_pk
            primary key,
    app_name       varchar(64),
    env            varchar(16),
    error_type     smallint,
    remote_host    varchar(32),
    request_id     varchar(64),
    request_method varchar(16),
    request_url    varchar(64),
    request_ip     varchar(16),
    stack_trace    varchar(1024),
    exception_name varchar(16),
    message        varchar(512),
    class_name     varchar(64),
    file_name      varchar(64),
    method_name    varchar(16),
    line_number    integer,
    create_time    timestamp(6)
);