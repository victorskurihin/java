package ru.otus.l081;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

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
    public Integer integer = 0;
    public EmptyClass emptyClass = new EmptyClass();
    public TestSerial testSerial = new TestSerial();
    public TestArray testArray = new TestArray();
    public TestString testString = new TestString();
}

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Type listType = new TypeToken<List<String>>() {}.getType();
        Map<String, Integer> targetMap = new TreeMap();
        targetMap.put("one", 0);
        targetMap.put("two", 1);

        TestArray targetTestArray = new TestArray();
        TestComplex targetTestComplex = new TestComplex();

        System.out.println("listType.getTypeName() = " + listType.getTypeName());

        Gson gson = new Gson();
        System.out.println("gson = " + gson.toJson(null));
        System.out.println("gson = " + gson.toJson(targetTestArray));
        System.out.println("gson = " + gson.toJson(targetTestComplex));
        System.out.println("gson = " + gson.toJson(targetMap));

        ObjectOutputJson oojs = new ObjectOutputJson();
        System.out.println("oojs = " + oojs.toJson(null));
        System.out.println("oojs = " + oojs.toJson(targetTestArray));
        System.out.println("oojs = " + oojs.toJson(targetTestComplex));
        System.out.println("oojs = " + oojs.toJson(targetMap));
    }
}
