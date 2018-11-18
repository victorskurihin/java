package ru.otus.adapters.function;

import ru.otus.models.DeptEntity;

import java.util.function.Function;

public class CreateDeptEntity implements Function<String[], DeptEntity>
{
    @Override
    public DeptEntity apply(String[] fields)
    {
        if (fields.length < 3) {
            throw new IndexOutOfBoundsException();
        }

        DeptEntity entity = new DeptEntity();

        entity.setId(Long.parseLong(fields[0]));
        entity.setParentId(Long.parseLong(fields[1]));
        entity.setTitle(fields[2]);

        return entity;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
