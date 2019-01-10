package ru.otus.homework.services.dao;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.otus.homework.models.Author;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.outside.utils.TestData.*;

@DisplayName("Class JdbcAuthorDao")
class JdbcAuthorDaoTest
{
    private DataSource dataSource;

    private JdbcAuthorDao dao;

    private NamedParameterJdbcTemplate jdbc;

    private DSLContext dsl;

    @Test
    @DisplayName("is instantiated with new JdbcAuthorDao()")
    void isInstantiatedWithNew()
    {
        dataSource = injectTestDataSource();
        new JdbcAuthorDao(dataSource, dsl, jdbc);
    }

    private void printFindAll()
    {
        System.out.println("findAll = " + dao.findAll());
    }

    @Nested
    @DisplayName("when new default")
    class WhenNew
    {
        @BeforeEach
        void createNewQuestions()
        {
            dataSource = injectTestDataSource();
            jdbc = new NamedParameterJdbcTemplate(dataSource);
            dao = new JdbcAuthorDao(dataSource, dsl, jdbc);
        }

        @Test
        @DisplayName("injected values in JdbcAuthorDao()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("dataSource", dataSource);
            assertThat(dao).hasFieldOrProperty("namedParameterJdbcTemplate").isNotNull();
            assertThat(dao).hasFieldOrProperty("selectAuthorByFirstName").isNotNull();
            assertThat(dao).hasFieldOrProperty("selectAuthorByLastName").isNotNull();
            assertThat(dao).hasFieldOrProperty("insertAuthor").isNotNull();
            assertThat(dao).hasFieldOrProperty("updateAuthor").isNotNull();
            assertThat(dao).hasFieldOrProperty("deleteAuthor").isNotNull();
        }
    }

    @Nested
    @DisplayName("when new DAO operation")
    class WhenNewDAOoperation
    {
        // TODO
        MockDataProvider provider = new MockDataProvider()
        {
            // Your contract is to return execution results, given a context
            // object, which contains SQL statement(s), bind values, and some
            // other context values
            @Override
            public MockResult[] execute(MockExecuteContext context) throws SQLException
            {
                dataSource = injectTestDataSource();

                // Use ordinary jOOQ API to create an org.jooq.Result object.
                // You can also use ordinary jOOQ API to load CSV files or
                // other formats, here!
                DSLContext ctx = DSL.using(dataSource.getConnection());

                // Result<MyTableRecord> result = create.newResult(MY_TABLE);
                // result.add(create.newRecord(MY_TABLE));
                Result<?> result = ctx.fetchFromTXT(
                    "AUTHOR_ID first_name last_name\n" +
                    "--------- ---------- ---------\n" +
                    "        1 FirstTest  LastTest \n"
                );

                // Now, return 1-many results, depending on whether this is
                // a batch/multi-result context
                return new MockResult[] {
                    new MockResult(1, result)
                };
            }
        };
        // TODO
        // @BeforeEach
        void createNew() throws SQLException
        {
            // Put your provider into a MockConnection and use that connection
            // in your application. In this case, with a jOOQ DSLContext:
            // TODO dsl = DSL.using(new MockConnection(provider), SQLDialect.H2);
        }

        @BeforeEach
        void createNewDAO() throws SQLException
        {
            dataSource = injectTestDataSource();
            inserToTables(dataSource);
            jdbc = new NamedParameterJdbcTemplate(dataSource);
            dsl = DSL.using(dataSource.getConnection());
            dao = new JdbcAuthorDao(dataSource, dsl, jdbc);
        }

        @AfterEach
        void deleteFromTable() throws SQLException
        {
            clearTables(dataSource);
        }

        @Test
        @DisplayName("setter and getter for dataSource")
        void testSetGetDataSource()
        {
            dao.setDataSource(null);
            assertNull(dao.getDataSource());
            dao.setDataSource(dataSource);
            assertEquals(dataSource, dao.getDataSource());
        }

        @Test
        @DisplayName("find first name by id")
        void testFindFirstNameById()
        {
            assertEquals(TEST_FIRST_NAME, dao.findFirstNameById(TEST_ID));
            assertThrows(EmptyResultDataAccessException.class, () -> dao.findFirstNameById(Long.MAX_VALUE));
        }

        @Test
        @DisplayName("find last name by id")
        void testFindLastNameById()
        {
            assertEquals(TEST_LAST_NAME, dao.findLastNameById(TEST_ID));
            assertThrows(EmptyResultDataAccessException.class, () -> dao.findLastNameById(Long.MAX_VALUE));
        }

        @Test
        @DisplayName("find ALL")
        void testFindAll()
        {
            List<Author> expected = Collections.singletonList(createTestAuthor13());
            assertEquals(expected, dao.findAll());
        }

        @Test
        @DisplayName("find by first name")
        void testFindByFirstName()
        {
            List<Author> expected = Collections.singletonList(createTestAuthor13());
            assertEquals(expected, dao.findByFirstName(TEST_FIRST_NAME));
            assertTrue(dao.findByFirstName("__NONE__").isEmpty());
        }

        @Test
        @DisplayName("find by last name")
        void testFindByLastName()
        {
            List<Author> expected = Collections.singletonList(createTestAuthor13());
            assertEquals(expected, dao.findByLastName(TEST_LAST_NAME));
            assertTrue(dao.findByLastName("__NONE__").isEmpty());
        }

        @Test
        @DisplayName("find by id")
        void testFindById()
        {
            assertEquals(createTestAuthor13(), dao.findById(TEST_ID));
            assertThrows(EmptyResultDataAccessException.class, () -> dao.findById(Long.MAX_VALUE));
        }

        @Test
        @DisplayName("insert")
        void testInsert() throws SQLException
        {
            Author author = createTestAuthorAnother();
            assertEquals(0L, author.getId());

            boolean autoCommit = dataSource.getConnection().getAutoCommit();
            dataSource.getConnection().setAutoCommit(true);
            dao.insert(author);
            dataSource.getConnection().setAutoCommit(autoCommit);

            assertTrue(author.getId() > 0);
            assertEquals(2, dao.findAll().size());
            assertEquals(author, dao.findById(author.getId()));
        }

        @Test
        @DisplayName("update")
        void testUpdate() throws SQLException
        {
            Author authorModify = dao.findById(TEST_ID);
            authorModify.setFirstName(TEST_FIRST_NAME + TEST);
            authorModify.setLastName(TEST_LAST_NAME + TEST);

            boolean autoCommit = dataSource.getConnection().getAutoCommit();
            dataSource.getConnection().setAutoCommit(true);
            dao.update(authorModify);
            assertEquals(authorModify, dao.findById(TEST_ID));
            dataSource.getConnection().setAutoCommit(autoCommit);
        }

        @SuppressWarnings("Duplicates")
        @Test
        @DisplayName("delete")
        void testDelete() throws SQLException
        {
            assertEquals(1, dao.findAll().size());
            boolean autoCommit = dataSource.getConnection().getAutoCommit();
            dataSource.getConnection().setAutoCommit(true);
            dao.delete(TEST_ID);
            assertEquals(0, dao.findAll().size());
            dao.delete(Long.MAX_VALUE);
            dataSource.getConnection().setAutoCommit(autoCommit);
        }
    }
}
/*
AUTHOR_ID first_name last_name\n
--------- ---------- ---------\n
        1 FirstTest  LastTest \n
 */