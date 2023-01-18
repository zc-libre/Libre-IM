CREATE TABLE IF NOT EXISTS sys_user
(
    id                int8,
    username          varchar(64),
    password          varchar(512),
    role_id           int8,
    dept_id           int8,
    nick_name         varchar(64),
    avatar            varchar(64),
    phone             varchar(64),
    email             varchar(64),
    gender            int2 NULL DEFAULT 0,
    enabled           int2 NULL DEFAULT 0,
    locked            int2 NULL DEFAULT 1,
    is_admin          int2 NULL DEFAULT 0,
    gmt_create        timestamp,
    gmt_modified      timestamp,
    gmt_create_name   varchar(64),
    gmt_modified_name varchar(64),
    is_deleted        int2
);

CREATE TABLE IF NOT EXISTS sys_role
(
    id                int8,
    role_name         varchar(50),
    parent_id         int8,
    status            int2,
    permission        varchar(64),
    gmt_create        timestamp,
    gmt_modified      timestamp,
    gmt_create_name   varchar(64),
    gmt_modified_name varchar(64),
    is_deleted        int2
);

CREATE TABLE IF NOT EXISTS sys_menu
(
    id                int8,
    parent_id         int8,
    title             varchar(64),
    name              varchar(64),
    seq               int4,
    path              varchar(256),
    permission        varchar(64),
    component         varchar(64),
    icon              varchar(64),
    is_frame          int2,
    type              int2,
    cache             int2,
    hidden            int2,
    status            int2,
    remark            varchar(512),
    gmt_create        timestamp,
    gmt_modified      timestamp,
    gmt_create_name   varchar(64),
    gmt_modified_name varchar(64),
    is_deleted        int2
);

CREATE TABLE IF NOT EXISTS sys_user_role
(
    id                int8,
    user_id           int8,
    role_id           int8,
    gmt_create        timestamp,
    gmt_modified      timestamp,
    gmt_create_name   varchar(64),
    gmt_modified_name varchar(64)
);

CREATE TABLE IF NOT EXISTS sys_role_menu
(
    id                int8,
    role_id           int8,
    menu_id           int8,
    gmt_create        timestamp,
    gmt_modified      timestamp,
    gmt_create_name   varchar(64),
    gmt_modified_name varchar(64)
);

-- DROP TABLE IF EXISTS quartz_job;
CREATE TABLE IF NOT EXISTS sys_job
(
    id                int8        NOT NULL,
    job_name          VARCHAR(64) NOT NULL,
    job_group         VARCHAR(64) NOT NULL,
    bean_name         VARCHAR(64) NOT NULL,
    trigger_name      VARCHAR(64) NOT NULL,
    trigger_group     VARCHAR(64) NOT NULL,
    repeat_interval   int8        NOT NULL,
    times_triggered   int8        NOT NULL,
    cron_expression   VARCHAR(16) NOT NULL,
    time_zone_id      VARCHAR(16) NOT NULL,
    trigger_state     VARCHAR(16) NOT NULL,
    is_deleted        INT2,
    gmt_create        timestamp   NOT NULL,
    gmt_modified      timestamp   NOT NULL,
    gmt_create_name   VARCHAR(64) NOT NULL,
    gmt_modified_name VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS sys_job_log
(
    id                int8        NOT NULL,
    job_name          VARCHAR(64) NOT NULL,
    bean_name         VARCHAR(64) NOT NULL,
    method_name       VARCHAR(64) NOT NULL,
    params            VARCHAR(64) NOT NULL,
    cron_expression   VARCHAR(64) NOT NULL,
    execute_time      int8        NOT NULL,
    success           int2        NOT NULL,
    exception_detail  text        NOT NULL,
    last_execute_time timestamp   NOT NULL,
    create_time       timestamp   NOT NULL
);

create table IF NOT EXISTS sys_log
(
    id             bigint not null
        primary key,
    user_id        bigint,
    username       varchar(20),
    log_type       varchar(64),
    description    varchar(128),
    params         text,
    data           text,
    success        smallint,
    class_method   varchar(128),
    stack_trace    text,
    request_ip     varchar(64),
    request_time   integer,
    os             varchar(64),
    browser        varchar(64),
    address        varchar(255),
    gmt_create     timestamp,
    app_name       varchar(64),
    request_method varchar(64),
    class_name     varchar(64),
    file_name      varchar(64),
    method_name    varchar(64),
    line_number    varchar(8),
    exception_name varchar(64),
    message        varchar(512)
);


create index sys_log_log_type_index on sys_log (log_type);

create index sys_log_gmt_create_index on sys_log (gmt_create desc);

create index sys_log_success_index on sys_log (success);

CREATE TABLE IF NOT EXISTS libre_blog
(
    id                int8,
    title             VARCHAR(64),
    content           text,
    picture           VARCHAR(512),
    views             int4,
    user_id           VARCHAR(64),
    category_id       VARCHAR(64),
    description       VARCHAR(1024),
    published         INT4,
    top               INT4,
    like_num          INT8,
    recommend         INT2,
    gmt_create        timestamp,
    gmt_modified      timestamp,
    gmt_create_name   VARCHAR(50),
    gmt_modified_name VARCHAR(50),
    is_deleted        int4,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS sys_dict_info
(
    id                int8,
    seq               int2,
    label             varchar(64),
    value             varchar(255),
    type              varchar(64),
    css_class         varchar(255),
    list_class        varchar(128),
    is_default        int2,
    status            int2,
    gmt_create_name   varchar(64),
    gmt_create        timestamp,
    gmt_modified_name varchar(64),
    gmt_modified      timestamp,
    remark            varchar(255)

);

CREATE TABLE IF NOT EXISTS sys_dict
(
    id                int8,
    name              varchar(64),
    description       varchar(64),
    status            int2,
    gmt_create_name   varchar(64),
    gmt_create        timestamp,
    gmt_modified_name varchar(64),
    gmt_modified      timestamp,
    remark            varchar(255)
);