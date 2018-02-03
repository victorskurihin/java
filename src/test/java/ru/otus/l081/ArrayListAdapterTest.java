package ru.otus.l081;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListAdapterTest {
    private ObjectOutputJson oojs;

    @Before
    public void setUp() {
        oojs = new ObjectOutputJson();
    }

    @After
    public void tearDown() {
        oojs = null;
    }

    private static <T> List<T> arrayToList(final T[] array) {
        final List<T> l = new ArrayList<>(array.length);
        l.addAll(Arrays.asList(array));
        return l;
    }

    @Test
    public void testBooleanList() throws IllegalAccessException {
        List<Boolean> booleanList = arrayToList(new Boolean[]{false, true});
        String expectedBooleanList = Json
            .createArrayBuilder()
            .add(false).add(true)
            .build().toString();
        Assert.assertEquals(expectedBooleanList, oojs.toJson(booleanList));
    }

    @Test
    public void testCharacterList() throws IllegalAccessException {
        List<Character> characterList = arrayToList(
            new Character[]{'a', 'b', 'c', 'd'}
        );
        String expectedCharList = Json
            .createArrayBuilder()
            .add("a").add("b")
            .add("c").add("d")
            .build().toString();
        Assert.assertEquals(expectedCharList, oojs.toJson(characterList));
    }

    @Test
    public void testIntegerList() throws IllegalAccessException {
        List<Integer> integerList = arrayToList(
            new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        );
        Assert.assertEquals(
            BuildinArrayAdapterTest.generateJsonArray(),
            oojs.toJson(integerList)
        );
    }

    @Test
    public void testByteList() throws IllegalAccessException {
        List<Byte> byteList = arrayToList(
            new Byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        );
        Assert.assertEquals(
            BuildinArrayAdapterTest.generateJsonArray(),
            oojs.toJson(byteList)
        );
    }

    @Test
    public void testShortList() throws IllegalAccessException {
        List<Short> shortList = arrayToList(
            new Short[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        );
        Assert.assertEquals(
            BuildinArrayAdapterTest.generateJsonArray(),
            oojs.toJson(shortList)
        );
    }

    @Test
    public void testLongList() throws IllegalAccessException {
        List<Long> longList = arrayToList(
            new Long[]{0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L}
        );
        Assert.assertEquals(
            BuildinArrayAdapterTest.generateJsonArray(),
            oojs.toJson(longList)
        );
    }

    @Test
    public void testStringList() throws IllegalAccessException {
        List<String> stringList = arrayToList(
            new String[]{"one", "two", "three"}
        );
        String expectedStringList = Json
            .createArrayBuilder()
            .add("one").add("two").add("three")
            .build().toString();
        Assert.assertEquals(expectedStringList, oojs.toJson(stringList));
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF