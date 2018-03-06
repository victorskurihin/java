package ru.otus.l141.sort;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @param <T> the type of elements in the target array, must be Comparable.
 */
public class ThreadsSortRunner<T extends Comparable<? super T>> {

    private int interval;
    private int lastInterval;
    private int numberThreads;

    private T[] array;
    private Collection<T> collection;

    private Comparator<T> comparator = Comparator.naturalOrder();
    private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) { };

    private ThreadsSortRunner(int nThreads, int length) {
        if (nThreads < 1 || length < nThreads) {
            throw new IllegalArgumentException();
        }

        this.interval = length / nThreads;
        this.lastInterval = (0 != (length % nThreads))
            ? length - (interval*(nThreads - 1))
            : interval;
        this.numberThreads = nThreads;
    }

    /**
     * TODO
     *
     * @param arrayOfT
     * @param numberThreads
     */
    public ThreadsSortRunner(T[] arrayOfT, int numberThreads) {
        this(numberThreads, arrayOfT.length);
        this.array = arrayOfT;
    }

    /**
     * TODO
     *
     * @param elements
     * @param numberThreads
     */
    public ThreadsSortRunner(Collection<T> elements, int numberThreads) {
        this(numberThreads, elements.size());
        this.collection = elements;

        //noinspection unchecked
        Class<T> genericClass = (Class<T>) typeToken.getRawType();
        //noinspection unchecked
        this.array = elements.toArray(
            (T[]) Array.newInstance(genericClass, elements.size())
        );
    }

    private void runThread(List<Thread> threads, int from, int to) {
        Thread thread = new Thread(
            new MergeSortingJob<>(array, from, to, comparator)
        );
        threads.add(thread);
        thread.start();
    }

    /**
     * TODO
     *
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {
        List<Thread> threads = new ArrayList<>(numberThreads);
        int lastIndex = numberThreads - 1;

        for (int idx = 0; idx < lastIndex; ++idx) {
            int from = idx*interval;
            int to = from + interval - 1;
            runThread(threads, from, to);
        }
        int from = lastIndex*interval;
        int to = from + lastInterval - 1;
        runThread(threads, from, to);

        for (Thread thread : threads) {
            thread.join();
        }
    }

    private MergeAssembly<T> constructMergeAssembly() {
        MergeAssembly<T> merge = new MergeAssembly<T>(
            array, numberThreads, comparator
        );

        int lastIndex = numberThreads - 1;

        for (int idx = 0; idx < lastIndex; ++idx) {
            int from = idx*interval;
            int to = from + interval;
            merge.pushToSlot(idx, from, to);
        }
        int from = lastIndex*interval;
        int to = from + lastInterval;
        merge.pushToSlot(lastIndex, from, to);

        return merge;
    }

    /**
     * TODO
     *
     * @return
     */
    public T[] getResultToArray() {
        if (null != array) {
            MergeAssembly<T> merge = constructMergeAssembly();

            for (int idx = 0; idx < array.length; ++idx) {
                array[idx] = merge.poll();
            }

            return array;
        }

        throw new NullPointerException();
    }

    /**
     * TODO
     *
     * @return
     */
    public List<T> getResultToList() {
        if (null != collection) {
            MergeAssembly<T> merge = constructMergeAssembly();

            return collection.stream()
                .map(e -> merge.poll())
                .collect(Collectors.toCollection(ArrayList::new));
        }

        return new ArrayList<>(Arrays.asList(getResultToArray()));
    }

    public int getInterval() {
        return interval;
    }

    public int getLastInterval() {
        return lastInterval;
    }
}
