package ru.otus.l161.db;

/*
 * Created by VSkurikhin at winter 2018.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.service.ServiceRegistry;

import ru.otus.l161.MsgServerMain;
import ru.otus.l161.cache.*;
import ru.otus.l161.dao.*;
import ru.otus.l161.dataset.*;
import ru.otus.l161.messages.Address;
import ru.otus.l161.messages.Msg;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Class that provides common database services
 */
public class DBServiceImpl implements DBService {
    public static final int cacheSize = 10;
    private static final Logger LOG = LogManager.getLogger(MsgServerMain.class);

    private Map<String, DAO> adapters;
    private final SessionFactory sessionFactory;
    private CacheEngine<Long, DataSet> cache;

    private final Address address;

    public static Configuration defaultConfiguration() {
        Configuration cfg = new Configuration();

        cfg.addAnnotatedClass(AddressDataSet.class);
        cfg.addAnnotatedClass(EmptyDataSet.class);
        cfg.addAnnotatedClass(PhoneDataSet.class);
        cfg.addAnnotatedClass(UserDataSet.class);

        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        cfg.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        cfg.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/db");
        cfg.setProperty("hibernate.connection.username", "dbuser");
        cfg.setProperty("hibernate.connection.password", "password");
        cfg.setProperty("hibernate.show_sql", "false");
        cfg.setProperty("hibernate.hbm2ddl.auto", "validate");
        cfg.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        return cfg;
    }

    /**
     * Construct a `DbService` with service ID and the current application
     * @param id the service ID
     * @param app the current application
     */
    /**
     * TODO
     * @param session
     */
    public void setDefaultDAOFor(Session session) {
        adapters = new HashMap<>();
        addAdapter(new AddressDataSetDAO(session));
        addAdapter(new PhoneDataSetDAO(session));
        addAdapter(new UserDataSetDAO(session));
    }

    public static Metadata getMetadata(StandardServiceRegistryBuilder builder) {
        StandardServiceRegistry registry = builder.build();
        MetadataSources sources = new MetadataSources(registry);

        sources.addAnnotatedClass(AddressDataSet.class);
        sources.addAnnotatedClass(EmptyDataSet.class);
        sources.addAnnotatedClass(PhoneDataSet.class);
        sources.addAnnotatedClass(UserDataSet.class);

        return sources.buildMetadata();
    }

    /**
     * TODO
     */
    public DBServiceImpl(Address address) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.configure("createSchema.hibernate.cfg.xml");

        Metadata metadata = getMetadata(builder);

        SessionFactoryBuilder sessionFactoryBuilder = metadata.getSessionFactoryBuilder();

        sessionFactory = sessionFactoryBuilder.build();
        cache = new CacheEngineImpl<>(cacheSize, 10000, 0, false);
        this.address = address;
    }

    /**
     * TODO
     * @param adapter
     */
    public void addAdapter(DAO adapter) {
        if (! adapters.containsKey(adapter.getAdapteeOfType())) {
            adapters.put(adapter.getAdapteeOfType(), adapter);
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();

        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    public Connection getConnection() {
        try {
            ((SessionImplementor) sessionFactory)
                .getJdbcConnectionAccess()
                .obtainConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T extends DataSet> void save(T dataSet) {
        try (Session session = sessionFactory.openSession()) {
            setDefaultDAOFor(session);

            String key = dataSet.getClass().getName();
            DAO dao = adapters.getOrDefault(
                key, new EmptyDataSetDAO(session)
            );

            dao.save(dataSet);
            long longKey = dataSet.getClass().hashCode() + dataSet.getId();
            cache.put(longKey, dataSet);
        }
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        long softKey = clazz.hashCode() + id;
        DataSet element = cache.get(softKey);
        if (null != element) {
            //noinspection unchecked
            return (T) element;
        }
        return runInSession(session -> {
            setDefaultDAOFor(session);
            String key = clazz.getName();
            DAO dao = adapters.getOrDefault(
                key, new EmptyDataSetDAO(session)
            );
            //noinspection unchecked
            return (T) dao.read(id);
        });
    }

    @Override
    public UserDataSet loadByName(String name) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readByName(name);
        });
    }

    public List<UserDataSet> loadAll() {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAll();
        });
    }

    public int getHitCount() {
        return cache.getHitCount();
    }

    public int getMissCount() {
        return cache.getMissCount();
    }

    public void shutdown() {
        try {
            this.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void deliver(Msg msg) {

    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
