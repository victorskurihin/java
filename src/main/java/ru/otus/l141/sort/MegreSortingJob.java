package ru.otus.l141.sort;

import java.util.Arrays;
import java.util.Comparator;

public class MegreSortingJob<T extends Comparable<? super T>> implements Runnable {
    private int from;
    private int to;
    private T[] array;
    private MergeSort<T> mergeSort;

    public MegreSortingJob(T[] subArray, int from, int to, Comparator<T> comparator) {
        this.from = from;
        this.to = to;
        this.array = subArray;
        this.mergeSort = new MergeSort<>(comparator);
    }

    @Override
    public void run() {
        mergeSort.mergeSort(array, from, to);
    }

    public T[] getArray() {
        return array;
    }
}
