package ru.otus.adapters.function;

import ru.otus.models.UserEntity;
import ru.otus.utils.NullString;

import java.util.function.Function;

public class CreateUserEntity implements Function<String[], UserEntity>
{
    @Override
    public UserEntity apply(String[] fields)
    {
        if (fields.length < 3) {
            throw new IndexOutOfBoundsException();
        }

        UserEntity entity = new UserEntity();

        entity.setId(Long.parseLong(fields[0]));
        entity.setLogin(fields[1]);
        entity.setPassword(NullString.returnString(fields[2]));

        return entity;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
