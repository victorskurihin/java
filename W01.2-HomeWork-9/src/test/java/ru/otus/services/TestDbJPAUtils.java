package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.models.DataSet;
import ru.otus.models.DeptEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class TestDbJPAUtils
{
    public static void persistDeptEntity(EntityManager em, long id, long pid, String title)
    {
        DeptEntity entity = new DeptEntity();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        entity.setId(id);
        entity.setParentId(pid);
        entity.setTitle(title);
        em.persist(entity);
        transaction.commit();
    }

    public static long countRowsEntities(EntityManager em, Class<? extends DataSet> c)
    {
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(c)));

        return em.createQuery(cq).getSingleResult();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
