package ru.otus.adapters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.adapters.json.DeptEntityJsonAdapter;
import ru.otus.models.DeptEntity;

import static org.junit.Assert.assertEquals;

public class DeptEntityJsonAdapterTest
{
    DeptEntity entity;
    DeptEntityJsonAdapter adapter;

    @Before
    public void setUp() throws Exception
    {
        entity = new DeptEntity();
        adapter = new DeptEntityJsonAdapter();
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
        DeptEntity expected = new DeptEntity();

        String json = adapter.adaptToJson(entity);
        DeptEntity test = adapter.adaptFromJson(json);

        assertEquals(expected, test);
    }
}