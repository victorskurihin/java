package ru.otus.l141;

import java.util.*;
import java.util.stream.Collectors;

public class ParallelSortRunner<T extends Comparable<? super T>> {
    private int numberThreads;
    private T[] array = null;
    private Collection<T> collection = null;
    private ArrayList<T>[] subList;
    private int interval;
    private int lastInterval;

    private ParallelSortRunner(int nThreads, int length) {
        if (nThreads < 1 || length < nThreads) {
            throw new IllegalArgumentException();
        }

        this.interval = length / nThreads;
        this.lastInterval = (0 != (length % nThreads))
                          ? length - (interval*(nThreads - 1))
                          : interval;
        this.numberThreads = nThreads;

        //noinspection unchecked
        this.subList = new ArrayList[nThreads];
    }

    private void copyToSubList(int index, Iterator<T> iterator, int size) {
        subList[index] = new ArrayList<>(size);

        for (int idx = 0; idx < size; ++idx) {
            subList[index].add(iterator.next());
        }
    }

    private void givenSubsetWhenParitioning(Collection<T> c) {
        int lastIndex = numberThreads - 1;
        Iterator<T> iterator =  c.iterator();

        for (int idx = 0; idx < lastIndex; ++idx) {
            copyToSubList(idx, iterator, interval);
        }

        copyToSubList(lastIndex, iterator, lastInterval);
    }

    public ParallelSortRunner(T[] arrayOfT, int numberThreads) {
        this(numberThreads, arrayOfT.length);
        this.array = arrayOfT;

        givenSubsetWhenParitioning(Arrays.asList(arrayOfT));
    }

    public ParallelSortRunner(Collection<T> elements, int numberThreads) {
        this(numberThreads, elements.size());
        this.collection = elements;

        givenSubsetWhenParitioning(elements);
    }

    public void run() throws InterruptedException {
        List<Thread> threads = new ArrayList<>(numberThreads);

        for (int idx = 0; idx < numberThreads; ++idx) {
            Thread thread = new Thread(new SortingJob<>(subList[idx]));
            threads.add(thread);
            thread.start();

        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    int getInterval() {
        return interval;
    }

    int getLastInterval() {
        return lastInterval;
    }

    public T[] getResultFromArray() {
        if (null != array) {
            return array;
        }
        throw new NullPointerException();
    }

    public List<T> getResultFromCollection() {
        if (null != collection) {
            FinalAssembly<T> finalAssembly = new FinalAssembly<>(subList);

            return collection.stream()
                .map(e -> finalAssembly.pullMinimal())
                .collect(Collectors.toCollection(ArrayList::new));
        }
        throw new NullPointerException();
    }

    public void print() {
        Arrays.stream(subList).forEach(System.out::println);
    }
}
