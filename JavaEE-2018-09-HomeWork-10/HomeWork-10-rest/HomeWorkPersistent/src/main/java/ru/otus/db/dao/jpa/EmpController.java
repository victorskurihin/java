/*
 * EmpController.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.exceptions.ExceptionThrowable;
import ru.otus.models.EmpEntity;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class EmpController extends AbstractController<EmpEntity, Long>
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
    protected Class<EmpEntity> getTypeFirstParameterClass()
    {
        return EmpEntity.class;
    }

    @Override
    public List<EmpEntity> getAll() throws ExceptionThrowable
    {
        return getAll(EmpEntity.class);
    }

    @Override
    public EmpEntity getEntityById(Long id) throws ExceptionThrowable
    {
        return getEntityViaClassById(id, EmpEntity.class);
    }

    @Override
    public EmpEntity update(EmpEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity);
    }

    @Override
    public boolean delete(Long id) throws ExceptionThrowable
    {
        return deleteEntityViaClassById(id, EmpEntity.class);
    }

    @Override
    public boolean create(EmpEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity) != null;
    }

    public void updateFirstName(Long id, String firstName) throws ExceptionThrowable
    {
        updateEntity(id, entity -> entity.setFirstName(firstName));
    }

    public void updateSecondName(Long id, String secondName) throws ExceptionThrowable
    {
        updateEntity(id, entity -> entity.setSecondName(secondName));
    }

    public void updateSurName(Long id, String surName) throws ExceptionThrowable
    {
        updateEntity(id, entity -> entity.setSurName(surName));
    }

    public long getMaxSalary() throws ExceptionThrowable
    {
        return getMaxLong("salary", EmpEntity.class);
    }

    public double getAvgSalary() throws ExceptionThrowable
    {
        return getAvgDouble("salary", EmpEntity.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
