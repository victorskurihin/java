package ru.otus.l081;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import java.util.ArrayList;
import java.util.List;

public class ArrayListAdapterTest {
    private ObjectOutputJson oojs;

    @Before
    public void setUp() throws Exception {
        oojs = new ObjectOutputJson();
    }

    @After
    public void tearDown() throws Exception {
        oojs = null;
    }

    static <T> List<T> arrayToList(final T[] array) {
        final List<T> l = new ArrayList<T>(array.length);

        for (final T s : array) {
            l.add(s);
        }
        return l;
    }

    @Test
    public void test() throws IllegalAccessException {
        List<Boolean> booleanList = arrayToList(new Boolean[]{false, true});
        String expectedBooleanList = Json
            .createArrayBuilder()
            .add(false).add(true)
            .build().toString();
        Assert.assertEquals(expectedBooleanList, oojs.toJson(booleanList));

        List<Character> characterList = arrayToList(
            new Character[]{'a', 'b', 'c', 'd'}
        );
        String expectedCharList = Json
            .createArrayBuilder()
            .add("a").add("b")
            .add("c").add("d")
            .build().toString();
        Assert.assertEquals(expectedCharList, oojs.toJson(characterList));

        List<Integer> integerList = arrayToList(
            new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        );
        Assert.assertEquals(
           BuildinArrayAdapterTest.generateJsonArray(),
           oojs.toJson(integerList)
        );

        List<Byte> byteList = arrayToList(
            new Byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        );
        Assert.assertEquals(
            BuildinArrayAdapterTest.generateJsonArray(),
            oojs.toJson(byteList)
        );

        List<Short> shortList = arrayToList(
            new Short[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        );
        Assert.assertEquals(
            BuildinArrayAdapterTest.generateJsonArray(),
            oojs.toJson(shortList)
        );

        List<Long> longList = arrayToList(
            new Long[]{0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L}
        );
        Assert.assertEquals(
            BuildinArrayAdapterTest.generateJsonArray(),
            oojs.toJson(longList)
        );

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