package ru.otus.db.dao;

import ru.otus.exceptions.ExceptionThrowable;
import ru.otus.models.DeptEntity;

import javax.ejb.LocalBean;

@LocalBean
public interface DeptDAO extends DAOController<DeptEntity, Long>
{
    DeptEntity getEntityByTitle(String title) throws ExceptionThrowable;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
