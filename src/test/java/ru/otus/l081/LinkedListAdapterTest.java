package ru.otus.l081;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class LinkedListAdapterTest {
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

    static <T> List<T> arrayToList(final T[] array) {
        final List<T> l = new LinkedList<>();

        for (final T s : array) {
            l.add(s);
        }
        return l;
    }

    @Test
    public void test() throws IllegalAccessException {
        List<Integer> expectedIntegerList = arrayToList(
            new Integer[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5}
        );
        Type integerListType = new TypeToken<List<Integer>>() {}.getType();
        List<Integer> targetIntegerList = gson.fromJson(
            oojs.toJson(expectedIntegerList), integerListType
        );
        Assert.assertEquals(expectedIntegerList, targetIntegerList);

        List<Character> expectedCharacterList = arrayToList(
            new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'}
        );
        Type characterListType = new TypeToken<List<Character>>() {}.getType();
        List<Character> targetCharacterList = gson.fromJson(
            oojs.toJson(expectedCharacterList), characterListType
        );
        Assert.assertEquals(expectedCharacterList, targetCharacterList);

        List<FieldClassTest> expectedFieldClassTestList = arrayToList(
            new FieldClassTest[]{new FieldClassTest(), null, new FieldClassTest()}
        );
        Type fieldClassTestListType = new TypeToken<List<FieldClassTest>>() {}.getType();
        List<FieldClassTest> targetFieldClassTestList = gson.fromJson(
            oojs.toJson(expectedFieldClassTestList), fieldClassTestListType
        );
        Assert.assertEquals(expectedFieldClassTestList, targetFieldClassTestList);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF