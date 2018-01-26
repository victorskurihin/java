package ru.otus.l081;

import com.google.gson.Gson;

import java.io.*;

class EmptyClass {}

class TestSerial implements Serializable {
    public byte version = 100;
    public byte count = 0;
}

class TestArray {
    public int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
}

class TestString {
    public String string = "test";
}

class TestComplex {
    public EmptyClass emptyClass;
    public TestSerial testSerial;
    public TestArray testArray;
    public TestString testString;
}

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Gson gson = new Gson();
        TestSerial ts1 = new TestSerial();
        System.out.println("gson = " + gson.toJson(1));
        System.out.println("gson = " + gson.toJson("abc"));
        System.out.println("gson = " + gson.toJson(ts1));
        System.out.println("gson = " + gson.toJson(new EmptyClass()));
        System.out.println("gson = " + gson.toJson(new TestArray()));
        System.out.println("gson = " + gson.toJson(new TestString()));
        System.out.println("gson = " + gson.toJson(new TestComplex()));
        System.out.println("gson = " + gson.toJson(new Boolean(true)));
        System.out.println("gson = " + gson.toJson(new Character('a')));
        int[] values = { 1 };
        System.out.println("gson = " + gson.toJson(values));


        TestSerial ts2 = new TestSerial();
        ObjectOutputJson oojs = new ObjectOutputJson();
        System.out.println("oojs = " + oojs.toJson(2));
        System.out.println("oojs = " + oojs.toJson("abc"));
        System.out.println("oojs = " + oojs.toJson(ts2));
        System.out.println("oojs = " + oojs.toJson(new EmptyClass()));
        System.out.println("oojs = " + oojs.toJson(new TestArray()));
        System.out.println("oojs = " + oojs.toJson(new TestString()));
        System.out.println("oojs = " + oojs.toJson(new TestComplex()));
        System.out.println("oojs = " + oojs.toJson(new Boolean(true)));
        System.out.println("oojs = " + oojs.toJson(new Character('b')));
        System.out.println("oojs = " + oojs.toJson(values));
    }
}
