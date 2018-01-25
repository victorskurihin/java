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

public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException {
        Gson gson = new Gson();
        TestSerial ts1 = new TestSerial();
        System.out.println("gson = " + gson.toJson(ts1));
        System.out.println("gson = " + gson.toJson(new EmptyClass()));
        System.out.println("gson = " + gson.toJson(new TestArray()));

        TestSerial ts2 = new TestSerial();
        ObjectOutputJson oojs = new ObjectOutputJson();
        System.out.println("oojs = " + oojs.toJson(ts2));
        System.out.println("oojs = " + oojs.toJson(new EmptyClass()));
        System.out.println("oojs = " + oojs.toJson(new TestArray()));
    }
}
