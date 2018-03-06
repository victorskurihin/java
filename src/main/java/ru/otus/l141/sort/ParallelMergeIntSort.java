package ru.otus.l141.sort;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

/**
 * This is complete implementation of Parallel Merge Sort using Fork-Join
 * Framework to do parallel execution.
 *
 * * extend RecursiveAction to allow execution in ForkJoin Pool
 * * decide when to sort directly
 * * execute parallel tasks for subarrays
 * * merge everything at the end.
 */
public class ParallelMergeIntSort extends RecursiveAction {

    // Decides when to fork or compute directly:
    private static final int SORT_THRESHOLD = 128;

    private final int[] values;
    private final int from;
    private final int to;

    public ParallelMergeIntSort(int[] values) {
        this(values, 0, values.length-1);
    }

    public ParallelMergeIntSort(int[] values, int from, int to) {
        this.values = values;
        this.from = from;
        this.to = to;
    }

    public void sort() {
        compute();
    }

    @Override
    protected void compute() {
        if (from < to) {
            int size = to - from;
            if (size < SORT_THRESHOLD) {
                insertionSort();
            } else {
                int mid = from + Math.floorDiv(size, 2);
                invokeAll(
                    new ParallelMergeIntSort(values, from, mid),
                    new ParallelMergeIntSort(values, mid + 1, to));
                merge(mid);
            }
        }
    }

    private void insertionSort() {
        for (int i = from+1; i <= to; ++i) {
            int current = values[i];
            int j = i-1;
            while (from <= j && current < values[j]) {
                values[j+1] = values[j--];
            }
            values[j+1] = current;
        }
    }

    private void merge(int mid) {
        int[] left = Arrays.copyOfRange(values, from, mid+1);
        int[] right = Arrays.copyOfRange(values, mid+1, to+1);
        int f = from;

        int li = 0, ri = 0;
        while (li < left.length && ri < right.length) {
            if (left[li] <= right[ri]) {
                values[f++] = left[li++];
            } else {
                values[f++] = right[ri++];
            }
        }

        while (li < left.length) {
            values[f++] = left[li++];
        }

        while (ri < right.length) {
            values[f++] = right[ri++];
        }
    }
}
