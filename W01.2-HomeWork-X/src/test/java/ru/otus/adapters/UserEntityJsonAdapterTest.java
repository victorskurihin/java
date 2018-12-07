package ru.otus.adapters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.adapters.json.UserEntityJsonAdapter;
import ru.otus.models.UserEntity;

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