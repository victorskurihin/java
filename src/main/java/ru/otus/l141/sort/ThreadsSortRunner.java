package ru.otus.l141.sort;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class and its nested classes provide the main functionality and control
 * for a set of sorting worker threads.  Workers take these tasks and typically
 * split them into subtasks of sorting.
 *
 * @param <T> the type of elements in the target array, must be Comparable.
 */
public class ThreadsSortRunner<T extends Comparable<? super T>>
    implements Runnable {

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
     * The constructor is for creating new ThreadsSortRunner for a build-in
     * array sorting.
     *
     * @param arrayOfT a build-in array
     * @param numberThreads number of threads
     */
    public ThreadsSortRunner(T[] arrayOfT, int numberThreads) {
        this(numberThreads, arrayOfT.length);
        this.array = arrayOfT;
    }

    /**
     * The constructor is for creating new ThreadsSortRunner for a Collection.
     *
     * @param elements a Collection.
     * @param numberThreads number of threads
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

    private void runThread(List<Thread> threads, int from, int toBarrier) {
        Thread thread = new Thread(
            new MergeSortingJob<>(array, from, toBarrier - 1, comparator)
        );
        threads.add(thread);
        thread.start();
    }

    private void iterateByIntervals(IterateHandlerIndexFromTo handler) {

        int lastIndex = numberThreads - 1;

        for (int idx = 0; idx < lastIndex; ++idx) {
            int from = idx*interval;
            int to = from + interval;
            handler.handle(idx, from, to);
        }

        int from = lastIndex*interval;
        int to = from + lastInterval;
        handler.handle(lastIndex, from, to);
    }

    /**
     * The method prepares the list of threads and this threads will be
     * running and they will join to main thread.
     * For each thread in the method runThread creates the sorting job.
     *
     * @throws RuntimeInterruptedException
     */
    public void run() {
        List<Thread> threads = new ArrayList<>(numberThreads);

        iterateByIntervals((i, from, to) -> runThread(threads, from, to));

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeInterruptedException(e);
            }
        }

        MergeAssembly<T> merge = constructMergeAssembly();

        for (int idx = 0; idx < array.length; ++idx) {
            array[idx] = merge.poll();
        }

    }

    private MergeAssembly<T> constructMergeAssembly() {
        MergeAssembly<T> merge = new MergeAssembly<T>(
            array, numberThreads, comparator
        );

        iterateByIntervals(merge::pushToSlot);

        return merge;
    }

    /**
     * @return the target array
     */
    public T[] getResultToArray() {
        return array;
    }

    /**
     * TODO comments
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
