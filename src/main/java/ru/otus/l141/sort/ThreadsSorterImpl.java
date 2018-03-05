package ru.otus.l141.sort;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import static java.util.stream.Collectors.toList;

public class ThreadsSorterImpl implements Sorter {
    @Override
    public <T extends Comparable<? super T>>
    T[] sort(T[] array, int numberOfThreads)
        throws ExecutionException, InterruptedException {

        ForkJoinPool pool = new ForkJoinPool(numberOfThreads);
        //noinspection unchecked
        return (T[]) pool.submit(
            () -> Arrays.stream(array).parallel().sorted().toArray()
        ).get();
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
