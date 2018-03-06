package ru.otus.l141.sort;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This is helper class for
 * @param <T>
 */
class MergeAssembly<T extends Comparable<? super T>> {
    private T[] array;
    private T[][] arrays;

    private int[] positionsInArrays;
    private int minimumIndex = 0;

    private Comparator<T> comparator;
    private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) { };

    public MergeAssembly(T[] array, int numberPieces, Comparator<T> comparator) {

        if (array.length < 1 || numberPieces < 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        //noinspection unchecked
        Class<T> clazz = (Class<T>) typeToken.getRawType();

        this.array = array;
        //noinspection unchecked
        this.arrays = (T[][]) Array.newInstance(clazz, numberPieces, 0);
        this.positionsInArrays = new int[numberPieces];
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

    private <E> boolean indexOutOfBounds(E[] array, int index) {
        return index < 0 || index >= array.length;
    }

    /**
     * @param index
     * @param from the initial index of the range to be copied, inclusive
     * @param to the final index of the range to be copied, exclusive.
     *        (This index may lie outside the array.)
     */
    void pushToSlot(int index, int from, int to) {

        if (indexOutOfBounds(arrays, index) ||
            indexOutOfBounds(array, to - 1)  ||
            indexOutOfBounds(array, from) ) {
            throw new ArrayIndexOutOfBoundsException();
        }
        arrays[index] = Arrays.copyOfRange(array, from, to);
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
