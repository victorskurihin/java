package ru.otus.l091.db;

import com.google.common.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.l091.DataSet;

import java.sql.SQLException;
import java.util.List;

public class DBServiceLog extends DBServiceAdapters {
    public static final String DEFAULT = "__DEFAULT__";
    private Logger logger = LogManager.getLogger(getClass());

    @Override
    public <T extends DataSet> void createTables(Class<T> clazz) {

        Executor exec = new Executor(getConnection());
        List<String> createTables = adapters.get(DEFAULT).create(clazz);

        try {
            for (String createTable : createTables) {
                logger.info("Creating {} ...", createTable);
                exec.execUpdate(createTable);
                logger.info("By DDL {} created.", createTable);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends DataSet> void save(T user) {

        Executor exec = new Executor(getConnection());
        List<String> insertValues = adapters.get(DEFAULT).write(user);

        try {
            for (String insert : insertValues) {
                logger.info("Running {} ...", insert);
                exec.execUpdate(insert);
                logger.info("Query {} done.", insert);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        Loader loader = new Loader(getConnection());

        return loader.load(
            id, clazz, resultSet -> {
                if (resultSet.next()) {
                    return adapters.get(DEFAULT)
                        .read(resultSet, TypeToken.of(clazz), id);
                } else
                    throw new RuntimeException("SQL Error!!!");
            }
        );
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
