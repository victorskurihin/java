package ru.otus.l141.sort;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface Sorter {
    <T extends Comparable<? super T>>
    T[] sort(T[] array, int numberOfThreads)
        throws ExecutionException, InterruptedException;

    <T extends Comparable<? super T>>
    List<T> sort(Collection<T> list, int numberOfThreads)
        throws ExecutionException, InterruptedException;
}
