package ru.otus.l141.sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * This is the service class for instances of the parallel sorting job.
 *
 * @param <T> the type of elements in the target array, must be Comparable.
 */
public class MergeSortingJob<T extends Comparable<? super T>> implements Runnable {
    private int from;
    private int to;
    private T[] array;
    private MergeSort<T> mergeSort;

    public MergeSortingJob(T[] subArray, int from, int to, Comparator<T> comparator) {
        this.from = from;
        this.to = to;
        this.array = subArray;
        this.mergeSort = new MergeSort<>(comparator);
        Arrays.sort(array);
    }

    @Override
    public void run() {
        mergeSort.mergeSort(array, from, to);
    }

    public T[] getArray() {
        return array;
    }
}
