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
     * The getter for connection.
     *
     * @return the connection
     */
    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * The DBService interface is an AutoCloseable.
     *
     * @throws Exception inasmuch as AutoCloseable
     */
    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }

    /**
     * The helper method is transactional executing procedure for list of SQL
     * queries.
     *
     * @param list the list of SQL queries
     */
    protected void execQueries(List<String> list) {
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
     * This method implements  the DBService interface contract. In first step,
     * call  the default adapter  for  the clazz of type T  and get list of DDL
     * queries, after that execute this list in the RDBMS via JDBC API.
     * In other words this method prepare the tables in DB for store objects of
     * Class type.
     *
     * @param clazz the Class
     * @param <T> the type of Class is a subclass of DataSet
     */
    @Override
    public <T extends DataSet> void createTables(Class<T> clazz) {
        execQueries(adapters.get(DEFAULT).create(clazz));
    }

    /**
     * This method implements  the DBService interface contract. In first step,
     * calls  the default  adapter for  the user's object  and get  list of DML
     * queries after that execute this list in the RDBMS via JDBC API.
     * In other words this method save contents from the object user to
     * the tables in DB.
     *
     * @param user the object
     * @param <T> the type of user is a subclass of DataSet
     */
    @Override
    public <T extends DataSet> void save(T user) {
        execQueries(adapters.get(DEFAULT).write(user));
    }

    /**
     * This method implements  the DBService interface contract. In first step,
     * loads contents from  a tuple in  the appropriate DB table, second step in
     * lambda, create the object and next iterates by fields and filling of this
     * object. If type of Class contains  filed's type is  a particular subclass
     * of DataSet, the loader with createObject call recursively.
     *
     * @param id the primary key of object tuple in the appropriate DB table
     * @param clazz the Class
     * @param <T> the type of Class
     * @return the object with type is a subclass of DataSet
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
