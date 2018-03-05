package ru.otus.l141.sort;

import java.util.Arrays;
import java.util.Comparator;

class MergeAssemblies<T extends Comparable<? super T>> {
    private T[][] arrays;
    private int[] positionsInArrays;
    private int minimumIndex = 0;
    private Comparator<T> comparator;

    public MergeAssemblies(T[][] subArrays, Comparator<T> comparator) {

        if (subArrays.length < 1 || subArrays[0].length < 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        this.arrays = subArrays;
        this.positionsInArrays = new int[subArrays.length];
        this.comparator = comparator;

        Arrays.fill(this.positionsInArrays, 0);
    }

    private int firstIndex() {
        for (int idx = 0; idx < arrays.length; ++idx) {
            if (positionsInArrays[idx] < arrays[idx].length) {
                return idx;
            }
        }

        return -1;
    }

    boolean hasNext() {
        return firstIndex() > -1;
    }

    private T getMinimum() {
        return arrays[minimumIndex][positionsInArrays[minimumIndex]];
    }

    boolean findMinimumInList(T[] array, int index) {
        if (positionsInArrays[index] < array.length) {

            T element = array[positionsInArrays[index]];
            T minimum = getMinimum();

            if (comparator.compare(element, minimum) <= 0) {
                minimumIndex = index;
                return true;
            }
        }
        return false;
    }

    private boolean findMinimum() {
        boolean result = false;

        for (int idx = 0; idx < arrays.length; ++idx) {
            if (findMinimumInList(arrays[idx], idx)) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Retrieves and removes the head of this queue, or --returns null if this
     * structure is empty--.
     * @return
     */
    T poll() {
        minimumIndex = firstIndex();

        if (findMinimum()) {
            T minimum = getMinimum();
            positionsInArrays[minimumIndex] += 1;
            return minimum;
        }

        throw new IndexOutOfBoundsException();
    }
}
