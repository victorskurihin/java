package ru.otus.l141.sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The algorithm belongs to  the group  of Divide and Conquer family of
 * algorithms.
 * This basically means that it divides the sorting problem into smaller
 * parts to solve.
 *
 * @param <T> the type of elements in the target array, must be Comparable.
 */
public class MergeSort<T extends Comparable<? super T>> {

    // Decides when to fork or compute directly:
    private static final int SORT_THRESHOLD = 16;

    private Comparator<T> comparator = Comparator.naturalOrder();

    public MergeSort() { /* None */ }

    /**
     * TODO comments
     *
     * @param comparator
     */
    public MergeSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * TODO comments
     *
     * @param array
     */
    public void sort(T[] array) {
        mergeSort(array, 0, array.length-1);
    }

    private boolean indexOutOfBounds(T[] array, int index) {
        return index < 0 || index >= array.length;
    }

    /**
     * TODO comments
     *
     * @param array
     * @param from
     * @param to
     */
    public void sort(T[] array, int from, int to) {
        if (indexOutOfBounds(array, from) || indexOutOfBounds(array, to)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        mergeSort(array, from, to);
    }

    private void insertionSort(T[] array, int from, int to) {
        for (int i = from+1; i <= to; ++i) {
            T current = array[i];
            int j = i-1;
            while (from <= j && comparator.compare(current, array[j]) < 0) {
                array[j+1] = array[j--];
            }
            array[j+1] = current;
        }
    }

    void mergeSort(T[] array, int from, int to) {
        if (from < to) {
            int size = to - from;
            if (size < SORT_THRESHOLD) {
                insertionSort(array, from, to);
            } else {
                int mid = from + Math.floorDiv(to - from, 2);
                mergeSort(array, from, mid);
                mergeSort(array, mid + 1, to);
                merge(array, from, mid, to);
            }
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
