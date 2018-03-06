package ru.otus.l141.sort;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Interface class that has the following methods.
 */
public interface Sorter {
    /**
     * Sorts  the specified array  of objects  into ascending order, according
     * to the Comparable natural ordering of its elements.
     * All elements in the array must implement the Comparable interface.
     * Furthermore, all elements in the array must be mutually comparable (that
     * is, {@code compare(e1, e2)} must not throw a ClassCastException  for any
     * elements e1 and e2 in the array).
     *
     * This method for sorting build-id array of generics.
     *
     * @param array the target array
     * @param numberOfThreads number of threads
     * @param <T> the type of elements in the target array, must be Comparable.
     * @return the sorted array
     * @throws ExecutionException
     * @throws InterruptedException
     */
    <T extends Comparable<? super T>>
    T[] sort(T[] array, int numberOfThreads)
        throws ExecutionException, InterruptedException;

    /**
     * This method for sorting a Collection.
     *
     * @param collection the target collection
     * @param numberOfThreads number of threads
     * @param <T> the type of elements in the target list, must be Comparable.
     * @return the sorted array
     * @throws ExecutionException
     * @throws InterruptedException
     */
    <T extends Comparable<? super T>>
    List<T> sort(Collection<T> collection, int numberOfThreads)
        throws ExecutionException, InterruptedException;
}
