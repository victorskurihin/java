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

        ThreadsSortRunner <T> runner = new ThreadsSortRunner<>(
            array, numberOfThreads
        );

        // TODO

        return null;
    }

    @Override
    public <T extends Comparable<? super T>>
    List<T> sort(Collection<T> collection, int numberOfThreads)
        throws ExecutionException, InterruptedException {

        ThreadsSortRunner <T> runner = new ThreadsSortRunner<>(
            collection, numberOfThreads
        );

        // TODO

        return null;
    }
}
