package ru.otus.adapters.function;

import ru.otus.models.DeptEntity;
import ru.otus.models.EmpEntity;
import ru.otus.models.UserEntity;
import ru.otus.utils.NullString;

import java.util.function.Function;

public class CreateEmpEntity implements Function<String[], EmpEntity>
{
    @Override
    public EmpEntity apply(String[] fields)
    {
        if (fields.length < 10) {
            throw new IndexOutOfBoundsException();
        }

        EmpEntity entity = new EmpEntity();

        entity.setId(Long.parseLong(fields[0]));
        entity.setCity(NullString.returnString(fields[1]));
        entity.setFirstName(fields[2]);
        entity.setSecondName(fields[3]);
        entity.setSurName(fields[4]);
        entity.setJob(NullString.returnString(fields[5]));
        entity.setSalary(NullString.returnLong(fields[6]));

        if ("NULL".equals(fields[7].toUpperCase())) {
            entity.setDepartment(null);
        }
        else {
            DeptEntity deptEntity = new DeptEntity();
            deptEntity.setId(Long.parseLong(fields[7]));
            entity.setDepartment(deptEntity);
        }

        entity.setUser(NullString.returnUserEntity(fields[8]));
        entity.setAge(NullString.returnLong(fields[9]));

        return entity;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
