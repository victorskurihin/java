package ru.otus.l161.app;

import java.util.Random;

public class RandomUnsignedInt {
    public static int get() {
        Random random = new Random();
        return (int) (random.nextLong() & 0x7fffffffL);
    }
}
