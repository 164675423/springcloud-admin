/*所有api*/
drop table if exists api;
/*所有界面的操作按钮,如新增，修改，作废...*/
drop table if exists operation;
/*操作按钮与api关系*/
drop table if exists operation_api;
/*菜单*/
drop table if exists page;
/*角色与菜单、操作按钮关系*/
drop table if exists permission;

drop table if exists role;

drop table if exists user;

drop table if exists user_role;
/*白名单*/
drop table if exists white_list;

/*==============================================================*/
/* Table: api                                                   */
/*==============================================================*/
create table api
(
   id                   char(36) not null,
   name                 varchar(50) not null,
   http_method          varchar(50) not null,
   path_pattern         varchar(100) not null,
   primary key (id)
);


/*==============================================================*/
/* Table: operation                                             */
/*==============================================================*/
create table operation
(
   id                   char(36) not null,
   code                 varchar(50) not null,
   name                 varchar(50) not null,
   page_id              char(36) not null,
   primary key (id)
);

/*==============================================================*/
/* Table: operation_api                                         */
/*==============================================================*/
create table operation_api
(
   operation_id         char(36) not null,
   api_id               char(36) not null,
   primary key (operation_id, api_id)
);

/*==============================================================*/
/* Table: page                                                  */
/*==============================================================*/
create table page
(
   id                   char(36) not null,
   code                 varchar(50) not null,
   name                 varchar(50) not null,
   pinyin               varchar(200),
   initial              varchar(50),
   url                  varchar(100),
   level                int not null,
   parent_id            char(36),
   primary key (id)
);

/*==============================================================*/
/* Table: page_api                                              */
/*==============================================================*/
create table page_api
(
   page_id              char(36) not null,
   api_id               char(36) not null,
   primary key (api_id, page_id)
);

/*==============================================================*/
/* Table: permission                                            */
/*==============================================================*/
create table permission
(
   role_id              char(36) not null,
   related_id           char(36) not null,
   related_type         int not null comment '1: 页面;
            2: 业务操作;',
   primary key (role_id, related_id)
);

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   id                   char(36) not null,
   name                 varchar(50) not null,
   creator_id           char(36) not null,
   creator_name         varchar(50) not null,
   create_time          datetime not null,
   modifier_id          char(36),
   modifier_name        varchar(50),
   modify_time          datetime,
   status               int comment '2:有效
            99:作废;',
   readonly             bool not null default false comment '默认false',
   row_version          timestamp(6) not null,
   primary key (id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   char(36) not null,
   name                 varchar(50) not null,
   username             varchar(50) not null comment '用户名',
   password             varchar(100) not null,
   creator_id           char(36) not null,
   creator_name         varchar(50) not null,
   create_time          datetime not null,
   modifier_id          char(36),
   modifier_name        varchar(50),
   modify_time          datetime,
   status               int not null comment '2:有效;
            0:冻结;',
   readonly             bool not null default false comment '默认false',
   row_version          timestamp(6) not null,
   primary key (id)
);

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
create table user_role
(
   user_id              char(36) not null,
   role_id              char(36) not null,
   primary key (role_id, user_id)
);

/*==============================================================*/
/* Table: whitelist                                            */
/*==============================================================*/
create table whitelist
(
   api_id               char(36) not null,
   primary key (api_id)
);

