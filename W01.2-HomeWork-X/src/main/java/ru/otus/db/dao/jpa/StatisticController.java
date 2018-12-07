/*
 * StatisticController.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.StatisticEntity;
import ru.otus.models.UserEntity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TemporalType;
import java.util.List;

import static ru.otus.utils.CalDateTime.localDateTimeToDate;

public class StatisticController extends AbstractController<StatisticEntity, Long>
{
    public StatisticController(EntityManagerFactory entityManagerFactory)
    {
        super(entityManagerFactory);
    }

    @Override
    public List<StatisticEntity> getAll() throws ExceptionThrowable
    {
        return getAll(StatisticEntity.class);
    }

    @Override
    public StatisticEntity getEntityById(Long id) throws ExceptionThrowable
    {
        return getEntityViaClassById(id, StatisticEntity.class);
    }

    @Override
    public StatisticEntity update(StatisticEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity);
    }

    @Override
    public boolean delete(Long id) throws ExceptionThrowable
    {
        return deleteEntityViaClassById(id, StatisticEntity.class);
    }

    @Override
    public boolean create(StatisticEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity) != null;
    }

    public long insertProcedure(StatisticEntity entity) throws ExceptionThrowable
    {
        return callStoredProcedureSingleResult("insert_statistic", proc -> {
            UserEntity user = entity.getUser();

            if (null == user) {
                user = new UserEntity();
                user.setId(-1L);
            }

            proc.setParameter("name_marker", entity.getNameMarker());
            proc.setParameter("jsp_page_name", entity.getJspPageName());
            proc.setParameter("ip_address", entity.getIpAddress());
            proc.setParameter("user_agent", entity.getUserAgent());
            proc.setParameter("client_time", localDateTimeToDate(entity.getClientTime()), TemporalType.TIMESTAMP);
            proc.setParameter("server_time", localDateTimeToDate(entity.getServerTime()), TemporalType.TIMESTAMP);
            proc.setParameter("session_id", entity.getSessionId());
            proc.setParameter("user_id", user.getId());
            proc.setParameter("prev_id", entity.getPreviousId());
        });
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
