alter table emp_registry drop constraint FKrx0lkq97klv1hub5v3xen9hlh
alter table emp_registry drop constraint FKbyijndlfb1a6vi97y5spvou5l
drop table if exists dep_directory cascade
drop table if exists emp_registry cascade
drop table if exists users cascade
drop sequence if exists hibernate_sequence
create sequence hibernate_sequence start 1 increment 1
create table dep_directory (id int8 not null, pid int8, title varchar(255), primary key (id))
create table emp_registry (id int8 not null, city varchar(255), firsh_name varchar(255) not null, job varchar(255), salary int8, second_name varchar(255) not null, sur_name varchar(255) not null, department int8, user_id int8, primary key (id))
create table users (id int8 not null, login varchar(255), password varchar(255), primary key (id))
alter table emp_registry add constraint FKrx0lkq97klv1hub5v3xen9hlh foreign key (department) references dep_directory
alter table emp_registry add constraint FKbyijndlfb1a6vi97y5spvou5l foreign key (user_id) references users
alter table emp_registry drop constraint FKrx0lkq97klv1hub5v3xen9hlh
alter table emp_registry drop constraint FKbyijndlfb1a6vi97y5spvou5l
drop table if exists dep_directory cascade
drop table if exists emp_registry cascade
drop table if exists users cascade
drop sequence if exists hibernate_sequence
create sequence hibernate_sequence start 1 increment 1
create table dep_directory (id int8 not null, pid int8, title varchar(255), primary key (id))
create table emp_registry (id int8 not null, city varchar(255), firsh_name varchar(255) not null, job varchar(255), salary int8, second_name varchar(255) not null, sur_name varchar(255) not null, department int8, user_id int8, primary key (id))
create table users (id int8 not null, login varchar(255), password varchar(255), primary key (id))
alter table emp_registry add constraint FKrx0lkq97klv1hub5v3xen9hlh foreign key (department) references dep_directory
alter table emp_registry add constraint FKbyijndlfb1a6vi97y5spvou5l foreign key (user_id) references users
