package ru.otus.l141.sort;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class ThreadsSortRunner<T extends Comparable<? super T>> {

    private int interval;
    private int lastInterval;
    private int numberThreads;

    private T[] array;
    private Collection<T> collection;

    private T[][] subArrays;

    private Comparator<T> comparator = Comparator.naturalOrder();
    private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) { };

    private Class<T> getParameterClass() {
        //noinspection unchecked
        return (Class<T>) typeToken.getRawType();
    }

    private ThreadsSortRunner(int nThreads, int length) {
        if (nThreads < 1 || length < nThreads) {
            throw new IllegalArgumentException();
        }

        this.interval = length / nThreads;
        this.lastInterval = (0 != (length % nThreads))
            ? length - (interval*(nThreads - 1))
            : interval;
        this.numberThreads = nThreads;

        Class<T> c = getParameterClass();

        //noinspection unchecked
        this.subArrays = (T[][]) Array.newInstance(c, nThreads, 0);
        for (int idx = 0; idx < nThreads - 1; ++idx) {
            //noinspection unchecked
            this.subArrays[idx] = (T[]) Array.newInstance(c, interval);
        }
        //noinspection unchecked
        this.subArrays[nThreads - 1] = (T[]) Array.newInstance(c, lastInterval);
    }

    private void copyToSubArray(int index, Iterator<T> iterator, int size) {
        for (int idx = 0; idx < size; ++idx) {
            subArrays[index][idx] = iterator.next();
        }
    }

    private int copyToSubArray(int index, int iterator, int size) {
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

    public T[] getResultToArray() {
        if (null != array) {
            MergeAssemblies<T> merge = new MergeAssemblies<>(
                subArrays, Comparator.naturalOrder()
            );

            for (int idx = 0; idx < array.length; ++idx) {
                array[idx] = merge.poll();
            }

            return array;
        }

        throw new NullPointerException();
    }

    public List<T> getResultToList() {
        if (null != collection) {
            MergeAssemblies<T> merge = new MergeAssemblies<>(
                subArrays, Comparator.naturalOrder()
            );

            return collection.stream()
                .map(e -> merge.poll())
                .collect(Collectors.toCollection(ArrayList::new));
        }

        return null;
    }

    public int getInterval() {
        return interval;
    }

    public int getLastInterval() {
        return lastInterval;
    }

}
