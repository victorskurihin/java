ALTER TABLE emp_registry DROP CONSTRAINT IF EXISTS FKrx0lkq97klv1hub5v3xen9hlh;
ALTER TABLE emp_registry DROP CONSTRAINT IF EXISTS FKbyijndlfb1a6vi97y5spvou5l;
ALTER TABLE statistic DROP CONSTRAINT IF EXISTS    FKoifiete5h1dmj02jk2fwe6d65;
DROP SEQUENCE IF EXISTS dept_id_seq;
DROP SEQUENCE IF EXISTS emp_id_seq;
DROP SEQUENCE IF EXISTS group_id_seq;
DROP SEQUENCE IF EXISTS statistic_id_seq;
DROP SEQUENCE IF EXISTS user_id_seq;
CREATE SEQUENCE dept_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE emp_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE group_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE statistic_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE user_id_seq START 1 INCREMENT 1;
DROP TABLE IF EXISTS dep_directory CASCADE;
DROP TABLE IF EXISTS emp_registry CASCADE;
DROP TABLE IF EXISTS statistic CASCADE;
DROP TABLE IF EXISTS user_groups CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP SEQUENCE IF EXISTS emp_id_seq;
DROP SEQUENCE IF EXISTS statistic_id_seq;
CREATE SEQUENCE emp_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE statistic_id_seq START 1 INCREMENT 1;
CREATE TABLE dep_directory (
  id BIGINT NOT NULL,
  pid BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE emp_registry (
  id BIGINT NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  second_name VARCHAR(255) NOT NULL,
  sur_name VARCHAR(255) NOT NULL,
  department BIGINT,
  city VARCHAR(255),
  job VARCHAR(255),
  salary BIGINT,
  user_id BIGINT,
  age BIGINT,
  PRIMARY KEY (id)
);
CREATE TABLE statistic (
  id BIGINT NOT NULL,
  client_time TIMESTAMP,
  ip_address VARCHAR(255) NOT NULL,
  jsp_page_name VARCHAR(255) NOT NULL,
  name_marker VARCHAR(255) NOT NULL,
  prev_id BIGINT,
  server_time TIMESTAMP,
  session_id VARCHAR(255) NOT NULL,
  user_agent VARCHAR(255) NOT NULL,
  user_id BIGINT, PRIMARY KEY (ID)
);
CREATE TABLE user_groups (
  id BIGINT NOT NULL,
  groupname VARCHAR(255) NOT NULL,
  login VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE users (
  id BIGINT NOT NULL,
  login VARCHAR(255) NOT NULL,
  password VARCHAR(255),
  PRIMARY KEY (id)
);
ALTER TABLE emp_registry ADD CONSTRAINT FKrx0lkq97klv1hub5v3xen9hlh FOREIGN KEY (department) REFERENCES dep_directory;
ALTER TABLE emp_registry ADD CONSTRAINT FKbyijndlfb1a6vi97y5spvou5l FOREIGN KEY (user_id)    REFERENCES users;
ALTER TABLE statistic    ADD CONSTRAINT FKoifiete5h1dmj02jk2fwe6d65 FOREIGN KEY (user_id)    REFERENCES users;
