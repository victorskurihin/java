package ru.otus.l091;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class DBServiceLog extends DBServiceAdapters {
    public static final String DEFAULT = "__DEFAULT__";
    private Logger logger = LogManager.getLogger(getClass());

    String classGetNameToTableName(Class <? extends DataSet> c) {
        return c.getName().replace('.','_');
    }

    @Override
    public <T extends DataSet> void createTables(Class<T> clazz) {

        Executor exec = new Executor(getConnection());
        List<String> createTables = adapters.get(DEFAULT).create(clazz);

        try {
            for (String createTable : createTables) {
                logger.info("Creating {} ...", createTable);
                exec.execUpdate(createTable);
                logger.info("Table {} created.", createTable);
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
                logger.info("Inserting {} ...", insert);
                exec.execUpdate(insert);
                logger.info("Value {} inserted.", insert);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return null;
    }
}
