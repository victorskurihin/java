package ru.otus.l081;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetAdapterTest {
    ObjectOutputJson oojs;

    @Before
    public void setUp() throws Exception {
        oojs = new ObjectOutputJson();
    }

    @After
    public void tearDown() throws Exception {
        oojs = null;
    }

    static <T> Set<T> arrayToSet(final T[] array) {
        final Set<T> s = new TreeSet<>();

        for (final T e : array) {
            s.add(e);
        }
        return (s);
    }

    @Test
    public void test() throws IllegalAccessException {
        Set<Character> characterSet = arrayToSet(
            new Character[]{'a', 'b', 'c', 'd'}
        );
        String expectedCharSet = Json
            .createArrayBuilder()
            .add("a").add("b")
            .add("c").add("d")
            .build().toString();
        Assert.assertEquals(expectedCharSet, oojs.toJson(characterSet));

        Set<Integer> integerSet = arrayToSet(
            new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        );
        Assert.assertEquals(
            BuildinArrayAdapterTest.generateJsonArray(),
            oojs.toJson(integerSet)
        );

        Set<Byte> byteSet = arrayToSet(
            new Byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        );
        Assert.assertEquals(
            BuildinArrayAdapterTest.generateJsonArray(),
            oojs.toJson(byteSet)
        );

        Set<Short> shortSet = arrayToSet(
            new Short[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
        );
        Assert.assertEquals(
            BuildinArrayAdapterTest.generateJsonArray(),
            oojs.toJson(shortSet)
        );

        Set<Long> longSet = arrayToSet(
            new Long[]{0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L}
        );
        Assert.assertEquals(
            BuildinArrayAdapterTest.generateJsonArray(),
            oojs.toJson(longSet)
        );

        Set<String> stringSet = arrayToSet(
            new String[]{"a1", "a2", "a3"}
        );
        String expectedStringSet = Json
            .createArrayBuilder()
            .add("a1").add("a2").add("a3")
            .build().toString();
        Assert.assertEquals(expectedStringSet, oojs.toJson(stringSet));
    }
}