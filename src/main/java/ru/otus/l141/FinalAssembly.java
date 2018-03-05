package ru.otus.l141;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class FinalAssembly <T extends Comparable<? super T>> {
    private int[] positions;
    private ArrayList<T>[] subList;
    private int minimumIndex = 0;

    FinalAssembly(ArrayList<T>[] subList) {
        if (subList.length < 1 || subList[0].size() < 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.subList = subList;
        this.positions = new int[subList.length];

        Arrays.fill(this.positions, 0);
    }

    private int firstIndex() {
        for (int idx = 0; idx < subList.length; ++idx) {
            if (positions[idx] < subList[idx].size()) {
                return idx;
            }
        }

        return -1;
    }

    boolean hasNext() {
        return firstIndex() > -1;
    }

    private T getMinimum() {
        return subList[minimumIndex].get(positions[minimumIndex]);
    }

    boolean findMinimumInList(List<T> list, int index) {
        if (positions[index] < list.size()) {
            T element = list.get(positions[index]);
            T minimum = getMinimum();

            if (element.compareTo(minimum) <= 0) {
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

    T pullMinimal() {
        minimumIndex = firstIndex();

        if (hasNext() && findMinimum()) {
            T minimum = getMinimum();
            positions[minimumIndex] += 1;
            return minimum;
        }

        throw new IndexOutOfBoundsException();
    }
}
