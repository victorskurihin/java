package ru.otus.l141.sort;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MergeSortTest {
    private final Integer[] expected10  = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final Integer[] expected5   = { 9, 8, 7, 3, 4, 5, 6, 2, 1, 0};
    private Integer[] test;

    private MergeSort<Integer> mergeSort;

    @Before
    public void setUp() throws Exception {
        mergeSort = new MergeSort<>();
        test = new Integer[]{ 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
    }

    @After
    public void tearDown() throws Exception {
        test = null;
        mergeSort = null;
    }

    @Test
    public void sortFull() {
        mergeSort.sort(test);
        Assert.assertArrayEquals(expected10, test);
    }

    @Test
    public void sortPartial() {
        mergeSort.sort(test,3, 6);
        Assert.assertArrayEquals(expected5, test);
    }
}