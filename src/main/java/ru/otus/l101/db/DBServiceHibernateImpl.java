package ru.otus.l101.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.service.ServiceRegistry;
import ru.otus.l101.dao.*;
import ru.otus.l101.dataset.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService, TypeNames {
    private Map<String, HibernateDAO> adapters;
    private final SessionFactory sessionFactory;

    private static Configuration defaultConfiguration() {
        Configuration cfg = new Configuration();

        cfg.addAnnotatedClass(UserDataSet.class);
        cfg.addAnnotatedClass(AddressDataSet.class);
        cfg.addAnnotatedClass(PhoneDataSet.class);
        cfg.addAnnotatedClass(EmptyDataSet.class);

        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        cfg.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        cfg.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/db");
        cfg.setProperty("hibernate.connection.username", "dbuser");
        cfg.setProperty("hibernate.connection.password", "password");
        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.hbm2ddl.auto", "create");
        cfg.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        return cfg;
    }

    public DBServiceHibernateImpl() {
        this(defaultConfiguration());

    }

    public DBServiceHibernateImpl(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    public void setDefaultDAOFor(Session session) {
        adapters = new HashMap<>();
        addAdapter(new AddressDataSetHibernateDAO(session));
        addAdapter(new PhoneDataSetHibernateDAO(session));
        addAdapter(new UserDataSetHibernateDAO(session));
    }

    public void addAdapter(HibernateDAO adapter) {
        if (! adapters.containsKey(adapter.getAdapteeOfType())) {
            adapters.put(adapter.getAdapteeOfType(), adapter);
            adapter.setAdapters(adapters);
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
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

    @Override
    public <T extends DataSet> void createTables(Class<T> clazz) {
        // TODO
    }

    @Override
    public <T extends DataSet> void save(T dataSet) {
        try (Session session = sessionFactory.openSession()) {
            setDefaultDAOFor(session);
            String key = dataSet.getClass().getName();
            HibernateDAO dao = adapters.getOrDefault(
                key, new EmptyDataSetHibernateDAO(session)
            );
            dao.save(dataSet);
            //session.save(dataSet);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return runInSession(session -> {
            setDefaultDAOFor(session);
            String key = clazz.getName();
            HibernateDAO dao = adapters.getOrDefault(
                key, new EmptyDataSetHibernateDAO(session)
            );
            //noinspection unchecked
            return (T) dao.read(id);
        });
    }

    @Override
    public UserDataSet loadByName(String name) {
        return runInSession(session -> {
            UserDataSetHibernateDAO dao = new UserDataSetHibernateDAO(session);
            return dao.readByName(name);
        });
    }

    @Override
    public List<UserDataSet> loadAll() {
        return runInSession(session -> {
            UserDataSetHibernateDAO dao = new UserDataSetHibernateDAO(session);
            return dao.readAll();
        });
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
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
