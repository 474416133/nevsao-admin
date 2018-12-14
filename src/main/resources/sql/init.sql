CREATE DATABASE IF NOT EXISTS nevsao_admin default charset utf8 COLLATE utf8_general_ci;

drop index UNIQUE_INDEX_NAME on sys_dept;

drop table if exists sys_dept;

/*==============================================================*/
/* Table: sys_dept                                              */
/*==============================================================*/
create table sys_dept
(
   id                   varchar(32) not null comment '主键',
   name                 varchar(64) comment '名字',
   order_seq            int(10) comment '排序',
   creater_id           varchar(32) comment '创建者id',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   modifier_id          varchar(32) comment '修改人',
   primary key (id)
);

/*==============================================================*/
/* Index: UNIQUE_INDEX_NAME                                     */
/*==============================================================*/
create unique index UNIQUE_INDEX_NAME on sys_dept
(
   name
);


drop index UNIQUE_INDEX_DICT_KEY on sys_dict;

drop table if exists sys_dict;

/*==============================================================*/
/* Table: sys_dict                                              */
/*==============================================================*/
create table sys_dict
(
   id                   varchar(32) not null comment '主键',
   dict_key             varchar(32) comment '字典key',
   dict_value           varchar(64) comment '字典value',
   column_name          varchar(32) comment '列名',
   table_name           varchar(32) comment '表名',
   creater_id           varchar(32) comment '创建者id',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   modifier_id          varchar(32) comment '修改人id',
   primary key (id)
);

/*==============================================================*/
/* Index: UNIQUE_INDEX_DICT_KEY                                 */
/*==============================================================*/
create unique index UNIQUE_INDEX_DICT_KEY on sys_dict
(
   dict_key,
   column_name,
   table_name
);

drop index UNIQUE_INDEX_NAME on sys_role;

drop table if exists sys_role;

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   varchar(32) not null comment 'id',
   name                 varchar(64) comment '名字',
   remark               varchar(200) comment '描述',
   creater_id           varchar(32) comment '创建者id',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   modifier_id          varchar(32) comment '修改人',
   primary key (id)
);

/*==============================================================*/
/* Index: UNIQUE_INDEX_NAME                                     */
/*==============================================================*/
create unique index UNIQUE_INDEX_NAME on sys_role
(
   name
);

drop table if exists sys_user_log;

/*==============================================================*/
/* Table: sys_user_log                                          */
/*==============================================================*/
create table sys_user_log
(
   id                   varchar(32) not null comment '主键',
   user_id              varchar(32) comment '用户id',
   username             varchar(64) comment '用户名',
   op_desc              varchar(200) comment '操作描述',
   period_time          datetime comment '操作时间',
   op_method            varchar(500) comment '操作方法',
   op_params            varchar(1000) comment '操作参数',
   op_ip                int(10) comment '操作ip',
   op_location          varchar(64) comment '操作地址',
   create_time          datetime comment '创建时间',
   primary key (id)
);

drop index UNIQUE_INDEX_USER_ID_ROLE_ID on sys_user_role;

drop table if exists sys_user_role;

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   id                   varchar(32) not null comment '主键id',
   user_id              varchar(32) comment '用户id',
   role_id              varchar(32) comment '角色id',
   creater_id           varchar(32) comment '创建者id',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   modifier_id          varchar(32) comment '修改人',
   primary key (id)
);

/*==============================================================*/
/* Index: UNIQUE_INDEX_USER_ID_ROLE_ID                          */
/*==============================================================*/
create unique index UNIQUE_INDEX_USER_ID_ROLE_ID on sys_user_role
(
   user_id,
   role_id
);

drop index UNIQUE_INDEX_ROLE_ID_MENU_ID on sys_role_menu;

drop table if exists sys_role_menu;

/*==============================================================*/
/* Table: sys_role_menu                                         */
/*==============================================================*/
create table sys_role_menu
(
   id                   varchar(32) not null comment 'id',
   role_id              varchar(32) comment '角色id',
   menu_id              varchar(32) comment '菜单id',
   creater_id           varchar(32) comment '创建者id',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   modifier_id          varchar(32) comment '修改人',
   primary key (id)
);

/*==============================================================*/
/* Index: UNIQUE_INDEX_ROLE_ID_MENU_ID                          */
/*==============================================================*/
create unique index UNIQUE_INDEX_ROLE_ID_MENU_ID on sys_role_menu
(
   role_id,
   menu_id
);

drop index UNIQUE_INDEX_USERNAME on sys_user;

drop table if exists sys_user;

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   varchar(32) not null comment 'id',
   username             varchar(16) comment '用户名',
   password             varchar(64) comment '密码',
   dept_id              varchar(32) comment '部门id',
   email                varchar(64) comment '邮箱',
   mobile               varchar(16) comment '手机',
   gender               char(1) comment '性别',
   avatar               varchar(200) comment '头像',
   theme_using          varchar(64) comment '正在使用的主题id',
   is_active            int(0) comment '账号状态 0/1=禁用/启用',
   remark               varchar(200) comment '账号备注',
   last_login_time      datetime comment '最近登录时间',
   last_login_ip        int(10) comment '最近登录ip',
   creater_id           varchar(32) comment '创建者id',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   modifier_id          varchar(32) comment '修改人',
   primary key (id)
);

/*==============================================================*/
/* Index: UNIQUE_INDEX_USERNAME                                 */
/*==============================================================*/
create unique index UNIQUE_INDEX_USERNAME on sys_user
(
   username
);

drop table if exists 菜单表;

/*==============================================================*/
/* Table: 菜单表                                                   */
/*==============================================================*/
create table 菜单表
(
   id                   varchar(32) not null comment 'id',
   name                 varchar(64) comment '名字',
   url                  varchar(200) comment '地址',
   perms                varchar(200) comment '权限标识',
   icon                 varchar(64) comment '图标',
   menu_type            char(1) comment '类型，0/1=菜单/按钮',
   order_seq            int(10) comment '排序',
   creater_id           varchar(32) comment '创建者id',
   create_time          datetime comment '创建时间',
   modify_time          datetime comment '修改时间',
   modifier_id          varchar(32) comment '修改人',
   primary key (id)
);
