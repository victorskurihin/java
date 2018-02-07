package ru.otus.l091;

import com.google.common.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBServiceLog extends DBServiceAdapters {
    public static final String DEFAULT = "__DEFAULT__";
    private Logger logger = LogManager.getLogger(getClass());
    private static final String SELECT1 = "SELECT * FROM %s";
    private static final String SELECT2 = "SELECT * FROM %s WHERE id=%s";

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
        try {
            System.out.println("clazz = " + TypeToken.of(clazz).getRawType().getTypeName() );
            TExecutor execT = new TExecutor(getConnection());
            String sql = String.format(SELECT1, classGetNameToTableName(clazz));
            System.out.println("sql = " + sql);
            return execT.execQuery(sql, resultSet -> {
                return adapters.get(DEFAULT).read(resultSet, TypeToken.of(clazz));
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
