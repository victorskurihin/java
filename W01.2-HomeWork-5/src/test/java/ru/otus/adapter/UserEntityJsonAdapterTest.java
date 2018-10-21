package ru.otus.adapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.dataset.EntityUtil;
import ru.otus.dataset.UserEntity;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import static org.junit.Assert.assertEquals;

public class UserEntityJsonAdapterTest
{
    UserEntity entity;
    UserEntityJsonAdapter adapter;

    @Before
    public void setUp() throws Exception
    {
        entity = new UserEntity();
        adapter = new UserEntityJsonAdapter();
    }

    @After
    public void tearDown() throws Exception
    {
        adapter = null;
        entity = null;
    }

    @Test
    public void adaptToJsonFromJson() throws Exception
    {
        UserEntity expected = new UserEntity();

        String json = adapter.adaptToJson(entity);
        UserEntity test = adapter.adaptFromJson(json);

        assertEquals(expected, test);
    }
}