/*
 * DeptController.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.exceptions.ExceptionThrowable;
import ru.otus.models.DeptEntity;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.*;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

@Stateless
@Remote
@TransactionAttribute(SUPPORTS)
public class DeptController extends AbstractController<DeptEntity, Long>
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    void setEntityManager(EntityManager emf)
    {
        this.em = em;
    }

    @Override
    protected Class<DeptEntity> getTypeFirstParameterClass()
    {
        return DeptEntity.class;
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public List<DeptEntity> getAll() throws ExceptionThrowable
    {
        return getAll(DeptEntity.class);
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public DeptEntity getEntityById(Long id) throws ExceptionThrowable
    {
        return getEntityViaClassById(id, DeptEntity.class);
    }

    @TransactionAttribute(SUPPORTS)
    public DeptEntity getEntityByTitle(String title) throws ExceptionThrowable
    {
        return getEntityViaClassByName("title", title, DeptEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public DeptEntity update(DeptEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id) throws ExceptionThrowable
    {
        return deleteEntityViaClassById(id, DeptEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean create(DeptEntity entity) throws ExceptionThrowable
    {
        return persistEntity(entity);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
