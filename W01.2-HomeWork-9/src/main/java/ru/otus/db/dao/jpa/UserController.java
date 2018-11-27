/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ru.otus.db.dao.jpa;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.UserEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class UserController extends AbstractController <UserEntity, Long>
{
    public UserController(EntityManager entityManager)
    {
        super(entityManager);
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
        return persistEntity(entity);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
