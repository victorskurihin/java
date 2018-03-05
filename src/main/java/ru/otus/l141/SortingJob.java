package ru.otus.l141;

import java.util.Comparator;
import java.util.List;

public class SortingJob<T extends Comparable<? super T>> implements Runnable {
    private List<T> list;
    private Comparator<T> comparator;

    public SortingJob(List<T> listOfT, Comparator<T> comparator) {
        list = listOfT;
        this.comparator = comparator;
    }

    @Override
    public void run() {
        // TimSort.sort(a, 0, n, Arrays.NaturalOrder.INSTANCE, null, 0, 0);
        list.sort(Comparator.naturalOrder());
    }

    public List<T> getList() {
        return list;
    }
}
