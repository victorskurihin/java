/*
 * GroupController.java
 * This file was last modified at 2018.12.01 15:16 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

/*
 * GroupController.java:
 * This file was last modified at 30.11.18 0:37 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.GroupEntity;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class GroupController extends AbstractController<GroupEntity, Long>
{
    public GroupController(EntityManagerFactory entityManagerFactory)
    {
        super(entityManagerFactory);
    }

    @Override
    public List<GroupEntity> getAll() throws ExceptionThrowable
    {
        return getAll(GroupEntity.class);
    }

    @Override
    public GroupEntity getEntityById(Long id) throws ExceptionThrowable
    {
        return getEntityViaClassById(id, GroupEntity.class);
    }

    public GroupEntity getEntityByTitle(String title) throws ExceptionThrowable
    {
        return getEntityViaClassByName("title", title, GroupEntity.class);
    }

    @Override
    public GroupEntity update(GroupEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity);
    }

    @Override
    public boolean delete(Long id) throws ExceptionThrowable
    {
        return deleteEntityViaClassById(id, GroupEntity.class);
    }

    @Override
    public boolean create(GroupEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity) != null;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
