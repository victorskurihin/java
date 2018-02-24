package ru.otus.l121.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l121.dataset.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class AdaptersTest {
    public static final String CREATE_EXPECTED_CLAUSE = " ( id BIGSERIAL PRIMARY KEY, " +
        "f0 BOOLEAN NOT NULL, f1 SMALLINT NOT NULL, f2 CHAR(1) NOT NULL, " +
        "f3 SMALLINT NOT NULL, f4 INTEGER NOT NULL, f5 BIGINT NOT NULL, " +
        "f6 REAL NOT NULL, f7 DOUBLE PRECISION NOT NULL, f8 TEXT NOT NULL )";

    public static final String CREATE_TABLE_EXPECTED1 = "CREATE TABLE IF NOT EXISTS " +
        "TestDataSet" + CREATE_EXPECTED_CLAUSE;

    public static final String CREATE_TABLE_EXPECTED2 = "CREATE TABLE IF NOT EXISTS " +
        "ru_otus_l121_dataset_TestDataSet" + CREATE_EXPECTED_CLAUSE;

    public static final String CREATE_TABLE_EXPECTED3 = "CREATE TABLE IF NOT EXISTS " +
        "ru_otus_l121_dataset_UserDataSet ( id BIGSERIAL PRIMARY KEY, name TEXT NOT NULL, " +
        "\"fk address\" BIGINT, \"rt phones\" TEXT )";
    public static final String CREATE_TABLE_EXPECTED4 = "CREATE TABLE IF NOT EXISTS " +
        "\"java_util_Set ru_otus_l121_dataset_PhoneDataSet\" " +
        "( id BIGSERIAL PRIMARY KEY, parent_id BIGINT, number TEXT NOT NULL )";

    public static final String CREATE_TABLE_EXPECTED5 = "CREATE TABLE IF NOT EXISTS " +
        "ru_otus_l121_dataset_AddressDataSet ( id BIGSERIAL PRIMARY KEY, " +
        "street TEXT NOT NULL )";

    public static final String F0_VALUE = "TRUE";
    public static final String F1_VALUE = "1";
    public static final String F2_VALUE = "'f'";
    public static final String F3_VALUE = "3";
    public static final String F4_VALUE = "4";
    public static final String F5_VALUE = "5";
    public static final String F6_VALUE = "6.6";
    public static final String VALUES1 = "13, TRUE, 1, 'f', 3, 4, 5, 6.6, 7.7, 'f8'";
    public static final String INSERT1_VALUES1 = "INSERT INTO TestDataSet VALUES" +
        " " + "(" + VALUES1 + " )";
    public static final String INSERT1_VALUES2 = "INSERT INTO " +
        "ru_otus_l121_dataset_TestDataSet VALUES (" + VALUES1 + " )";
    public static final String INSERT2_VALUES3 = "INSERT INTO " +
        "ru_otus_l121_dataset_UserDataSet VALUES (14, NULL, NULL, " +
        "'java_util_Set ru_otus_l121_dataset_PhoneDataSet' )";

    Adapters testAdapters;
    TestClass testClass;
    TestDataSet testDataSetClass;
    UserDataSet userDataSet;

    @Before
    public void setUp() throws Exception {
        Map<String, Adapter> adapters = new HashMap<>();
        testAdapters = new Adapters(null);
        adapters.put(TypeNames.DEFAULT, new TestDataSetOtusDAO(null));
        UserDataSetOtusDAO dao0 = new UserDataSetOtusDAO(null);
        adapters.put(dao0.getAdapteeOfType(), dao0);
        TestDataSetOtusDAO dao1 = new TestDataSetOtusDAO(null);
        adapters.put(dao1.getAdapteeOfType(), dao1);
        testAdapters.setAdapters(adapters);
        testClass = new TestClass();
        testDataSetClass = new TestDataSet(13);
        userDataSet = new UserDataSet(14);
    }

    @After
    public void tearDown() throws Exception {
        userDataSet = null;
        testDataSetClass = null;
        testClass = null;
        testAdapters = null;
    }

    private void testColumnDescription(String fieldName, String expectedDescription)
        throws ReflectiveOperationException {
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

    public static final String F0_BOOLEAN_DESC = "f0 BOOLEAN NOT NULL";
    @Test
    public void testColumnBooleanDescription() throws Exception {
        testColumnDescription("f0", F0_BOOLEAN_DESC);
    }

    public static final String F1_SMALLINT_DESC = "f1 SMALLINT NOT NULL";
    public static final String F3_SMALLINT_DESC = "f3 SMALLINT NOT NULL";
    @Test
    public void testColumnSmallIntDescription() throws Exception {
        testColumnDescription("f1", F1_SMALLINT_DESC);
        testColumnDescription("f3", F3_SMALLINT_DESC);
    }

    public static final String F2_CHAR_DESC = "f2 CHAR(1) NOT NULL";
    @Test
    public void testColumnCharDescription() throws Exception {
        testColumnDescription("f2", F2_CHAR_DESC);
    }

    public static final String F4_INT_DESC = "f4 INTEGER NOT NULL";
    @Test
    public void testColumnIntDescription() throws Exception {
        testColumnDescription("f4", F4_INT_DESC);
    }

    public static final String F5_LONG_DESC = "f5 BIGINT NOT NULL";
    @Test
    public void testColumnLongDescription() throws Exception {
        testColumnDescription("f5", F5_LONG_DESC);
    }

    public static final String F6_STRING_DESC = "f6 REAL NOT NULL";
    @Test
    public void testColumnStringDescription() throws Exception {
        testColumnDescription("f6", F6_STRING_DESC);
    }

    @Test
    public void testExpressionByFieldFieldFunction() throws Exception {
        Method expressionByField = testAdapters.getClass().getDeclaredMethod(
            "expressionByField", Field.class
        );
        boolean accessibleExpressionByField = expressionByField.isAccessible();
        expressionByField.setAccessible(true);

        Field testFieldFunction = testAdapters.getClass()
            .getDeclaredField("fieldFunction");
        boolean accessibleFieldFunctionExpressionByField = testFieldFunction.isAccessible();
        testFieldFunction.setAccessible(true);

        Function<Field, String> fieldFunction = testAdapters::getColumnDescription;
        testFieldFunction.set(testAdapters, fieldFunction);

        Field f1Field = TestDataSet.class.getField("f1");
        boolean accessibleF1Field = f1Field.isAccessible();

        String testResult = (String) expressionByField.invoke(testAdapters, f1Field);

        f1Field.setAccessible(accessibleF1Field);
        expressionByField.setAccessible(accessibleExpressionByField);
        testFieldFunction.setAccessible(accessibleFieldFunctionExpressionByField);

        Assert.assertEquals(F1_SMALLINT_DESC, testResult);
    }

    @Test
    public void testExpressionByFieldListFieldFunction() throws Exception {
        Method expressionByField = testAdapters.getClass().getDeclaredMethod(
            "expressionByField", Field.class
        );
        boolean accessibleExpressionByField = expressionByField.isAccessible();
        expressionByField.setAccessible(true);

        Field listFieldFunction = testAdapters.getClass()
            .getDeclaredField("listFieldFunction");
        boolean accessibleListFieldFunction = listFieldFunction.isAccessible();
        listFieldFunction.setAccessible(true);

        Function<Field, String> function = (Field field) -> {
            return "Ok";
        };
        listFieldFunction.set(testAdapters, function);

        UserDataSet testClass = new UserDataSet(13);

        Field phones = testClass.getClass().getDeclaredField("phones");
        boolean accessibleFList = phones.isAccessible();

        String testResult = (String) expressionByField.invoke(testAdapters, phones);

        phones.setAccessible(accessibleFList);
        listFieldFunction.setAccessible(accessibleListFieldFunction);
        expressionByField.setAccessible(accessibleExpressionByField);
        Assert.assertEquals("Ok", testResult);
    }

    private <T extends DataSet> String runCreateSQL(
        T o, Function<Field, String> function,
        UnaryOperator<SQLCommand> operator,
        SQLCommand sqlCommand
    ) throws Exception {
        Method constructSQL = testAdapters.getClass().getDeclaredMethod(
            "constructSQL", SQLCommand.class, DataSet.class.getClass()
        );
        boolean accessToCreateSQL = constructSQL.isAccessible();
        constructSQL.setAccessible(true);

        Field fieldFunction = testAdapters.getClass()
            .getDeclaredField("fieldFunction");
        boolean accessToFieldFunction = fieldFunction.isAccessible();
        fieldFunction.setAccessible(true);

        Field indexUnaryOperator = testAdapters.getClass()
            .getDeclaredField("indexUnaryOperator");
        boolean accessToIndexUnaryOperator = indexUnaryOperator.isAccessible();
        indexUnaryOperator.setAccessible(true);

        fieldFunction.set(testAdapters, function);
        indexUnaryOperator.set(testAdapters, operator);
        sqlCommand.openParenthesis();
        sqlCommand = (SQLCommand) constructSQL.invoke(
            testAdapters, sqlCommand, o.getClass()
        );
        sqlCommand.closeParenthesis();

        indexUnaryOperator.setAccessible(accessToIndexUnaryOperator);
        fieldFunction.setAccessible(accessToFieldFunction);
        constructSQL.setAccessible(accessToCreateSQL);

        return sqlCommand.getSql();
    }

    @Test
    public void testCreateSQLDDL() throws Exception {
        TestDataSet testDataSet = new TestDataSet(13);
        SQLCommand sqlCommand = new SQLCommand(Adapters.CREATE_TABLE, "TestDataSet");
        Function<Field, String> function = testAdapters::getColumnDescription;
        UnaryOperator<SQLCommand> operator = testAdapters::primaryKeyDescription;
        String result = runCreateSQL(testDataSet, function, operator, sqlCommand);
        Assert.assertEquals(CREATE_TABLE_EXPECTED1, result);
    }

    @Test
    public void testCreateTablesForTestDataSet() throws Exception {
        TestDataSet testDataSet = new TestDataSet(13);
        String tableName = testAdapters.classGetTableName(testDataSet.getClass());
        SQLCommand sqlCommand = new SQLCommand(Adapters.CREATE_TABLE, tableName);

        Set<String> result = new HashSet<>(
            testAdapters.generateSQLs(
                testDataSet.getClass(), sqlCommand,
                testAdapters::primaryKeyDescription,
                testAdapters::getColumnDescription,
                testAdapters::getTablesForCollection
            )
        );
        Set<String> expected = new HashSet<>();
        expected.add(CREATE_TABLE_EXPECTED2);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testUserDataSetDDL() throws Exception {
        UserDataSet userDataSet = new UserDataSet(13);

        String tableName = testAdapters.classGetTableName(userDataSet.getClass());
        SQLCommand sqlCommand = new SQLCommand(Adapters.CREATE_TABLE, tableName);

        Set<String> result = new HashSet<>(
            testAdapters.generateSQLs(
                userDataSet.getClass(), sqlCommand,
                testAdapters::primaryKeyDescription,
                testAdapters::getColumnDescription,
                testAdapters::getTablesForCollection
            )
        );

        Set<String> expected = new HashSet<>();
        expected.add(CREATE_TABLE_EXPECTED3);
        expected.add(CREATE_TABLE_EXPECTED4);
        expected.add(CREATE_TABLE_EXPECTED5);
        Assert.assertEquals(expected, result);
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
    public void testCreateSQLDML() throws Exception {
        TestDataSet testDataSet = new TestDataSet(13);
        SQLCommand sqlCommand = new SQLCommand(Adapters.INSERT_INTO, "TestDataSet");
        sqlCommand.concat(" VALUES");
        Function<Field, String> function = field -> testAdapters.getValue(field, testDataSet);
        UnaryOperator<SQLCommand> operator = sql -> sql.concat(Long.toString(testDataSet.getId()));
        String result = runCreateSQL(testDataSet, function, operator, sqlCommand);
        Assert.assertEquals(INSERT1_VALUES1, result);
    }

    @Test
    public void testInsertIntoTestDataSet() throws Exception {
        TestDataSet testDataSet = new TestDataSet(13);
        String tableName = testAdapters.classGetTableName(testDataSet.getClass());
        SQLCommand sqlCommand = new SQLCommand(Adapters.INSERT_INTO, tableName);
        sqlCommand.concat(" VALUES");

        Set<String> result = new HashSet<>(
            testAdapters.generateSQLs(
                testDataSet.getClass(), sqlCommand,
                sql -> sql.concat(Long.toString(testDataSet.getId())),
                field -> testAdapters.getValue(field, testDataSet),
                field -> { return null; }
            )
        );
        Set<String> expected = new HashSet<>();
        expected.add(INSERT1_VALUES2);
        Assert.assertEquals(expected, result);
    }


    @Test
    public void testInsertIntoComplex() throws Exception {
        UserDataSet userDataSet = new UserDataSet(14);
        String tableName = testAdapters.classGetTableName(userDataSet.getClass());
        SQLCommand sqlCommand = new SQLCommand(Adapters.INSERT_INTO, tableName);
        sqlCommand.valuesWord();

        Set<String> result = new HashSet<>(
            testAdapters.generateSQLs(
                userDataSet.getClass(), sqlCommand,
                sql -> sql.concat(Long.toString(userDataSet.getId())),
                field -> testAdapters.getValue(field, userDataSet),
                field -> testAdapters.getCollectionValues(field, userDataSet)
            )
        );
        Set<String> expected = new HashSet<>();
        expected.add(INSERT2_VALUES3);
        Assert.assertEquals(expected, result);
    }
}
