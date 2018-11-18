package ru.otus.utils;

import ru.otus.models.UserEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NullString
{
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        if ("NULL".equals(s.toUpperCase())) return null;

        UserEntity userEntity = new UserEntity();
        userEntity.setId(Long.parseLong(s));

        return userEntity;
    }
}
