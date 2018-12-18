package ru.otus.db.dao;

import ru.otus.models.UserEntity;

import javax.ejb.LocalBean;

@LocalBean
public interface UserDAO extends DAOController<UserEntity, Long>
{
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
