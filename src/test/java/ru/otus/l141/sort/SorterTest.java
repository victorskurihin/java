package ru.otus.l141.sort;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SorterTest {

    private final int numberThreads = 2;
    private final int size = 131_071;
    // private final int size = 524_287;

    private Integer[] valuesArray;
    private Integer[] expectedArray;
    private List<Integer> valuesList;
    private List<Integer> expectedList;
    private Sorter sorterThreads;
    private Sorter sorterForkJoin;

    @Before
    public void setUp() throws Exception {
        System.err.println("Sorter size = " + size);
        List<Integer> list = new ArrayList<>(size);

        for(int i = 0; i < size; i++) {
            list.add(i);
        }
        expectedArray = list.toArray(new Integer[size]);
        Collections.shuffle(list);
        valuesArray = list.toArray(new Integer[size]);

        valuesList = IntStream.range(0, size)
            .boxed()
            .collect(Collectors.toCollection(ArrayList::new));
        expectedList = new ArrayList<>(valuesList);
        Collections.shuffle(valuesList);
    }

    @After
    public void tearDown() throws Exception {
        sorterThreads = null;
        sorterForkJoin = null;
        valuesList = null;
        expectedList = null;
        valuesArray = null;
        expectedArray = null;
    }

    @Test
    public void sorterThreadsArray() throws ExecutionException, InterruptedException {
        sorterThreads = new ThreadsSorterImpl();
        valuesArray = sorterThreads.sort(valuesArray, numberThreads);
        Assert.assertArrayEquals(expectedArray, valuesArray);
    }

    @Test
    public void sorterThreadsList() throws ExecutionException, InterruptedException {
        sorterThreads = new ThreadsSorterImpl();
        valuesList = sorterThreads.sort(valuesList, numberThreads);
        Assert.assertEquals(expectedList, valuesList);
    }


    @Test
    public void sorterForkJoinArray() throws ExecutionException, InterruptedException {
        sorterForkJoin = new ForkJoinSorterImpl();
        valuesArray = sorterForkJoin.sort(valuesArray, numberThreads);
        Assert.assertArrayEquals(expectedArray, valuesArray);
    }

    @Test
    public void sorterForkJoinList() throws ExecutionException, InterruptedException {
        sorterForkJoin = new ForkJoinSorterImpl();
        valuesList = sorterForkJoin.sort(valuesList, numberThreads);
        Assert.assertEquals(expectedList, valuesList);
    }
}