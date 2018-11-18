package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.db.ImporterSmallXML;
import ru.otus.models.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

public class DbJPAPostgreSQLService implements DbService
{
    public static final String TABLE_USERS = "users";
    public static final String TABLE_DEP_DIRECTORY = "dep_directory";
    public static final String TABLE_EMP_REGISTRY = "emp_registry";
    public static final String TABLE_USER_GROUPS = "user_groups";
    public static final String TABLE_STATISTIC = "statistic";

    public static final String[] TABLES = new String[] {
            TABLE_USERS,
            TABLE_DEP_DIRECTORY,
            TABLE_EMP_REGISTRY,
            TABLE_USER_GROUPS,
            TABLE_STATISTIC,
    };

    public static final String[] ORDER_OF_DELETE_TABLES = new String[] {
            TABLE_EMP_REGISTRY,
            TABLE_DEP_DIRECTORY,
            TABLE_STATISTIC,
            TABLE_USER_GROUPS,
            TABLE_USERS,
    };

    public static final Map<String , String> FILE_LOCATIONS = new HashMap<String , String>() {{
        put(TABLE_DEP_DIRECTORY, "DeptEntitiesXMLDataFileLocation");
        put(TABLE_EMP_REGISTRY,  "EmpEntitiesXMLDataFileLocation");
        put(TABLE_STATISTIC,     "StatisticEntitiesXMLDataFileLocation");
        put(TABLE_USER_GROUPS,   "UsersgroupEntitiesXMLDataFileLocation");
        put(TABLE_USERS,         "UserEntitiesXMLDataFileLocation");
    }};

    public static final Class<?>[] CLASSES = new Class<?>[] {
            DeptEntity.class,
            DeptEntitiesList.class,
            EmpEntity.class,
            EmpEntitiesList.class,
            GroupEntity.class,
            GroupEntitiesList.class,
            StatisticEntity.class,
            StatisticEntitiesList.class,
            UserEntity.class,
            UserEntitiesList.class,
    };

    private static final String SELECT_EMP_ENTITY = "SELECT e FROM EmpEntity e";
    private static final String SELECT_EMP_ENTITY_BY_ID = "SELECT e FROM EmpEntity e WHERE e.id = :id";
    private static final String SELECT_USER_ENTITY_BY_NAME =  "SELECT e FROM UserEntity e WHERE e.login = :name";;

    private static final String UPDATE_EMP_FIRST_NAME_BY_ID =
            "UPDATE EmpEntity e SET e.firstName = :name WHERE e.id = :id";
    private static final String UPDATE_EMP_SECOND_NAME_BY_ID =
            "UPDATE EmpEntity e SET e.secondName = :name WHERE e.id = :id";
    private static final String UPDATE_EMP_SUR_NAME_BY_ID = "UPDATE EmpEntity e SET e.surName = :name WHERE e.id = :id";

    private static final String DELETE_EMP_ENTITY_BY_ID = "DELETE FROM EmpEntity e WHERE e.id = :id";
    private static final String ALTER_SEQ_RESTART_WITH_1 = "ALTER SEQUENCE emp_id_seq RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_2 = "ALTER SEQUENCE hibernate_sequence RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_3 = "ALTER SEQUENCE statistic_id_seq RESTART WITH 1";

    private static final String FIO_PREDICATE =
            "(e.firstName LIKE :name OR e.secondName LIKE :name OR e.surName LIKE :name)";
    private static final String JOB_PREDICATE = "e.job LIKE :job";
    private static final String CITY_PREDICATE = "e.city LIKE :city";
    private static final String AGE_PREDICATE = "e.age = :age";

    private EntityManager em;

    public DbJPAPostgreSQLService(EntityManager em)
    {
        this.em = em;
    }

    @Override
    public void clearDb(ServletContext sc) throws Exception
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            for (String table : ORDER_OF_DELETE_TABLES) {
                em.createNativeQuery("DELETE FROM " + table + " CASCADE").executeUpdate();
            }
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_1).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_2).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_3).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    private static <T extends EntitiesList> ImporterSmallXML<T> createImporterXML(ServletContext sc, String s)
    throws IOException, JAXBException
    {
        String entitiesXMLPath = sc.getInitParameter(s);
        return new ImporterSmallXML<>(sc, entitiesXMLPath, CLASSES);
    }

    @Override
    public void importDb(ServletContext sc) throws Exception
    {
        for (String table : TABLES) {
            createImporterXML(sc, FILE_LOCATIONS.get(table)).saveEntities(em);
        }
    }

    @Override
    public void exportDb(ServletContext sc)
    {
        // TODO
    }

    private <T extends DataSet> List<T> getEntities(String sql, Consumer<Query> c)
    {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Query q = em.createQuery(sql);
            if (null != c) c.accept(q);
            //noinspection unchecked
            List<T> result = new ArrayList<>(q.getResultList());
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private <T extends DataSet> List<T> getEntitiesViaClass(Class<T> c)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
            Root<T> criteria = criteriaQuery.from(c);
            criteriaQuery.select(criteria);
            List<T> result = em.createQuery(criteriaQuery).getResultList();
            transaction.commit();

            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private  <T extends DataSet> T getEntity(String query, Consumer<Query> c)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query q = em.createQuery(query);
            if (null != c) c.accept(q);
            //noinspection unchecked
            T result = (T) q.getSingleResult();
            transaction.commit();
            return result;
        } catch (Exception e){
            transaction.rollback();
            throw e;
        }
    }

    private  <T extends DataSet> T getEntityById(String sql, long id)
    {
        return getEntity(sql, query -> query.setParameter("id", id));
    }

    private  <T extends DataSet> T getEntityByName(String sql, String name)
    {
        return getEntity(sql, query -> query.setParameter("name", name));
    }

    private <T extends DataSet> T getEntityViaClassById(long id, Class<T> c)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(c);
            Root<T> criteria = query.from(c);
            query = query.select(criteria)
                    .where(criteriaBuilder.equal(criteria.get("id"), id));
            T result = em.createQuery(query).getSingleResult();
            transaction.commit();

            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public List<StatisticEntity> getAllStatisticElements() throws SQLException
    {
        return getEntitiesViaClass(StatisticEntity.class);
    }


    private  <T extends DataSet> void mergeEntity(T entity)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(entity);
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    private void queryUpdate(String query, Consumer<Query> c)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query q = em.createQuery(query);
            if (null != c) c.accept(q);
            q.executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e); // TODO
        }
    }

    private void queryUpdateById(String query, long id)
    {
        queryUpdate(query, q -> q.setParameter("id", id));
    }

    private void queryUpdateVariableNameById(String query, long id, String value)
    {
        queryUpdate(query, q -> {
            q.setParameter("id", id);
            q.setParameter("name", value);
        });
    }

    private <T extends DataSet> void deleteEntityViaClassById(long id, Class<T> c)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaDelete<T> delete = criteriaBuilder.createCriteriaDelete(c);
            Root<T> criteria = delete.from(c);
            delete.where(criteriaBuilder.equal(criteria.get("id"), id));
            em.createQuery(delete).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private boolean isCorrectAttr(String key, Map<String, Object> attrs)
    {
        return attrs.containsKey(key) && attrs.get(key) != null;
    }

    @Override
    public List<EmpEntity> getEmpEntities()
    {
        return getEntities(SELECT_EMP_ENTITY, null);
    }

    @Override
    public <T extends DataSet> List<T> getEntities(Class<T> c)
    {
        return getEntitiesViaClass(c);
    }

    @Override
    public EmpEntity getEmpEntityById(long id)
    {
        try {
            return getEntityById(SELECT_EMP_ENTITY_BY_ID, id);
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public UserEntity getUserEntityByName(String name)
    {
        try {
            return getEntityByName(SELECT_USER_ENTITY_BY_NAME, name);
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public <T extends DataSet> T getEntityById(long id, Class<T> c)
    {
        try {
            return getEntityViaClassById(id, c);
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public <T extends DataSet> void saveEntity(T entity)
    {
        mergeEntity(entity);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(
            localDateTime.getYear(), localDateTime.getMonthValue()-1, localDateTime.getDayOfMonth(),
            localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()
        );
        return calendar.getTime();
    }

    @Override
    public long insertIntoStatistic(StatisticEntity entity)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            UserEntity user = entity.getUser();
            if (null == user) {
                user = new UserEntity();
                user.setId(-1L);
            }

            StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("insert_statistic");
            proc.setParameter("name_marker",   entity.getNameMarker());
            proc.setParameter("jsp_page_name", entity.getJspPageName());
            proc.setParameter("ip_address",    entity.getIpAddress());
            proc.setParameter("user_agent",    entity.getUserAgent());
            proc.setParameter("client_time",   localDateTimeToDate(entity.getClientTime()), TemporalType.TIMESTAMP);
            proc.setParameter("server_time",   localDateTimeToDate(entity.getServerTime()), TemporalType.TIMESTAMP);
            proc.setParameter("session_id",    entity.getSessionId());
            proc.setParameter("user_id",       user.getId());
            proc.setParameter("prev_id",       entity.getPreviousId());

            BigInteger result = (BigInteger) proc.getSingleResult();
            transaction.commit();
            return result.longValue();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public void updateFirstNameInEmpEntityById(long id, String firstName)
    {
        queryUpdateVariableNameById(UPDATE_EMP_FIRST_NAME_BY_ID, id, firstName);
    }

    @Override
    public void updateSecondNameInEmpEntityById(long id, String secondName)
    {
        queryUpdateVariableNameById(UPDATE_EMP_SECOND_NAME_BY_ID, id, secondName);
    }

    @Override
    public void updateSurNameInEmpEntityById(long id, String surName)
    {
        queryUpdateVariableNameById(UPDATE_EMP_SUR_NAME_BY_ID, id, surName);
    }

    @Override
    public void deleteEmpEntityById(long id)
    {
        queryUpdateById(DELETE_EMP_ENTITY_BY_ID, id);
    }

    @Override
    public <T extends DataSet> void deleteEntityById(long id, Class<T> c)
    {
        deleteEntityViaClassById(id, c);
    }

    @Override
    public List<EmpEntity> searchEmpEntity(Map<String, Object> attrs)
    {
        String sql = SELECT_EMP_ENTITY;
        String sqlTerm = " WHERE ";
        String sqlTermAnd = " AND ";

        if (isCorrectAttr("name", attrs)) {
            sql += sqlTerm;
            sql += FIO_PREDICATE;
            sqlTerm = sqlTermAnd;
        }
        if (isCorrectAttr("job", attrs)) {
            sql += sqlTerm;
            sql += JOB_PREDICATE;
            sqlTerm = sqlTermAnd;
        }
        if (isCorrectAttr("city", attrs)) {
            sql += sqlTerm;
            sql += CITY_PREDICATE;
            sqlTerm = sqlTermAnd;
        }
        if (isCorrectAttr("age", attrs)) {
            sql += sqlTerm;
            sql += AGE_PREDICATE;
        }

        return getEntities(sql, q -> attrs.forEach(q::setParameter));
    }

    @Override
    public void close()
    {
        em.close();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
