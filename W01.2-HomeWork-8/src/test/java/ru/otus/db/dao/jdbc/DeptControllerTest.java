package ru.otus.db.dao.jdbc;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.db.Executor;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.DeptEntity;

import java.util.ArrayList;
import java.util.List;

public class DeptControllerTest
{
    private JdbcDataSource dataSource;
    private DeptController controller;

    @Before
    public void setUp() throws Exception
    {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test");
        controller = new DeptController(dataSource);
        new Executor(dataSource.getConnection()).execUpdate(
            "CREATE TABLE dep_directory (" +
            " id BIGINT NOT NULL," +
            " pid BIGINT NOT NULL," +
            " title VARCHAR(255) NOT NULL," +
            " PRIMARY KEY (ID))"
        );
    }

    @After
    public void tearDown() throws Exception
    {
        controller = null;
    }

    @Test
    public void testEmptyGetAll() throws ExceptionThrowable
    {
        List<DeptEntity> expected = new ArrayList<>();
        List<DeptEntity> test = controller.getAll();
        Assert.assertEquals(expected, test);
    }
}