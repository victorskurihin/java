package ru.otus.l081;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import java.lang.reflect.Field;

public class ObjectOutputJsonTest extends ObjectOutputJson {
    public static final String TEST_PACKAGE_FIELD = "testPackageField";
    public static final String TEST_PRIVATE_FIELD = "testPrivateField";
    public static final String TEST_PROTECTED_FIELD = "testProtectedField";
    public static final String TEST_PUBLIC_FIELD = "testPublicField";
    ObjectOutputJson oojs;

    @Before
    public void setUp() throws Exception {
        oojs = new ObjectOutputJson();
    }

    @After
    public void tearDown() throws Exception {
        oojs = null;
    }

    private void longFieldTest(Object ifct, String fieldName, long value)
    throws NoSuchFieldException, IllegalAccessException {
        Field packageField = ifct.getClass().getDeclaredField(fieldName);
        boolean accessible = packageField.isAccessible();
        packageField.setAccessible(true);
        JsonObjectBuilder testPackageJob = addFiled(
            Json.createObjectBuilder(), ifct, packageField
        );
        packageField.setAccessible(accessible);

        JsonObject expectedPackageJo = Json
            .createObjectBuilder()
            .add(fieldName, value)
            .build();
        Assert.assertEquals(expectedPackageJo, testPackageJob.build());
    }

    private void booleanFieldTest (Object ifct, String fieldName, boolean value)
    throws NoSuchFieldException, IllegalAccessException {
        Field packageField = ifct.getClass().getDeclaredField(fieldName);
        boolean accessible = packageField.isAccessible();
        packageField.setAccessible(true);
        JsonObjectBuilder testPackageJob = addFiled(
            Json.createObjectBuilder(), ifct, packageField
        );
        packageField.setAccessible(accessible);

        JsonObject expectedPackageJo = Json
            .createObjectBuilder()
            .add(fieldName, value)
            .build();
        Assert.assertEquals(expectedPackageJo, testPackageJob.build());
    }

    private void booleanFieldTest (Object ifct, String fieldName)
    throws NoSuchFieldException, IllegalAccessException {
        booleanFieldTest(ifct, fieldName, false);
    }

    @Test
    public void addFiledIntCharLongTest() throws NoSuchFieldException, IllegalAccessException {
        longFieldTest(new IntFieldDefaultClassTest(), TEST_PACKAGE_FIELD, 0);
        longFieldTest(new IntFieldDefaultClassTest(), TEST_PRIVATE_FIELD, 0);
        longFieldTest(new IntFieldDefaultClassTest(), TEST_PROTECTED_FIELD, 0);
        longFieldTest(new IntFieldDefaultClassTest(), TEST_PUBLIC_FIELD, 0);

        longFieldTest(new IntFieldValuesClassTest(), TEST_PACKAGE_FIELD, Integer.MIN_VALUE);
        longFieldTest(new IntFieldValuesClassTest(), TEST_PUBLIC_FIELD, Integer.MAX_VALUE);

        longFieldTest(new CharFieldValuesClassTest(), TEST_PACKAGE_FIELD, Character.MIN_VALUE);
        longFieldTest(new CharFieldValuesClassTest(), TEST_PUBLIC_FIELD, Character.MAX_VALUE);

        longFieldTest(new LongFieldValuesClassTest(), TEST_PACKAGE_FIELD, Long.MIN_VALUE);
        longFieldTest(new LongFieldValuesClassTest(), TEST_PUBLIC_FIELD, Long.MAX_VALUE);

        booleanFieldTest(new BooleanFieldValuesClassTest(), TEST_PACKAGE_FIELD);
        booleanFieldTest(new BooleanFieldValuesClassTest(), TEST_PUBLIC_FIELD, true);
    }
}