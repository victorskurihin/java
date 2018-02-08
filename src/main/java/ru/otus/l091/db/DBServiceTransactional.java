package ru.otus.l091.db;

import com.google.common.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.l091.DataSet;

import java.sql.SQLException;
import java.util.List;

public class DBServiceTransactional extends DBServiceAdapters {
    public static final String DEFAULT = "__DEFAULT__";
    private Logger logger = LogManager.getLogger(getClass());

    <T extends DataSet> void execQueries(List<String> list) {
        Executor exec = new Executor(getConnection());

        try {
            getConnection().setAutoCommit(false);

            for (String quety : list) {
                exec.execUpdate(quety);
            }
            getConnection().commit();

        } catch (SQLException e1) {
            logger.error(e1);

            try {
                getConnection().rollback();
            } catch (SQLException e2) {
                logger.error(e2);
                throw new RuntimeException(e2);
            }
            throw new RuntimeException(e1);

        } catch (Throwable e) {
            logger.error(e);
        } finally {

            try {
                getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                logger.error(e);
            }
        }
    }


    @Override
    public <T extends DataSet> void createTables(Class<T> clazz) {
        execQueries(adapters.get(DEFAULT).create(clazz));
    }

    @Override
    public <T extends DataSet> void save(T user) {
        execQueries(adapters.get(DEFAULT).write(user));
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
