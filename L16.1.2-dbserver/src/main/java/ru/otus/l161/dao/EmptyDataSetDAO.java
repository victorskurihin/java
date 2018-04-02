package ru.otus.l161.dao;

/*
 * Created by VSkurikhin at winter 2018.
 */

import org.hibernate.Session;
import ru.otus.l161.exeption.NoImplementationException;
import ru.otus.l161.dataset.*;

import java.util.List;

/**
 * The class is stub as Data access object.
 */
public class EmptyDataSetDAO extends DAO {
    private final String DAO_TYPE = EmptyDataSet.class.getName();

    public EmptyDataSetDAO(Session session) {
        super(session);
    }

    @Override
    public String getAdapteeOfType() {
        return DAO_TYPE;
    }

    @Override
    public <T extends DataSet> void save(T dataSet) {
        throw new NoImplementationException();
    }

    @Override
    public <T extends DataSet> T read(long id) {
        throw new NoImplementationException();
    }

    @Override
    public <T extends DataSet> T readByName(String name) {
        throw new NoImplementationException();
    }

    @Override
    public <T extends DataSet> List<T> readAll() {
        throw new NoImplementationException();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
