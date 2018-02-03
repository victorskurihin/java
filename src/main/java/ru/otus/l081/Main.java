package ru.otus.l081;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

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

class Inside {
    public int inside;

    @Override
    public String toString() {
        return "Inside{" +
            "inside=" + inside +
            '}';
    }
}

class Test {
    boolean myBooleanField;
    private byte myByteField;
    protected char myCharField = 'a';
    public int myIntField;

    public Inside inside;

    int[] ints = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    String str = "text";

    @Override
    public String toString() {
        //noinspection ConstantConditions
        return super.toString() + ": Test{" +
            "myBooleanField=" + myBooleanField +
            ", myByteField=" + myByteField +
            ", myCharField=" + myCharField +
            ", myIntField=" + myIntField +
            ", inside={" + inside.toString() +
            ", ints=[" +
            Arrays.stream(ints)
                .mapToObj(Integer::toString)
                .reduce(
                    (s1, s2) -> s1.concat(",").concat(s2)
                ).get() +
            "], \"str\" : " + str + "}}";
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        ObjectOutputJson oojs = new ObjectOutputJson();
        System.out.println("oojs.toJson() = " + oojs.toJson(new Test()));
        System.out.println("gson.toJson() = " + gson.toJson(new Test()));
        TypeToken<?> tt = new TypeToken<Test>() {};
        String json = "{ \"myBooleanField\" : true, " +
            "\"myByteField\" : 1 ,\"myCharField\" : \"a\", " +
            "\"myIntField\" : 3, \"inside\" : { \"inside\" : 13 }, " +
            "\"ints\": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10], " +
            "\"str\" : \"tezd\" }";
        Test t = oojs.fromJson(json, tt);

        System.out.println("t = " + t);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF