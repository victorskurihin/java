package ru.otus.l141.sort;

import java.util.Arrays;
import java.util.Comparator;

public class SortingJob<T extends Comparable<? super T>> implements Runnable {
    private T[] array;
    private Comparator<T> comparator;

    public SortingJob(T[] subArray, Comparator<T> comparator) {
        this.array = subArray;
        this.comparator = comparator;
    }

    @Override
    public void run() {
        Arrays.sort(array);
    }

    public T[] getArray() {
        return array;
    }
}
