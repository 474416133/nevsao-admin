/* 2019-01-03
alter table sys_dept add column principal_id varchar(200) comment "负责人" after name;
alter table sys_user add column title varchar(200) comment "title" after  dept_id;
alter table sys_dict add column dict_key_rank int comment "键排序" after  column_name;
