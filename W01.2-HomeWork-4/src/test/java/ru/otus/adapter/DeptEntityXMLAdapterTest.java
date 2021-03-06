package ru.otus.adapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.dataset.DeptEntity;
import ru.otus.dataset.UserEntity;

import static org.junit.Assert.assertEquals;

public class DeptEntityXMLAdapterTest
{
    DeptEntity entity;
    DeptEntityXMLAdapter adapter;

    @Before
    public void setUp() throws Exception
    {
        entity = new DeptEntity();
        adapter = new DeptEntityXMLAdapter();
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
        String xml = adapter.marshal(entity);
        DeptEntity test = adapter.unmarshal(xml);
        assertEquals(expected, test);
    }
}