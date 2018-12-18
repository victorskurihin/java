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
public class EmpController extends AbstractController<EmpEntity, Long>
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
    protected Class<EmpEntity> getTypeFirstParameterClass()
    {
        return EmpEntity.class;
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public List<EmpEntity> getAll() throws ExceptionThrowable
    {
        return getAll(EmpEntity.class);
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public EmpEntity getEntityById(Long id) throws ExceptionThrowable
    {
        return getEntityViaClassById(id, EmpEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public EmpEntity update(EmpEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id) throws ExceptionThrowable
    {
        return deleteEntityViaClassById(id, EmpEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean create(EmpEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity) != null;
    }

    @TransactionAttribute(REQUIRES_NEW)
    public void updateFirstName(Long id, String firstName) throws ExceptionThrowable
    {
        updateEntity(id, entity -> entity.setFirstName(firstName));
    }

    @TransactionAttribute(REQUIRES_NEW)
    public void updateSecondName(Long id, String secondName) throws ExceptionThrowable
    {
        updateEntity(id, entity -> entity.setSecondName(secondName));
    }

    @TransactionAttribute(REQUIRES_NEW)
    public void updateSurName(Long id, String surName) throws ExceptionThrowable
    {
        updateEntity(id, entity -> entity.setSurName(surName));
    }

    @TransactionAttribute(SUPPORTS)
    public long getMaxSalary() throws ExceptionThrowable
    {
        return getMaxLong("salary", EmpEntity.class);
    }

    @TransactionAttribute(SUPPORTS)
    public double getAvgSalary() throws ExceptionThrowable
    {
        return getAvgDouble("salary", EmpEntity.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
