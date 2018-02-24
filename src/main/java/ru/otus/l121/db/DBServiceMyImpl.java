package ru.otus.l121.db;

import com.google.common.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.l121.cache.CacheEngine;
import ru.otus.l121.cache.CacheEngineImpl;
import ru.otus.l121.cache.MyElement;
import ru.otus.l121.cache.SoftReferenceElement;
import ru.otus.l121.dao.Adapter;
import ru.otus.l121.dao.DataSetOtusDAO;
import ru.otus.l121.dao.TypeNames;
import ru.otus.l121.dataset.DataSet;
import ru.otus.l121.dataset.UserDataSet;
import ru.otus.l121.exeption.NewInstanceException;
import ru.otus.l121.exeption.RuntimeSQLException;

import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBServiceMyImpl implements DBService {
    public static final int cacheSize = 10;
    private static final String DEFAULT = TypeNames.DEFAULT;

    private Map<String, Adapter> adapters = new HashMap<>();
    private final Connection connection = ConnectionHelper.getConnection(
        DBConf.dbName, DBConf.userName, DBConf.password
    );
    private final Logger logger = LogManager.getLogger(getClass());
    private final Adapter[] predefinedAdapters = {
        new DataSetOtusDAO(getConnection())
    };
    private CacheEngine<Long, DataSet> cache;

    /**
     * Default constructor.
     */
    public DBServiceMyImpl() {
        for (Adapter adapter : predefinedAdapters) {
            addAdapter(adapter);
        }
        cache = new CacheEngineImpl<>(
            cacheSize, 0, 0, true
        );
    }

    @SafeVarargs
    public DBServiceMyImpl(Class<?>... classes) {
        this();
        for (Class<?> c : classes) {
            try {
                Adapter adapter = (Adapter) c
                    .getDeclaredConstructor(Connection.class)
                    .newInstance(connection);
                addAdapter(adapter);

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                logger.error(e);
                throw new NewInstanceException(e);

            } catch (NoSuchMethodException | InvocationTargetException e) {
                logger.error(e);
                throw new NewInstanceException(e);
            }
        }
    }

    @Override
    public String getLocalStatus() {
        return "Not implemented!";
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

    public void addAdapter(Adapter adapter) {
        adapters.put(adapter.getAdapteeOfType(), adapter);
        adapter.setAdapters(adapters);
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
                throw new RuntimeSQLException(e2);
            }
            throw new RuntimeSQLException(e1);

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
        Adapter adapter = adapters.getOrDefault(
            clazz.getName(), adapters.get(DEFAULT)
        );

        execQueries(adapter.create(clazz));
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
        Adapter adapter = adapters.getOrDefault(
            user.getClass().getName(), adapters.get(DEFAULT)
        );
        cache.put(new SoftReferenceElement<>(user.getId(), user));
        execQueries(adapter.write(user));
    }

    <T extends DataSet> T loadFromDB(long id, Class<T> clazz) {
        Loader loader = new Loader(getConnection());
        Adapter adapter = adapters.getOrDefault(
            clazz.getName(), adapters.get(DEFAULT)
        );

        return loader.load(
            id, clazz, resultSet -> {
                if (resultSet.next()) {
                    return adapter.read(resultSet, TypeToken.of(clazz), id);
                } else
                    throw new SQLException("SQL Error!!!");
            }
        );
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
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        SoftReferenceElement<Long, DataSet> element = cache.get(id);
        if (null == element) {
            return loadFromDB(id, clazz);
        } else {
            //noinspection unchecked
            return (T) element.getValue();
        }

    }

    @Override
    public UserDataSet loadByName(String name) {
        return null; // TODO
    }

    @Override
    public List<UserDataSet> loadAll() {
        return null; // TODO
    }

    @Override
    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeSQLException(e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
