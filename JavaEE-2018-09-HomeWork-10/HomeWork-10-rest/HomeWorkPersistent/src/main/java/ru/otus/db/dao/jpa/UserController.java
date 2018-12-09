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

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.*;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

@Stateless
@LocalBean
@TransactionAttribute(SUPPORTS)
public class UserController extends AbstractController<UserEntity, Long>
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    void setEntityManager(EntityManager em)
    {
        this.em = em;
    }

    @Override
    protected Class<UserEntity> getTypeFirstParameterClass()
    {
        return UserEntity.class;
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public List<UserEntity> getAll() throws ExceptionThrowable
    {
        return getAll(UserEntity.class);
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public UserEntity getEntityById(Long id) throws ExceptionThrowable
    {
        return getEntityViaClassById(id, UserEntity.class);
    }

    @TransactionAttribute(SUPPORTS)
    public UserEntity getEntityByTitle(String title) throws ExceptionThrowable
    {
        return getEntityViaClassByName("title", title, UserEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public UserEntity update(UserEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id) throws ExceptionThrowable
    {
        return deleteEntityViaClassById(id, UserEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean create(UserEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity) != null;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
