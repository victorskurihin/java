package ru.otus.l091;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdaptersTest {
    public static final String F0_BOOLEAN_DESC = "f0 BOOLEAN";
    public static final String F1_SMALLINT_DESC = "f1 SMALLINT";
    public static final String F2_CHAR_DESC = "f2 CHAR(1)";
    public static final String F3_SMALLINT_DESC = "f3 SMALLINT";
    public static final String F4_INT_DESC = "f4 INTEGER";
    public static final String F5_LONG_DESC = "f5 BIGINT";
    public static final String F6_STRING_DESC = "f6 TEXT";
    public static final String CREATE_EXPECTED_CLAUSE = " ( id BIGSERIAL PRIMARY KEY, " +
        "f0 BOOLEAN, f1 SMALLINT, f2 CHAR(1), f3 SMALLINT, f4 INTEGER, f5 BIGINT, f6 TEXT )";
    public static final String CREATE_TABLE_EXPECTED1 = "CREATE TABLE TestDataSetClass" +
                               CREATE_EXPECTED_CLAUSE;
    public static final String CREATE_TABLE_EXPECTED2 = "CREATE TABLE IF NOT EXISTS " +
        "ru_otus_l091_TestDataSetClass" + CREATE_EXPECTED_CLAUSE;
    public static final String CREATE_TABLE_EXPECTED3 = "CREATE TABLE IF NOT EXISTS " +
        "ru_otus_l091_TestComplexDataSetClass ( id BIGSERIAL PRIMARY KEY, \"fk test\" BIGINT )";
    public static final String F0_VALUE = "TRUE";
    public static final String F1_VALUE = "1";
    public static final String F2_VALUE = "'f'";
    public static final String F3_VALUE = "3";
    public static final String F4_VALUE = "4";
    public static final String F5_VALUE = "5";
    public static final String F6_VALUE = "'f6'";
    public static final String VALUES1 = "13, TRUE, 1, 'f', 3, 4, 5, 'f6'";
    public static final String INSERT1_VALUES1 = "INSERT INTO ru_otus_l091_TestDataSetClass VALUES (" +
                               VALUES1 + " )";
    public static final String INSERT2_VALUES2 = "INSERT INTO ru_otus_l091_TestComplexDataSetClass VALUES " +
        "(14, 13 )";
    public static final String INSERT3_VALUES3 = "INSERT INTO relationship VALUES " +
        "('ru_otus_l091_TestComplexDataSetClass', 14, 'test', 'ru_otus_l091_TestDataSetClass', 13)";

    Adapters adapters;
    TestClass testClass;
    TestDataSetClass testDataSetClass;
    TestComplexDataSetClass testComplexDataSetClass;

    @Before
    public void setUp() throws Exception {
        adapters = new Adapters();
        testClass = new TestClass();
        testDataSetClass = new TestDataSetClass(13);
        testComplexDataSetClass = new TestComplexDataSetClass(14);
    }

    @After
    public void tearDown() throws Exception {
        testComplexDataSetClass = null;
        testDataSetClass = null;
        testClass = null;
        adapters = null;
    }

    private void testColumnDescription(String fieldName, String expectedDescription)
        throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        Field f0 = testClass.getClass().getDeclaredField(fieldName);
        Method getColumnDescription = adapters.getClass().getDeclaredMethod(
            "getColumnDescription", Field.class
        );
        boolean accessible = getColumnDescription.isAccessible();
        getColumnDescription.setAccessible(true);
        String result = (String) getColumnDescription.invoke(adapters, f0);
        getColumnDescription.setAccessible(accessible);
        Assert.assertEquals(expectedDescription, result);
    }

    @Test
    public void testColumnBooleanDescription() throws Exception {
        testColumnDescription("f0", F0_BOOLEAN_DESC);
    }

    @Test
    public void testColumnSmallIntDescription() throws Exception {
        testColumnDescription("f1", F1_SMALLINT_DESC);
        testColumnDescription("f3", F3_SMALLINT_DESC);
    }

    @Test
    public void testColumnCharDescription() throws Exception {
        testColumnDescription("f2", F2_CHAR_DESC);
    }

    @Test
    public void testColumnIntDescription() throws Exception {
        testColumnDescription("f4", F4_INT_DESC);
    }

    @Test
    public void testColumnLongDescription() throws Exception {
        testColumnDescription("f5", F5_LONG_DESC);
    }

    @Test
    public void testColumnStringDescription() throws Exception {
        testColumnDescription("f6", F6_STRING_DESC);
    }

    @Test
    public void testColumnsForClass() throws Exception {
        Method getColumns = adapters.getClass().getDeclaredMethod(
            "getColumns", SQLCommand.class, DataSet.class.getClass()
        );
        boolean accessible = getColumns.isAccessible();
        getColumns.setAccessible(true);
        SQLCommand result = new SQLCommand("CREATE TABLE ", "TestDataSetClass");
        result.openParenthesis();
        result = (SQLCommand) getColumns.invoke(adapters, result, TestDataSetClass.class);
        getColumns.setAccessible(accessible);
        result.closeParenthesis();
        Assert.assertEquals(CREATE_TABLE_EXPECTED1, result.getSql());
    }

    @Test
    public void testCreateTableForClass() throws Exception {
        Method createTableForClass = adapters.getClass().getDeclaredMethod(
            "createTableForClass", DataSet.class.getClass()
        );
        boolean accessible = createTableForClass.isAccessible();
        createTableForClass.setAccessible(true);
        SQLCommand result = (SQLCommand) createTableForClass.invoke(adapters, TestDataSetClass.class);
        createTableForClass.setAccessible(accessible);
        Assert.assertEquals(CREATE_TABLE_EXPECTED2, result.getSql());
    }

    @Test
    public void testCreateTablesForClass() throws Exception {
        List<String> result = adapters.createTablesForClass(TestDataSetClass.class);
        List<String> expected = new ArrayList<>();
        expected.add(CREATE_TABLE_EXPECTED2);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testCreateTablesForClassComplex() throws Exception {
        //noinspection unchecked
        Set<String> result = new HashSet<>(
            adapters.createTablesForClass(TestComplexDataSetClass.class)
        );
        Set<String> expected = new HashSet<>();
        expected.add(CREATE_TABLE_EXPECTED2);
        expected.add(CREATE_TABLE_EXPECTED3);
        expected.add(Adapters.CREATE_TABLE_RELATIONSHIP);
        Assert.assertEquals(expected, result);
    }

    private void testGetValue(String fieldName, String expectedValue) throws Exception {
        Field f0 = testDataSetClass.getClass().getDeclaredField(fieldName);
        Method getValue = adapters.getClass().getDeclaredMethod(
            "getValue", Field.class, DataSet.class
        );
        boolean accessible = getValue.isAccessible();
        getValue.setAccessible(true);
        String result = (String) getValue.invoke(adapters, f0, testDataSetClass);
        getValue.setAccessible(accessible);
        Assert.assertEquals(expectedValue, result);

    }

    @Test
    public void testGetValueBoolean() throws Exception {
        testGetValue("f0", F0_VALUE);
    }

    @Test
    public void testGetValueBype() throws Exception {
        testGetValue("f1", F1_VALUE);
    }

    @Test
    public void testGetValueChar() throws Exception {
        testGetValue("f2", F2_VALUE);
    }

    @Test
    public void testGetValueShort() throws Exception {
        testGetValue("f3", F3_VALUE);
    }

    @Test
    public void testGetValueInt() throws Exception {
        testGetValue("f4", F4_VALUE);
    }

    @Test
    public void testGetValueLong() throws Exception {
        testGetValue("f5", F5_VALUE);
    }

    @Test
    public void testGetValueString() throws Exception {
        testGetValue("f6", F6_VALUE);
    }

    @Test
    public void testGetValuesObject() throws Exception {
        Method getValuesObject = adapters.getClass().getDeclaredMethod(
            "getValuesObject", SQLCommand.class, DataSet.class
        );
        boolean accessible = getValuesObject.isAccessible();
        getValuesObject.setAccessible(true);
        SQLCommand result = new SQLCommand("", "");
        result = (SQLCommand) getValuesObject.invoke(adapters, result, testDataSetClass);
        getValuesObject.setAccessible(accessible);
        Assert.assertEquals(VALUES1, result.getSql());
    }

    @Test
    public void testInsertObjectToTable() throws Exception {
        Method insertObjectToTable = adapters.getClass().getDeclaredMethod(
            "insertObjectToTable", DataSet.class
        );
        boolean accessible = insertObjectToTable.isAccessible();
        insertObjectToTable.setAccessible(true);
        SQLCommand result = (SQLCommand) insertObjectToTable.invoke(adapters, testDataSetClass);
        insertObjectToTable.setAccessible(accessible);
        Assert.assertEquals(INSERT1_VALUES1, result.getSql());
    }

    @Test
    public void testInsertObjectsToTables() throws Exception {
        List<String> result = adapters.insertObjectsToTables(testDataSetClass);
        List<String> expected = new ArrayList<>();
        expected.add(INSERT1_VALUES1);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testInsertObjectsToTablesComplex() throws Exception {
        //noinspection unchecked
        Set<String> result = new HashSet<>((
            adapters.insertObjectsToTables(testComplexDataSetClass)
        ));
        Set<String> expected = new HashSet<>();
        expected.add(INSERT1_VALUES1);
        expected.add(INSERT2_VALUES2);
        expected.add(INSERT3_VALUES3);
        Assert.assertEquals(expected, result);
    }
}