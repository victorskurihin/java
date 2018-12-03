/*
 * NullString.java
 * This file was last modified at 2018.12.01 16:18 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.utils;

import ru.otus.models.UserEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NullString
{
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String NULL = "NULL";

    public static Long returnLong(String s) {
        return "NULL".equals(s.toUpperCase()) ? null : Long.parseLong(s);
    }

    public static String returnString(String s) {
        return "NULL".equals(s.toUpperCase()) ? null : s;
    }

    public static LocalDateTime returnLocalDateTime(String s)
    {
        return "NULL".equals(s.toUpperCase()) ? null : LocalDateTime.parse(s, dateTimeFormatter);
    }

    public static UserEntity returnUserEntity(String s)
    {
        if (NULL.equals(s.toUpperCase())) return null;

        UserEntity userEntity = new UserEntity();
        userEntity.setId(Long.parseLong(s));

        return userEntity;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
