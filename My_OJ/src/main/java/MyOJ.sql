show databases ;

create database if not exists MyOJ;

use MyOJ;

drop table if exists OJ_table;

create table OJ_table (
                          id int primary key auto_increment,
                          title varchar(50),
                          level varchar(50),
                          description varchar(4096),
                          codeTemplate varchar(4096),
                          codeTest varchar(4096)
);

desc oj_table;

select *from oj_table;
