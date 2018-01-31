package ru.otus.l081;

import com.google.common.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
 */


public class Main {
    public static void main(String[] args) throws Exception {
        Map<String, Integer> map = new TreeMap<>();
        map.put("key1", 1);
        map.put("key2", 2);

        ObjectOutputJson oojs = new ObjectOutputJson();
        System.out.println("oojs.toJson(map) = " + oojs.toJson(map));

        TypeToken<?> tt = new TypeToken<ArrayList<Character>>() {};
        List<Character> list = oojs.fromJson("[\"a\", \"b\"]", tt);

        for (Character e: list) {
            System.out.print(" " + e);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF