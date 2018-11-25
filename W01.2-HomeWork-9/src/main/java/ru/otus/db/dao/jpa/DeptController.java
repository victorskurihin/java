package ru.otus.db.dao.jpa;

/*
 * Created by VSkurikhin at winter 2018.
 */

import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DeptEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class DeptController extends AbstractController <DeptEntity, Long>
{
    public DeptController(EntityManager entityManager)
    {
        super(entityManager);
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
        return persistEntity(entity);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
