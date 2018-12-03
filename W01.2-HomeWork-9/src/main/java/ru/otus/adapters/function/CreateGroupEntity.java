/*
 * CreateGroupEntity.java
 * This file was last modified at 2018.12.01 15:04 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.adapters.function;

import ru.otus.models.GroupEntity;

import java.util.function.Function;

public class CreateGroupEntity implements Function<String[], GroupEntity>
{
    /**
     * The Factory method for build GroupEntity from array of String's.
     *
     * @param fields array of String's.
     * @return new object.
     */
    @Override
    public GroupEntity apply(String[] fields)
    {
        if (fields.length < 3) {
            throw new IndexOutOfBoundsException();
        }

        GroupEntity entity = new GroupEntity();

        entity.setId(Long.parseLong(fields[0]));
        entity.setGroupname(fields[1]);
        entity.setLogin(fields[2]);

        return entity;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
