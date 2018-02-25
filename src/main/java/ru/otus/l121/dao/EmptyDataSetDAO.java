package ru.otus.l121.dao;

import org.hibernate.Session;
import ru.otus.l121.exeption.NoImplementationException;
import ru.otus.l121.dataset.*;

import java.util.List;

/**
 * TODO
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
