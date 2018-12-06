package ru.otus.adapters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.adapters.xml.UserEntityXMLAdapter;
import ru.otus.models.UserEntity;

import static org.junit.Assert.assertEquals;

public class UserEntityXMLAdapterTest
{
    UserEntity entity;
    UserEntityXMLAdapter adapter;

    @Before
    public void setUp() throws Exception
    {
        entity = new UserEntity();
        adapter = new UserEntityXMLAdapter();
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
        String xml = adapter.marshal(entity);
        UserEntity test = adapter.unmarshal(xml);
        assertEquals(expected, test);
    }
}