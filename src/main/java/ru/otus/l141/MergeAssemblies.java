package ru.otus.l141;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class MergeAssemblies<T extends Comparable<? super T>> {
    private ArrayList<T>[] subList;
    private int[] positionsInSubList;
    private int minimumIndex = 0;
    private Comparator<T> comparator;

    MergeAssemblies(ArrayList<T>[] subList, Comparator<T> comparator) {
        if (subList.length < 1 || subList[0].size() < 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.subList = subList;
        this.positionsInSubList = new int[subList.length];
        this.comparator = comparator;

        Arrays.fill(this.positionsInSubList, 0);
    }

    private int firstIndex() {
        for (int idx = 0; idx < subList.length; ++idx) {
            if (positionsInSubList[idx] < subList[idx].size()) {
                return idx;
            }
        }

        return -1;
    }

    boolean hasNext() {
        return firstIndex() > -1;
    }

    private T getMinimum() {
        return subList[minimumIndex].get(positionsInSubList[minimumIndex]);
    }

    boolean findMinimumInList(List<T> list, int index) {
        if (positionsInSubList[index] < list.size()) {
            T element = list.get(positionsInSubList[index]);
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
        for (int idx = 0; idx < subList.length; ++idx) {
            if (findMinimumInList(subList[idx], idx)) {
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
            positionsInSubList[minimumIndex] += 1;
            return minimum;
        }

        throw new IndexOutOfBoundsException();
    }
}
