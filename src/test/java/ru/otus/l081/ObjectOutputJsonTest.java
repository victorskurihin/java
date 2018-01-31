package ru.otus.l081;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjectOutputJsonTest {
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
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF