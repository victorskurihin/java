/*
 * JPAController.java
 * This file was last modified at 29.11.18 10:43 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao;

import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DataSet;

import javax.persistence.EntityManager;

public interface JPAController<E extends DataSet, K> extends DAOController<E, K>
{
    EntityManager mergeToEntityManager(E entity);

    E findById(EntityManager entityManager, long key) throws ExceptionThrowable;

    E findById(long key) throws ExceptionThrowable;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
