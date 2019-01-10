package ru.otus.homework.services.dao;


import static org.jooq.impl.DSL.param;
import static ru.otus.homework.db.h2.Tables.AUTHORS;
import static ru.otus.outside.utils.JdbcHelper.getLongOrDefault;
import static ru.otus.outside.utils.JdbcHelper.getStringOrDefault;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.NamedBeanHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Author;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Repository("authorDao")
public class JdbcAuthorDao extends JdbcAbstractDao<Author> implements AuthorDao
{
    public static final String F_AUTHOR_ID = "author_id";

    public static final String F_FIRST_NAME = "first_name";

    public static final String F_LAST_NAME = "last_name";

    public static String[] FIND_ALL_HEADER = {F_AUTHOR_ID, F_FIRST_NAME, F_LAST_NAME};

    public static final String TBL_AUTHOR = "authors";

    public static final String SELECT_ALL = "SELECT " + F_AUTHOR_ID + ", " + F_FIRST_NAME + ", " + F_LAST_NAME
                                          + " FROM " + TBL_AUTHOR;

    public static final String AUTHOR_ID = "authorId";

    public static final String AUTHOR_FN = "authorFn";

    public static final String AUTHOR_LN = "authorLn";

    public static final String SELECT_ALL_WHERE_ID = SELECT_ALL + " WHERE " + F_AUTHOR_ID + " = :" + AUTHOR_ID;

    private DataSource dataSource;

    public class SelectAuthorByName extends MappingSqlQuery<Author>
    {
        SelectAuthorByName(DataSource dataSource, String fieldName, String sid)
        {
            super(dataSource, SELECT_ALL + " WHERE " + fieldName + " LIKE :" + sid);
            super.declareParameter(new SqlParameter(sid, Types.VARCHAR));
        }

        @Override
        protected Author mapRow(ResultSet resultSet, int i) throws SQLException
        {
            return fetchAuthor(resultSet);
        }
    }

    private SelectAuthorByName selectAuthorByFirstName;

    private SelectAuthorByName selectAuthorByLastName;

    public class Insert extends SqlUpdate
    {
        public static final String SQL = "INSERT INTO " + TBL_AUTHOR  + " (" + F_FIRST_NAME + ", " + F_LAST_NAME
                                       + ") values (:" + F_FIRST_NAME + ", :" + F_LAST_NAME + ')';

        Insert(DataSource dataSource)
        {
            super(dataSource, SQL);
            super.declareParameter(new SqlParameter(F_FIRST_NAME, Types.VARCHAR));
            super.declareParameter(new SqlParameter(F_LAST_NAME, Types.VARCHAR));
            super.setGeneratedKeysColumnNames(F_AUTHOR_ID);
            super.setReturnGeneratedKeys(true);
        }
    }

    private Insert insertAuthor;

    public class Update extends SqlUpdate
    {
        public static final String SQL = "UPDATE " + TBL_AUTHOR
                                       + " SET " + F_FIRST_NAME + " = :" + F_FIRST_NAME
                                       + ", " + F_LAST_NAME + "= :" + F_LAST_NAME
                                       + " WHERE  " + F_AUTHOR_ID + " = :" + F_AUTHOR_ID;

        public Update(DataSource dataSource)
        {
            super(dataSource, SQL);
            super.declareParameter(new SqlParameter(F_FIRST_NAME, Types.VARCHAR));
            super.declareParameter(new SqlParameter(F_LAST_NAME, Types.VARCHAR));
            super.declareParameter(new SqlParameter(F_AUTHOR_ID, Types.BIGINT));
        }
    }

    private Update updateAuthor;

    private Delete deleteAuthor;

    private DSLContext dsl;

    NamedParameterJdbcTemplate jdbc;

    public JdbcAuthorDao(DataSource dataSource, DSLContext dsl, NamedParameterJdbcTemplate jdbc)
    {
        super(dataSource);
        this.dataSource = dataSource;
        this.selectAuthorByFirstName = new SelectAuthorByName(dataSource, F_FIRST_NAME, AUTHOR_FN);
        this.selectAuthorByLastName = new SelectAuthorByName(dataSource, F_LAST_NAME, AUTHOR_LN);
        this.insertAuthor = new Insert(dataSource);
        this.updateAuthor = new Update(dataSource);
        this.deleteAuthor = new Delete(dataSource, TBL_AUTHOR, F_AUTHOR_ID, AUTHOR_ID);

        this.dsl = dsl;
        this.jdbc = jdbc;
    }

    public DataSource getDataSource()
    {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public static Author buildBy(ResultSet resultSet) throws SQLException
    {
        Author author = new Author();

        author.setId(getLongOrDefault(resultSet, AUTHORS.AUTHOR_ID.getName()));
        author.setFirstName(getStringOrDefault(resultSet, AUTHORS.FIRST_NAME.getName()));
        author.setLastName(getStringOrDefault(resultSet, AUTHORS.LAST_NAME.getName()));

        return author;
    }

    public static Author fetchAuthor(ResultSet resultSet) throws SQLException
    {
        Author author = new Author();

        author.setId(resultSet.getLong(F_AUTHOR_ID));
        author.setFirstName(resultSet.getString(F_FIRST_NAME));
        author.setLastName(resultSet.getString(F_LAST_NAME));

        return author;
    }

    @Override
    public Author fetchFrom(ResultSet resultSet) throws SQLException
    {
        return fetchAuthor(resultSet);
    }

    @Override
    public List<Author> findAll()
    {
        String sql = dsl
            .select(AUTHORS.AUTHOR_ID, AUTHORS.FIRST_NAME, AUTHORS.LAST_NAME)
            .from(AUTHORS)
            .getSQL();

        return jdbc.query(sql, (rs, rn) -> buildBy(rs));
    }

    @Override
    public Author findById(long id)
    {
        MapSqlParameterSource mapParameter = new MapSqlParameterSource("id", id);
        String sql = dsl
            .select(AUTHORS.AUTHOR_ID, AUTHORS.FIRST_NAME, AUTHORS.LAST_NAME)
            .from(AUTHORS)
            .where( AUTHORS.AUTHOR_ID + " = :id")
            .getSQL();

        return jdbc.queryForObject(sql, mapParameter, (rs, rn) -> buildBy(rs));
    }

    @Override
    public List<Author> findAllWithDetail()
    {
        return findAll(); // TODO
    }

    @Override
    public List<Author> findByFirstName(String firstName)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(AUTHOR_FN, firstName);

        return selectAuthorByFirstName.executeByNamedParam(paramMap);
    }

    @Override
    public List<Author> findByLastName(String lastName)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(AUTHOR_LN, lastName);

        return selectAuthorByLastName.executeByNamedParam(paramMap);
    }

    @Override
    public String findFirstNameById(long id)
    {
        return findStringFieldNameById(TBL_AUTHOR, F_FIRST_NAME, F_AUTHOR_ID, AUTHOR_ID, id);
    }

    @Override
    public String findLastNameById(long id)
    {
        return findStringFieldNameById(TBL_AUTHOR, F_LAST_NAME, F_AUTHOR_ID, AUTHOR_ID, id);
    }

    @Override
    public void insert(Author author)
    {
        dsl.insertInto(AUTHORS)
            .set(AUTHORS.FIRST_NAME, author.getFirstName())
            .set(AUTHORS.LAST_NAME, author.getLastName())
            .execute();
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put(F_FIRST_NAME, author.getFirstName());
//        paramMap.put(F_LAST_NAME, author.getLastName());
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        insertAuthor.updateByNamedParam(paramMap, keyHolder);
//        author.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public void update(Author author)
    {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put(F_FIRST_NAME, author.getFirstName());
        paramMap.put(F_LAST_NAME, author.getLastName());
        paramMap.put(F_AUTHOR_ID, author.getId());

        updateAuthor.updateByNamedParam(paramMap);
    }

    @Override
    public void delete(long id)
    {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(AUTHOR_ID, id);
        deleteAuthor.updateByNamedParam(paramMap);
    }
}
