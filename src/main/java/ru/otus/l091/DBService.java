package ru.otus.l091;

public interface DBService extends AutoCloseable {
    public <T extends DataSet> void createTables(Class<T> clazz);
    public <T extends DataSet> void save(T user);
    public <T extends DataSet> T load(long id, Class<T> clazz);
}
