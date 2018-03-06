package ru.otus.l141.sort;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ThreadsSorterImpl implements Sorter {
    @Override
    public <T extends Comparable<? super T>>
    T[] sort(T[] array, int numberOfThreads)
        throws ExecutionException, InterruptedException {

        long startTime = System.currentTimeMillis();
        ThreadsSortRunner <T> runner = new ThreadsSortRunner<T>(
            array, numberOfThreads
        );
        long endTime = System.currentTimeMillis();
        System.err.println("Array construct took " + (endTime - startTime) + " milliseconds");

        startTime = System.currentTimeMillis();
        runner.run();
        endTime = System.currentTimeMillis();
        System.err.println("Array run took " + (endTime - startTime) + " milliseconds");

        startTime = System.currentTimeMillis();
        T[] result = runner.getResultToArray();
        System.err.println("Array get took " + (endTime - startTime) + " milliseconds");

        return result;
    }

    @Override
    public <T extends Comparable<? super T>>
    List<T> sort(Collection<T> collection, int numberOfThreads)
        throws ExecutionException, InterruptedException {

        long startTime = System.currentTimeMillis();
        ThreadsSortRunner <T> runner = new ThreadsSortRunner<T>(
            collection, numberOfThreads
        );
        long endTime = System.currentTimeMillis();
        System.err.println("Collection construct took " + (endTime - startTime) + " milliseconds");

        startTime = System.currentTimeMillis();
        runner.run();
        endTime = System.currentTimeMillis();
        System.err.println("Collection run took " + (endTime - startTime) + " milliseconds");

        startTime = System.currentTimeMillis();
        List<T> result = runner.getResultToList();
        endTime = System.currentTimeMillis();
        System.err.println("Collection get took " + (endTime - startTime) + " milliseconds");

        return result;
    }
}
