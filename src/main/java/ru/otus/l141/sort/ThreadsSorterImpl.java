package ru.otus.l141.sort;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ThreadsSorterImpl implements Sorter {
    @Override
    public <T extends Comparable<? super T>>
    T[] sort(T[] array, int numberOfThreads)
        throws ExecutionException, InterruptedException {

        ThreadsSortRunner <T> runner = new ThreadsSortRunner<T>(
            array, numberOfThreads
        );
        runner.run();

        return runner.getResultToArray();
    }

    @Override
    public <T extends Comparable<? super T>>
    List<T> sort(Collection<T> collection, int numberOfThreads)
        throws ExecutionException, InterruptedException {

        ThreadsSortRunner <T> runner = new ThreadsSortRunner<T>(
            collection, numberOfThreads
        );
        runner.run();

        return runner.getResultToList();
    }
}
