package ru.otus.l141.sort;

import java.util.*;

public class ThreadsSortRunner<T extends Comparable<? super T>> {

    private int interval;
    private int lastInterval;
    private int numberThreads;

    private T[] array;
    private Collection<T> collection;

    private T[][] subArrays;

    private Comparator<T> comparator = Comparator.naturalOrder();

    private ThreadsSortRunner(int nThreads, int length) {
        if (nThreads < 1 || length < nThreads) {
            throw new IllegalArgumentException();
        }

        this.interval = length / nThreads;
        this.lastInterval = (0 != (length % nThreads))
            ? length - (interval*(nThreads - 1))
            : interval;
        this.numberThreads = nThreads;

        //noinspection unchecked
        this.subArrays = (T[][]) new Object[nThreads][];
    }

    private void copyToSubArray(int index, Iterator<T> iterator, int size) {
        //noinspection unchecked
        subArrays[index] = (T[]) new Object[size];

        for (int idx = 0; idx < size; ++idx) {
            subArrays[index][idx] = iterator.next();
        }
    }

    private int copyToSubArray(int index, int iterator, int size) {
        //noinspection unchecked
        subArrays[index] = (T[]) new Object[size];

        for (int idx = 0; idx < size; ++idx) {
            subArrays[index][idx] = array[iterator++];
        }
        return iterator;
    }

    private void givenSubsetWhenParitioning(Collection<T> c) {
        int lastIndex = numberThreads - 1;
        Iterator<T> iterator =  c.iterator();

        for (int idx = 0; idx < lastIndex; ++idx) {
            copyToSubArray(idx, iterator, interval);
        }

        copyToSubArray(lastIndex, iterator, lastInterval);
    }

    private void givenSubsetWhenParitioning() {
        int lastIndex = numberThreads - 1;
        int index =  0;

        for (int idx = 0; idx < lastIndex; ++idx) {
            index = copyToSubArray(idx, index, interval);
        }

        index = copyToSubArray(lastIndex, index, lastInterval);
    }

    public ThreadsSortRunner(T[] arrayOfT, int numberThreads) {
        this(numberThreads, arrayOfT.length);
        this.array = arrayOfT;

        givenSubsetWhenParitioning();
    }

    public ThreadsSortRunner(Collection<T> elements, int numberThreads) {
        this(numberThreads, elements.size());
        this.collection = elements;

        givenSubsetWhenParitioning(elements);
    }

    public void run() throws InterruptedException {
        List<Thread> threads = new ArrayList<>(numberThreads);

        for (int idx = 0; idx < numberThreads; ++idx) {
            Thread thread = new Thread(new SortingJob<>(subArrays[idx], comparator));
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    public List<T> getResultToList() {
        if (null != collection) {
            MergeAssemblies<T> merge = new MergeAssemblies<>(
                subArrays, Comparator.naturalOrder()
            );
        }
        return null;
    }
}
