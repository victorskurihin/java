package ru.otus.l101.dao;

import com.google.common.reflect.TypeToken;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l101.NoImplementationException;
import ru.otus.l101.dataset.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

public class AdaptersTest {
    public static final String F0_BOOLEAN_DESC = "f0 BOOLEAN NOT NULL";
    public static final String F1_SMALLINT_DESC = "f1 SMALLINT NOT NULL";
    public static final String F2_CHAR_DESC = "f2 CHAR(1) NOT NULL";
    public static final String F3_SMALLINT_DESC = "f3 SMALLINT NOT NULL";
    public static final String F4_INT_DESC = "f4 INTEGER NOT NULL";
    public static final String F5_LONG_DESC = "f5 BIGINT NOT NULL";
    public static final String F6_STRING_DESC = "f6 REAL NOT NULL";
    public static final String CREATE_EXPECTED_CLAUSE = " ( id BIGSERIAL PRIMARY KEY, " +
        "f0 BOOLEAN NOT NULL, f1 SMALLINT NOT NULL, f2 CHAR(1) NOT NULL, f3 SMALLINT NOT NULL, " +
        "f4 INTEGER NOT NULL, f5 BIGINT NOT NULL, f6 REAL NOT NULL, f7 DOUBLE PRECISION NOT NULL, " +
        "f8 TEXT NOT NULL )";
    public static final String CREATE_TABLE_EXPECTED1 = "CREATE TABLE TestDataSetClass" +
        CREATE_EXPECTED_CLAUSE;
    public static final String CREATE_TABLE_EXPECTED2 = "CREATE TABLE IF NOT EXISTS " +
        "ru_otus_l101_dataset_TestDataSetClass" + CREATE_EXPECTED_CLAUSE;
    public static final String CREATE_TABLE_EXPECTED3 = "CREATE TABLE IF NOT EXISTS " +
        "ru_otus_l101_dataset_TestComplexDataSetClass ( id BIGSERIAL PRIMARY KEY, \"fk test\" BIGINT )";
    public static final String F0_VALUE = "TRUE";
    public static final String F1_VALUE = "1";
    public static final String F2_VALUE = "'f'";
    public static final String F3_VALUE = "3";
    public static final String F4_VALUE = "4";
    public static final String F5_VALUE = "5";
    public static final String F6_VALUE = "6.6";
    public static final String VALUES1 = "13, TRUE, 1, 'f', 3, 4, 5, 6.6, 7.7, 'f8'";
    public static final String INSERT1_VALUES1 = "INSERT INTO ru_otus_l101_dataset_TestDataSetClass VALUES" +
        " " +
        "(" + VALUES1 + " )";
    public static final String INSERT2_VALUES2 = "INSERT INTO ru_otus_l101_dataset_TestComplexDataSetClass" +
        " " +
        "VALUES (14, 13 )";
    public static final String INSERT3_VALUES3 = "INSERT INTO relationship VALUES " +
        "('ru_otus_l101_dataset_TestComplexDataSetClass', 14, 'test', " +
        "'ru_otus_l101_dataset_TestDataSetClass', 13)";

    Adapters testAdapters;
    TestClass testClass;
    TestDataSetClass testDataSetClass;
    TestComplexDataSetClass testComplexDataSetClass;

    @Before
    public void setUp() throws Exception {
        Map<String, Adapter> adapters = new HashMap<>();
        testAdapters = new Adapters(null);
        adapters.put(TypeNames.DEFAULT, new TestDataSetClassMyDAO(null));
        TestDataSetOneToManyMyDAO dao = new TestDataSetOneToManyMyDAO(null);
        adapters.put(dao.getAdapteeOfType(), dao);
        testAdapters.setAdapters(adapters);
        testClass = new TestClass();
        testDataSetClass = new TestDataSetClass(13);
        testComplexDataSetClass = new TestComplexDataSetClass(14);
    }

    @After
    public void tearDown() throws Exception {
        testComplexDataSetClass = null;
        testDataSetClass = null;
        testClass = null;
        testAdapters = null;
    }

    private void testColumnDescription(String fieldName, String expectedDescription)
        throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        Field f0 = testClass.getClass().getDeclaredField(fieldName);
        Method getColumnDescription = testAdapters.getClass().getDeclaredMethod(
            "getColumnDescription", Field.class
        );
        boolean accessible = getColumnDescription.isAccessible();
        getColumnDescription.setAccessible(true);
        String result = (String) getColumnDescription.invoke(testAdapters, f0);
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
        Method getColumns = testAdapters.getClass().getDeclaredMethod(
            "getColumns", SQLCommand.class, DataSet.class.getClass()
        );
        boolean accessible = getColumns.isAccessible();
        getColumns.setAccessible(true);
        SQLCommand result = new SQLCommand("CREATE TABLE ", "TestDataSetClass");
        result.openParenthesis();
        result = (SQLCommand) getColumns.invoke(testAdapters, result, TestDataSetClass.class);
        getColumns.setAccessible(accessible);
        result.closeParenthesis();
        Assert.assertEquals(CREATE_TABLE_EXPECTED1, result.getSql());
    }

    @Test
    public void testCreateTableForClass() throws Exception {
        Method createTableForClass = testAdapters.getClass().getDeclaredMethod(
            "createTableForClass", DataSet.class.getClass()
        );
        boolean accessible = createTableForClass.isAccessible();
        createTableForClass.setAccessible(true);
        SQLCommand result = (SQLCommand) createTableForClass.invoke(testAdapters, TestDataSetClass.class);
        createTableForClass.setAccessible(accessible);
        Assert.assertEquals(CREATE_TABLE_EXPECTED2, result.getSql());
    }

    @Test
    public void testCreateTablesForClass() throws Exception {
        List<String> result = testAdapters.createTablesForClass(TestDataSetClass.class);
        List<String> expected = new ArrayList<>();
        expected.add(CREATE_TABLE_EXPECTED2);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testCreateTablesForClassComplex() throws Exception {
        //noinspection unchecked
        Set<String> result = new HashSet<>(
            testAdapters.createTablesForClass(TestComplexDataSetClass.class)
        );
        Set<String> expected = new HashSet<>();
        expected.add(CREATE_TABLE_EXPECTED2);
        expected.add(CREATE_TABLE_EXPECTED3);
        expected.add(Adapters.CREATE_TABLE_RELATIONSHIP);
        Assert.assertEquals(expected, result);
    }

    /**
     * TODO experimental
     * @param tt the type token of the collection
     * @return the type token the element from the collection
     */
    private TypeToken<?> getTypeOfFirstParameterAddMethod(TypeToken<?> tt) {
        Method[] methods = Collection.class.getMethods();
        Optional<Method> optionalMethod = Optional.empty();

        for (Method m : methods) {
            if (m.getName().equals("add")) {
                if (1 == m.getParameterCount()) {
                    optionalMethod = Optional.of(m);
                    break;
                }
            }
        }

        if (! optionalMethod.isPresent()) {
            throw new NoImplementationException();
        }

        return tt.resolveType(
            optionalMethod.get().getGenericParameterTypes()[0]
        );
    }

    @Test
    public void testCreateTablesForClassDataSetOneToMany() throws Exception {
        Set<String> result = new HashSet<>(
            testAdapters.createTablesForClass(TestDataSetOneToManyClass.class)
        );
    }

    private void testGetValue(String fieldName, String expectedValue) throws Exception {
        Field f0 = testDataSetClass.getClass().getField(fieldName);
        Method getValue = testAdapters.getClass().getDeclaredMethod(
            "getValue", Field.class, DataSet.class
        );
        boolean accessible = getValue.isAccessible();
        getValue.setAccessible(true);
        String result = (String) getValue.invoke(testAdapters, f0, testDataSetClass);
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
        Method getValuesObject = testAdapters.getClass().getDeclaredMethod(
            "getValues", SQLCommand.class, DataSet.class.getClass(), DataSet.class
        );
        boolean accessible = getValuesObject.isAccessible();
        getValuesObject.setAccessible(true);
        SQLCommand result = new SQLCommand("", "");
        result = (SQLCommand) getValuesObject.invoke(testAdapters, result, testDataSetClass.getClass(), testDataSetClass);
        getValuesObject.setAccessible(accessible);
        Assert.assertEquals(VALUES1, result.getSql());
    }

    @Test
    public void testInsertObjectToTable() throws Exception {
        Method insertObjectToTable = testAdapters.getClass().getDeclaredMethod(
            "insertObjectToTable", DataSet.class
        );
        boolean accessible = insertObjectToTable.isAccessible();
        insertObjectToTable.setAccessible(true);
        SQLCommand result = (SQLCommand) insertObjectToTable.invoke(testAdapters, testDataSetClass);
        insertObjectToTable.setAccessible(accessible);
        Assert.assertEquals(INSERT1_VALUES1, result.getSql());
    }

    @Test
    public void testInsertObjectsToTables() throws Exception {
        List<String> result = testAdapters.insertObjectsToTables(testDataSetClass);
        List<String> expected = new ArrayList<>();
        expected.add(INSERT1_VALUES1);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testInsertObjectsToTablesComplex() throws Exception {
        //noinspection unchecked
        Set<String> result = new HashSet<>((
            testAdapters.insertObjectsToTables(testComplexDataSetClass)
        ));
        Set<String> expected = new HashSet<>();
        expected.add(INSERT1_VALUES1);
        expected.add(INSERT2_VALUES2);
        // expected.add(INSERT3_VALUES3);
        Assert.assertEquals(expected, result);
    }
}
