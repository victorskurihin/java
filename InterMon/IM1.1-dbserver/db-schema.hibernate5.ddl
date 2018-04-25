alter table phones drop constraint FKmg6d77tgqfen7n1g763nvsqe3
alter table users drop constraint FKditu6lr4ek16tkxtdsne0gxib
drop table if exists address cascade
drop table if exists EmptyDataSet cascade
drop table if exists phones cascade
drop table if exists users cascade
create table address (id  bigserial not null, street varchar(255), primary key (id))
create table EmptyDataSet (id  bigserial not null, primary key (id))
create table phones (id  bigserial not null, number varchar(255), user_id int8 not null, primary key (id))
create table users (id  bigserial not null, name varchar(255), password varchar(255), address_id int8, primary key (id))
alter table address add constraint UK_5p3dypyron3on97vyujw2l95f unique (street)
alter table phones add constraint UK_iyb1ctsfd11m9cbkngt35br2v unique (number)
alter table users add constraint UK_3g1j96g94xpk3lpxl2qbl985x unique (name)
alter table phones add constraint FKmg6d77tgqfen7n1g763nvsqe3 foreign key (user_id) references users
alter table users add constraint FKditu6lr4ek16tkxtdsne0gxib foreign key (address_id) references address
