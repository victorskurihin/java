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

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class DeptController extends AbstractController<DeptEntity, Long>
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
    protected Class<DeptEntity> getTypeFirstParameterClass()
    {
        return DeptEntity.class;
    }

    @Override
    public List<DeptEntity> getAll() throws ExceptionThrowable
    {
        return getAll(DeptEntity.class);
    }

    @Override
    public DeptEntity getEntityById(Long id) throws ExceptionThrowable
    {
        return getEntityViaClassById(id, DeptEntity.class);
    }

    public DeptEntity getEntityByTitle(String title) throws ExceptionThrowable
    {
        return getEntityViaClassByName("title", title, DeptEntity.class);
    }

    @Override
    public DeptEntity update(DeptEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity);
    }

    @Override
    public boolean delete(Long id) throws ExceptionThrowable
    {
        return deleteEntityViaClassById(id, DeptEntity.class);
    }

    @Override
    public boolean create(DeptEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity) != null;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
