package ru.otus.l081;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class HashSetAdapterTest {
    Gson gson;
    ObjectOutputJson oojs;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        oojs = new ObjectOutputJson();
    }

    @After
    public void tearDown() throws Exception {
        oojs = null;
        gson = null;
    }

    static <T> Set<T> arrayToSet(final T[] array) {
        final Set<T> l = new HashSet<>();

        for (final T s : array) {
            l.add(s);
        }
        return (l);
    }

    @Test
    public void test() throws IllegalAccessException {
        Set<Integer> expectedIntegerSet = arrayToSet(
            new Integer[]{0, 1, 2, 3}
        );
        Type integerSetType = new TypeToken<Set<Integer>>() {}.getType();
        Set<Integer> targetIntegerSet = gson.fromJson(
            oojs.toJson(expectedIntegerSet), integerSetType
        );
        Assert.assertEquals(expectedIntegerSet, targetIntegerSet);

        Set<String> expectedStringSet = arrayToSet(
            new String[]{"one", "two", "three"}
        );
        Type stringSetType = new TypeToken<Set<String>>() {}.getType();
        Set<String> targetStringSet = gson.fromJson(
            oojs.toJson(expectedStringSet), stringSetType
        );
        Assert.assertEquals(expectedStringSet, targetStringSet);
    }
}