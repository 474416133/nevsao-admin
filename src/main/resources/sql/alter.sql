/* 2019-01-03
alter table sys_dept add column principal_id varchar(200) comment "负责人" after name;
alter table sys_user add column title varchar(200) comment "title" after  dept_id;
alter table sys_dict add column dict_key_rank int comment "键排序" after  column_name;

/* 2019-01-07
alter table sys_user_log modify column op_time int comment "消耗时间";

/* 2019-01-12
alter table sys_dict add column dict_key_seq int comment "键排序" after  column_name;
alter table sys_dict drop column dict_key_rank;
alter table sys_dict modify column remark varchar(200) comment "备注" after  dict_key_seq;

/* 2019-01-14
alter table sys_role drop column remark;

/* 2019-01-20
alter table sys_user add column nickname varchar(64) comment "昵称" after  username;

/*2019-01-24
alter table sys_user_log drop column op_desc;
alter table sys_user_log drop column op_time;
alter table sys_user_log drop column op_method;
alter table sys_user_log drop column op_params;
alter table sys_user_log drop column op_ip;
alter table sys_user_log drop column op_location;
alter table sys_user_log add column method_remark varchar(200) comment "方法备注" after  username;
alter table sys_user_log add column period_time int comment "耗时（毫秒）" after  method_remark;
alter table sys_user_log add column method_name varchar(300) comment "方法名" after  period_time;
alter table sys_user_log add column method_params varchar(2000) comment "耗时（毫秒）" after  method_name;
alter table sys_user_log add column user_ip varchar(32) comment "用户ip" after  method_params;
alter table sys_user_log add column user_location varchar(64) comment "用户位置" after  user_ip;

