/*
 * CreateStatisticEntity.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.adapters.function;

import ru.otus.models.StatisticEntity;
import ru.otus.utils.NullString;

import java.util.function.Function;

public class CreateStatisticEntity implements Function<String[], StatisticEntity>
{
    /**
     * The factory method is for build StatisticEntity from array of String's.
     *
     * @param fields array of String's.
     * @return new object.
     */
    @Override
    public StatisticEntity apply(String[] fields)
    {
        if (fields.length < 10) {
            throw new IndexOutOfBoundsException();
        }

        StatisticEntity entity = new StatisticEntity();

        entity.setId(Long.parseLong(fields[0]));
        entity.setNameMarker(fields[1]);
        entity.setJspPageName(NullString.returnString(fields[2]));
        entity.setIpAddress(NullString.returnString(fields[3]));
        entity.setUserAgent(NullString.returnString(fields[4]));
        entity.setClientTime(NullString.returnLocalDateTime(fields[5]));
        entity.setServerTime(NullString.returnLocalDateTime(fields[6]));
        entity.setSessionId(NullString.returnString(fields[7]));
        entity.setUser(NullString.returnUserEntity(fields[8]));
        entity.setPreviousId(NullString.returnLong(fields[9]));

        return entity;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
