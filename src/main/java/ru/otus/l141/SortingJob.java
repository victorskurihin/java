package ru.otus.l141;

import java.util.Comparator;
import java.util.List;

public class SortingJob<T extends Comparable<? super T>> implements Runnable {
    private List<T> list;

    public SortingJob(List<T> listOfT) {
        list = listOfT;
    }

    @Override
    public void run() {
        list.sort(Comparator.naturalOrder());
    }

    public List<T> getList() {
        return list;
    }
}
