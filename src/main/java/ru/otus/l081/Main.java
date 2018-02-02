package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import java.util.*;

/**
 * Created by VSkurikhin.
 *
 * Solution for L08.1
 *
 * To start:
 * mvn clean compile
 *
 * ./run.sh
 * or
 * run.bat

 Set<Integer> expectedIntegerSet = arrayToSet(
 new Integer[]{0, 1, 2, 3}
 );
 Type integerSetType = new TypeToken<Set<Integer>>() {}.getType();
 Set<Integer> targetIntegerSet = gson.fromJson(
 oojs.toJson(expectedIntegerSet), integerSetType
 );

 */


public class Main {
    public static void main(String[] args) throws Exception {
        ObjectOutputJson oojs = new ObjectOutputJson();
        TypeToken<?> tt = new TypeToken<HashSet<String>>() {};
        Set<String> set = oojs.fromJson("[\"a\", \"b\"]", tt);

        for (String e: set) {
            System.out.print(" " + e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF