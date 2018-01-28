package ru.otus.l081;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapAdapterTest {
    ObjectOutputJson oojs;

    @Before
    public void setUp() throws Exception {
        oojs = new ObjectOutputJson();
    }

    @After
    public void tearDown() throws Exception {
        oojs = null;
    }

    static <K, V> Map<K, V> arrayToMap(final K[] keys, final V[] values) {
        final Map<K, V> t = new TreeMap<>();

        if (keys.length == values.length ) {
            for (int i = 0; i < keys.length; i++) {
                t.put(keys[i], values[i]);
            }
        }
        return t;
    }

    @Test
    public void test() throws IllegalAccessException {
        Map<Character, Integer> characterMap = arrayToMap(
            new Character[]{'a', 'b', 'c', 'd'},
            new Integer[]  { 0,   1,   2,   3}
        );
        String expectedCharMap = Json
            .createObjectBuilder()
            .add("a", 0)
            .add("b", 1)
            .add("c", 2)
            .add("d", 3)
            .build().toString();
        Assert.assertEquals(expectedCharMap, oojs.toJson(characterMap));

        Map<Integer, FieldClassTest> integerMap = arrayToMap(
            new Integer[]{0, 1, 3},
            new FieldClassTest[]{new FieldClassTest(), null, new FieldClassTest()}
        );
        String expectedIntegerMap = Json
            .createObjectBuilder()
            .add("0",
                Json.createObjectBuilder()
                    .add("testPackageField", 0)
            ).add("3",
                Json.createObjectBuilder()
                    .add("testPackageField", 0)
            ).build().toString();
        Assert.assertEquals(expectedIntegerMap, oojs.toJson(integerMap));
    }
}