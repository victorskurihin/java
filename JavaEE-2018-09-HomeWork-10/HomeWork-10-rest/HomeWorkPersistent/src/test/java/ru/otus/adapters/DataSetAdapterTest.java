package ru.otus.adapters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.models.TestEntity;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import static junit.framework.TestCase.assertEquals;

public class DataSetAdapterTest implements DataSetAdapter<TestEntity>
{
    public static final String TEST = "test";
    DataSetAdapterTest selfTest;
    TestEntity entity;

    @Before
    public void setUp() throws Exception
    {
        selfTest = new DataSetAdapterTest();
        entity = new TestEntity();
    }

    @After
    public void tearDown() throws Exception
    {
        entity = null;
        selfTest = null;
    }

    @Test
    public void testGetTypeParameterClass()
    {
        Class<TestEntity> clazz = selfTest.getTypeParameterClass();
        assertEquals(TestEntity.class, clazz);
    }

    @Test
    public void testUnmarshalAdapter() throws Exception
    {
        TestEntity expected = new TestEntity();
        expected.setTest(TEST);
        TestEntity test1 = selfTest.unmarshalAdapter(TEST, TestEntity.class);
        assertEquals(expected, test1);
        TestEntity test2 = selfTest.unmarshalAdapter(TEST);
        assertEquals(expected, test2);
    }

    @Test
    public void testMarshalAdapter()
    {
        String expected = TEST;
        entity.setTest(TEST);
        String test = selfTest.marshalAdapter(entity);
        assertEquals(expected, test);
    }

    @Test
    public void testGetJsonWithIdObjectBuilder()
    {
        JsonObjectBuilder job = selfTest.getJsonWithIdObjectBuilder(entity);
        JsonObject jo = job.build();
        assertEquals(0, jo.getInt("id"));
    }

    @Test
    public void testMarshalToJson()
    {
        entity.letName(TEST);
        JsonObject jo = selfTest.marshalToJson(entity);
        assertEquals(0, jo.getInt("id"));
        assertEquals(TEST, jo.getString("name"));
    }

    @Test
    public void testUnmarshalFromJson() throws InstantiationException, IllegalAccessException
    {
        entity.letName(TEST);
        JsonObject jo = Json.createObjectBuilder()
                .add("id", entity.getId())
                .add("name", entity.nameGet()).build();
        TestEntity expected = selfTest.unmarshalFromJson(jo);
        assertEquals(expected, entity);
    }
}