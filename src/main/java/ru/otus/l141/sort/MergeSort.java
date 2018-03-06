package ru.otus.l141.sort;

import java.util.Arrays;
import java.util.Comparator;

public class MergeSort<T extends Comparable<? super T>> {
    private Comparator<T> comparator = Comparator.naturalOrder();

    public MergeSort() { /* None */ }
    public MergeSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(T[] array) {
        mergeSort(array, 0, array.length-1);
    }

    private boolean indexOutOfBounds(T[] array, int index) {
        return index < 0 || index >= array.length;
    }

    public void sort(T[] array, int from, int to) {
        if (indexOutOfBounds(array, from) || indexOutOfBounds(array, to)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        mergeSort(array, from, to);
    }

    void mergeSort(T[] array, int from, int to) {
        if (from < to) {
            int mid = from + Math.floorDiv(to-from, 2);
            mergeSort(array, from, mid);
            mergeSort(array, mid+1, to);
            merge(array, from, mid, to);
        }
    }

    void merge(T[] array, int from, int mid, int to) {
        T[] left = Arrays.copyOfRange(array, from, mid+1);
        T[] right = Arrays.copyOfRange(array, mid+1, to+1);

        int li = 0, ri = 0;
        while (li < left.length && ri < right.length) {
            if (comparator.compare(left[li], right[ri]) < 0) {
                array[from++] = left[li++];
            } else {
                array[from++] = right[ri++];
            }
        }

        while (li < left.length) {
            array[from++] = left[li++];
        }

        while (ri < right.length) {
            array[from++] = right[ri++];
        }
    }
}
