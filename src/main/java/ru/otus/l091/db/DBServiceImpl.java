package ru.otus.l091.db;

import com.google.common.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.l091.adapters.Adapter;
import ru.otus.l091.adapters.DefaultAdapter;
import ru.otus.l091.dataset.DataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBServiceImpl implements DBService {
    public static final String DEFAULT = "__DEFAULT__";

    private Map<String, Adapter> adapters = new HashMap<>();
    private final Connection connection = ConnectionHelper.getConnection(
        DBConf.dbName, DBConf.userName, DBConf.password
    );
    private final Logger logger = LogManager.getLogger(getClass());
    private final Adapter[] predefinedAdapters = {
        new DefaultAdapter(getConnection())
    };

    /**
     * Default constructor.
     */
    public DBServiceImpl() {
        for (Adapter a : predefinedAdapters) {
            adapters.put(a.getAdapteeOfType(), a);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }

    /**
     *
     * @param list
     * @param <T>
     */
    protected
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

    /**
     *
     * @param clazz
     * @param <T>
     */
    @Override
    public <T extends DataSet> void createTables(Class<T> clazz) {
        execQueries(adapters.get(DEFAULT).create(clazz));
    }

    /**
     *
     * @param user
     * @param <T>
     */
    @Override
    public <T extends DataSet> void save(T user) {
        execQueries(adapters.get(DEFAULT).write(user));
    }

    /**
     * This method implements  the DBService interface contract. In first step,
     * loads contents  from  the appropriate DB table, second  step  in lambda,
     * create the object and next iterates by fields and filleng of this object.
     *
     * @param id the primary key of object contents
     * @param clazz
     * @param <T>
     * @return
     */
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
