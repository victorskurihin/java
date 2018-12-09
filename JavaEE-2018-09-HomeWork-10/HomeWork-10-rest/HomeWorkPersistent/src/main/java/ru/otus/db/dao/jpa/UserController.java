/*
 * UserController.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
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

import ru.otus.exceptions.ExceptionThrowable;
import ru.otus.models.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class UserController extends AbstractController<UserEntity, Long>
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    // @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;
    // @PersistenceContext(unitName = "jpa")
    // private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        if (null == emf) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }

        return emf.createEntityManager(SynchronizationType.UNSYNCHRONIZED);
    }

    void setEntityManager(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    @Override
    protected Class<UserEntity> getTypeFirstParameterClass()
    {
        return UserEntity.class;
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
