package ru.otus.l141.sort;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * This is implementation of Parallel Sort using Fork-Join Framework to do
 * parallel execution.
 *
 * In this implementation of Sorter interface realises two methods, one for
 * build-in arrays and other for Collections.
 */
public class ForkJoinSorterImpl implements Sorter {
    @Override
    public <T extends Comparable<? super T>>
    T[] sort(T[] array, int numberOfThreads)
        throws ExecutionException, InterruptedException {

        ForkJoinPool pool = new ForkJoinPool(numberOfThreads);
        //noinspection unchecked
        Stream<T> stream = pool.submit(
            () -> Arrays.stream(array).parallel().sorted()
        ).get();

        AtomicInteger index = new AtomicInteger();
        stream.forEachOrdered(e -> array[index.getAndIncrement()] = e);

        return array;
    }

    @Override
    public <T extends Comparable<? super T>>
    List<T> sort(Collection<T> list, int numberOfThreads)
        throws ExecutionException, InterruptedException {

        ForkJoinPool pool = new ForkJoinPool(numberOfThreads);
        return pool.submit(
            () -> list.stream().parallel().sorted().collect(toList())
        ).get();
    }
}
