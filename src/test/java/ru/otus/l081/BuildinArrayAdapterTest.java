package ru.otus.l081;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArrayBuilder;

public class BuildinArrayAdapterTest {
    public static final String TEST_PRIVATE_ARRAY_FIELD = "testPrivateArrayField";
    public static final String TEST_PUBLIC_ARRAY_FIELD = "testPublicArrayField";
    public static final String TEST_PACKAGE_FIELD = "testPackageField";

    private ObjectOutputJson oojs;

    @Before
    public void setUp() throws Exception {
        oojs = new ObjectOutputJson();
    }

    @After
    public void tearDown() throws Exception {
        oojs = null;
    }

    public static String generateJsonArray() {
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

        Object[] objects = new Object[]{null, new Object()};

        String expectedObjects  = Json
            .createArrayBuilder()
            .addNull().add(Json.createObjectBuilder())
            .build().toString();
        Assert.assertEquals(expectedObjects, oojs.toJson(objects));
    }

    @Test
    public void jsonFieldArrayTest() throws IllegalAccessException {
        JsonArrayBuilder jab1 = Json.createArrayBuilder();
        JsonArrayBuilder jab2 = Json.createArrayBuilder();

        for (int i = 0; i < 10; i++) {
            jab1.add(i);
            jab2.add(i);
        }
        String expectedIntFieldArrayClass = Json
            .createObjectBuilder()
            .add(TEST_PRIVATE_ARRAY_FIELD, jab1)
            .add(TEST_PUBLIC_ARRAY_FIELD, jab2)
            .build().toString();
        Assert.assertEquals(
            expectedIntFieldArrayClass, oojs.toJson(new IntsFieldArrayClassTest())
        );

        ObjectsFieldArrayClassTest source = new ObjectsFieldArrayClassTest();
        String sourceString = oojs.toJson(source);

        String expectedString = Json
            .createObjectBuilder()
            .add(TEST_PRIVATE_ARRAY_FIELD, Json.createArrayBuilder()
                .addNull().add(
                    Json.createObjectBuilder()
                        .add(TEST_PACKAGE_FIELD, 0)))
            .build().toString();
        Assert.assertEquals(expectedString, sourceString);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF