package ru.otus.l081;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
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

    @Test
    public void testPrimitiveToJson() throws IllegalAccessException {
        String expectedBooleanTrue = Boolean.toString(true);
        String expectedBooleanFalse = Boolean.toString(false);
        Assert.assertEquals(expectedBooleanFalse, oojs.toJson(false));
        Assert.assertEquals(expectedBooleanTrue, oojs.toJson(true));

        String expectedCharSpace = "\" \"";
        String expectedCharA = "\"a\"";
        Assert.assertEquals(expectedCharSpace, oojs.toJson(' '));
        Assert.assertEquals(expectedCharA, oojs.toJson('a'));

        String expectedIntZero = Integer.toString(0);
        Assert.assertEquals(expectedIntZero, oojs.toJson(0));

        String expectedByteZero = expectedIntZero;
        Assert.assertEquals(expectedByteZero, oojs.toJson( (byte) 0));

        String expectedShortZero = expectedIntZero;
        Assert.assertEquals(expectedShortZero, oojs.toJson( (short) 0));

        String expectedLongZero = expectedIntZero;
        Assert.assertEquals(expectedLongZero, oojs.toJson(0L));

        String expectedDoubleZero = Double.toString(0.0);
        String expectedFloatZero = expectedDoubleZero;

        Assert.assertEquals(expectedDoubleZero, oojs.toJson(0.0));
        Assert.assertEquals(expectedFloatZero, oojs.toJson( (float) 0));
    }

    @Test
    public void testBoxingTypeToJson() throws IllegalAccessException {
        String expectedBooleanTrue = Boolean.toString(true);
        String expectedBooleanFalse = Boolean.toString(false);
        Boolean falseTest = new Boolean(false);
        Boolean trueTest = new Boolean(true);
        Assert.assertEquals(
                expectedBooleanFalse, oojs.toJson(falseTest.getClass(), falseTest)
        );
        Assert.assertEquals(
                expectedBooleanTrue, oojs.toJson(trueTest.getClass(), trueTest)
        );
        Assert.assertEquals(expectedBooleanFalse, oojs.toJson(falseTest));
        Assert.assertEquals(expectedBooleanTrue, oojs.toJson(trueTest));

        String expectedCharSpace = "\" \"";
        String expectedCharA = "\"a\"";
        Character testSpace = new Character(' ');
        Character testA = new Character('a');
        Assert.assertEquals(
                expectedCharSpace, oojs.toJson(testSpace.getClass(), testSpace)
        );
        Assert.assertEquals(
                expectedCharA, oojs.toJson(testA.getClass(), testA)
        );
        Assert.assertEquals(expectedCharSpace, oojs.toJson(testSpace));
        Assert.assertEquals(expectedCharA, oojs.toJson(testA));

        String expectedIntZero = Integer.toString(0);
        String expectedIntMin = Integer.toString(Integer.MIN_VALUE);
        String expectedIntMax = Integer.toString(Integer.MAX_VALUE);
        Integer integerZero = 0;
        Integer integerMin = Integer.MIN_VALUE;
        Integer integerMax = Integer.MAX_VALUE;
        Assert.assertEquals(
                expectedIntZero, oojs.toJson(integerZero.getClass(), integerZero)
        );
        Assert.assertEquals(
                expectedIntMin, oojs.toJson(integerMin.getClass(), integerMin)
        );
        Assert.assertEquals(
                expectedIntMax, oojs.toJson(integerMax.getClass(), integerMax)
        );
        Assert.assertEquals(expectedIntZero, oojs.toJson(integerZero));
        Assert.assertEquals(expectedIntMin, oojs.toJson(integerMin));
        Assert.assertEquals(expectedIntMax, oojs.toJson(integerMax));


        String expectedByteZero = expectedIntZero;
        String expectedByteMin = Byte.toString(Byte.MIN_VALUE);
        String expectedByteMax = Byte.toString(Byte.MAX_VALUE);
        Byte byteZero = (byte) 0;
        Byte byteMin = Byte.MIN_VALUE;
        Byte byteMax = Byte.MAX_VALUE;
        Assert.assertEquals(
                expectedByteZero, oojs.toJson(byteZero.getClass(), byteZero)
        );
        Assert.assertEquals(
                expectedByteMin, oojs.toJson(byteMin.getClass(), byteMin)
        );
        Assert.assertEquals(
                expectedByteMax, oojs.toJson(byteMax.getClass(), byteMax)
        );
        Assert.assertEquals(expectedByteZero, oojs.toJson(byteZero));
        Assert.assertEquals(expectedByteMin, oojs.toJson(byteMin));
        Assert.assertEquals(expectedByteMax, oojs.toJson(byteMax));

        String expectedShortZero = expectedIntZero;
        String expectedShortMin = Short.toString(Short.MIN_VALUE);
        String expectedShortMax = Short.toString(Short.MAX_VALUE);
        Short shortZero = (short) 0;
        Short shortMin = Short.MIN_VALUE;
        Short shortMax = Short.MAX_VALUE;
        Assert.assertEquals(
                expectedShortZero, oojs.toJson(shortZero.getClass(), shortZero)
        );
        Assert.assertEquals(
                expectedShortMin, oojs.toJson(shortMin.getClass(), shortMin)
        );
        Assert.assertEquals(
                expectedShortMax, oojs.toJson(shortMax.getClass(), shortMax)
        );
        Assert.assertEquals(expectedShortZero, oojs.toJson(shortZero));
        Assert.assertEquals(expectedShortMin, oojs.toJson(shortMin));
        Assert.assertEquals(expectedShortMax, oojs.toJson(shortMax));

        String expectedLongZero = expectedIntZero;
        String expectedLongMin = Long.toString(Long.MIN_VALUE);
        String expectedLongMax = Long.toString(Long.MAX_VALUE);
        Long longZero = 0L;
        Long longMin = Long.MIN_VALUE;
        Long longMax = Long.MAX_VALUE;
        Assert.assertEquals(
                expectedLongZero, oojs.toJson(longZero.getClass(), longZero)
        );
        Assert.assertEquals(
                expectedLongMin, oojs.toJson(longMin.getClass(), longMin)
        );
        Assert.assertEquals(
                expectedLongMax, oojs.toJson(longMax.getClass(), longMax)
        );
        Assert.assertEquals(expectedLongZero, oojs.toJson(longZero));
        Assert.assertEquals(expectedLongMin, oojs.toJson(longMin));
        Assert.assertEquals(expectedLongMax, oojs.toJson(longMax));

        String expectedFloatZero = Float.toString(0);
        String expectedFloatMin = Float.toString(Float.MIN_VALUE);
        String expectedFloatMax = Float.toString(Float.MAX_VALUE);
        Float floatZero = (float) 0;
        Float floatMin = Float.MIN_VALUE;
        Float floatMax = Float.MAX_VALUE;
        Assert.assertEquals(
                expectedFloatZero, oojs.toJson(floatZero.getClass(), floatZero)
        );
        Assert.assertEquals(
                expectedFloatMin, oojs.toJson(floatMin.getClass(), floatMin)
        );
        Assert.assertEquals(
                expectedFloatMax, oojs.toJson(floatMax.getClass(), floatMax)
        );
        Assert.assertEquals(expectedFloatZero, oojs.toJson(floatZero));
        Assert.assertEquals(expectedFloatMin, oojs.toJson(floatMin));
        Assert.assertEquals(expectedFloatMax, oojs.toJson(floatMax));

        String expectedDoubleZero = Double.toString(0);
        String expectedDoubleMin = Double.toString(Double.MIN_VALUE);
        String expectedDoubleMax = Double.toString(Double.MAX_VALUE);
        Double doubleZero = 0.0;
        Double doubleMin = Double.MIN_VALUE;
        Double doubleMax = Double.MAX_VALUE;
        Assert.assertEquals(
                expectedDoubleZero, oojs.toJson(doubleZero.getClass(), doubleZero)
        );
        Assert.assertEquals(
                expectedDoubleMin, oojs.toJson(doubleMin.getClass(), doubleMin)
        );
        Assert.assertEquals(
                expectedDoubleMax, oojs.toJson(doubleMax.getClass(), doubleMax)
        );
        Assert.assertEquals(expectedDoubleZero, oojs.toJson(doubleZero));
        Assert.assertEquals(expectedDoubleMin, oojs.toJson(doubleMin));
        Assert.assertEquals(expectedDoubleMax, oojs.toJson(doubleMax));
    }

    private String generateJsonArray() {
        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (int i = 0; i < 10; i++) {
            jab.add(i);

        }

        return jab.build().toString();
    }

    @Test
    public void jsonArrayTest() throws IllegalAccessException {
        String expectedBooleanArray = Json
                .createArrayBuilder()
                .add(false).add(true)
                .build().toString();
        Assert.assertEquals(
                expectedBooleanArray, oojs.toJson(new boolean[]{false, true})
        );

        String expectedCharArray = Json
                .createArrayBuilder()
                .add('a').add('b')
                .add('c').add('d')
                .build().toString();
        Assert.assertEquals(
                expectedCharArray, oojs.toJson(new char[]{'a', 'b', 'c', 'd'})
        );

        Assert.assertEquals(
                generateJsonArray(), oojs.toJson(new int[]{0,1,2,3,4,5,6,7,8,9})
        );
        Assert.assertEquals(
                generateJsonArray(), oojs.toJson(new byte[]{0,1,2,3,4,5,6,7,8,9})
        );
        Assert.assertEquals(
                generateJsonArray(), oojs.toJson(new short[]{0,1,2,3,4,5,6,7,8,9})
        );
        Assert.assertEquals(
                generateJsonArray(), oojs.toJson(new long[]{0,1,2,3,4,5,6,7,8,9})
        );

        String expectedDoubleArray = Json
                .createArrayBuilder()
                .add(0.0).add(0.1).add(0.2)
                .build().toString();
        Assert.assertEquals(
                expectedDoubleArray, oojs.toJson(new double[]{0, 0.1, 0.2})
        );
    }

    private JsonObjectBuilder fieldTest(Object ifct, String fieldName)
    throws IllegalAccessException, NoSuchFieldException {
        Field packageField = ifct.getClass().getDeclaredField(fieldName);
        boolean accessible = packageField.isAccessible();
        packageField.setAccessible(true);

        JsonObjectBuilder testPackageJob = addFiled(
                Json.createObjectBuilder(), ifct, packageField
        );
        packageField.setAccessible(accessible);

        return testPackageJob;
    }

    private void longFieldTest(Object ifct, String fieldName, long value)
    throws NoSuchFieldException, IllegalAccessException {

        JsonObject expectedLongFieldTest = Json
            .createObjectBuilder()
            .add(fieldName, value)
            .build();
        Assert.assertEquals(
                expectedLongFieldTest, fieldTest(ifct, fieldName).build()
        );
    }

    private void booleanFieldTest (Object ifct, String fieldName, boolean value)
    throws NoSuchFieldException, IllegalAccessException {

        JsonObject expectedBooleanFieldTest = Json
            .createObjectBuilder()
            .add(fieldName, value)
            .build();
        Assert.assertEquals(
                expectedBooleanFieldTest, fieldTest(ifct, fieldName).build()
        );
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
