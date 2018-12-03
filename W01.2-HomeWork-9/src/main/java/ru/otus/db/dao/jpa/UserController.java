/*
 * UserController.java
 * This file was last modified at 2018.12.01 15:16 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

/*
 * This file was last modified at 30.11.18 0:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.UserEntity;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserController extends AbstractController<UserEntity, Long>
{
    public UserController(EntityManagerFactory entityManagerFactory)
    {
        super(entityManagerFactory);
    }

    @Override
    public List<UserEntity> getAll() throws ExceptionThrowable
    {
        return getAll(UserEntity.class);
    }

    @Override
    public UserEntity getEntityById(Long id) throws ExceptionThrowable
    {
        return getEntityViaClassById(id, UserEntity.class);
    }

    public UserEntity getEntityByTitle(String title) throws ExceptionThrowable
    {
        return getEntityViaClassByName("title", title, UserEntity.class);
    }

    @Override
    public UserEntity update(UserEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity);
    }

    @Override
    public boolean delete(Long id) throws ExceptionThrowable
    {
        return deleteEntityViaClassById(id, UserEntity.class);
    }

    @Override
    public boolean create(UserEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity) != null;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
