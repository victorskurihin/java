package ru.otus.l141.sort;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This is an auxiliary class for the final sort. The instance of this class
 * creates  auxiliary  pieces of the array where is already sorted intervals
 * are copied.
 *
 * @param <T> the type of elements in the target array, must be Comparable.
 */
class MergeAssembly<T extends Comparable<? super T>> {
    private T[] array;
    private T[][] arrays;

    private int[] positionsInArrays;
    private int minimumIndex = 0;

    private Comparator<T> comparator;
    private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) { };

    /**
     * The constructor gets the target array, number of pieces and a comparator.
     *
     * @param array the target array
     * @param numberPieces number of pieces what already sorted
     * @param comparator comparator for a type T
     */
    MergeAssembly(T[] array, int numberPieces, Comparator<T> comparator) {

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

    /**
     *
     * @param array the target array
     * @param index the index of the piece
     * @return true if founded a minimum
     */
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
     * The method copy from the already sorted piece of the target array
     * to the service substructure in this class.
     *
     * @param index the index of the piece
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
     * Retrieves  and  removes  the head (minimum)  of  the service structure.
     * If this structure is empty this method throw IndexOutOfBoundsException.
     *
     * @return the minimum element
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
