package ru.otus.l101.dao;

/*
 * Created by VSkurikhin at winter 2018.
 */

import com.google.common.reflect.TypeToken;
import ru.otus.l101.dataset.DataSet;
import ru.otus.l101.dataset.UserDataSet;
import ru.otus.l101.exeption.FieldFunctionException;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * By default this class save application classes to DB.
 */
public class UserDataSetMyDAO extends MyDAO {
    private final String ADAPTEE_TYPE = UserDataSet.class.getName();

    public UserDataSetMyDAO(Connection connection) {
        super(connection);
    }

    @Override
    public String getAdapteeOfType() {
        return ADAPTEE_TYPE;
    }

    private Map<String, HibernateDAO> adapters = new HashMap<>();
    /**
     * The helper method save a field of the object
     * @param field a field of the type by subclass DataSet
     * @param o the object contains the field.
     * @param <T> the type of the object
     */
    private <T extends DataSet> void saveField(Field field, T o) {
        boolean accessible = field.isAccessible();
        field.setAccessible(true);

        try {
            String key = field.getType().getName();
            HibernateDAO dao = adapters.getOrDefault(key, null);
            if (null != dao) {
                //noinspection unchecked
                dao.save((T) field.get(o));
            }
        } catch (Throwable e) {
            throw new FieldFunctionException(e);
        } finally {
            field.setAccessible(accessible);
        }
    }


}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
