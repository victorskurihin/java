package ru.otus.l141.sort;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class MergeAssemblyTest {
    private MergeAssembly<Integer> mergeAssembly;

    private Integer[] array0;
    private Integer[] array1;
    private Integer[] array2;

    @Before
    public void setUp() throws Exception {
        array0 = new Integer[0];

        array1 = new Integer[1];
        array1[0] = 1;

        array2 = new Integer[2];
        array2[0] = 1;
        array2[1] = 2;
    }

    @After
    public void tearDown() throws Exception {
        array2 = null;
        array1 = null;
        array0 = null;
        mergeAssembly = null;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void emptyThrowExeption() {
        mergeAssembly = new MergeAssembly<>(array0, 1, Comparator.naturalOrder());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void zeroThreadsThrowExeption() {
        mergeAssembly = new MergeAssembly<>(array1, 0, Comparator.naturalOrder());
    }

    @Test
    public void mergeAssembliesToOneHasNext() {
        mergeAssembly = new MergeAssembly<>(array1, 1, Comparator.naturalOrder());
        mergeAssembly.pushToSlot(0, 0, array1.length);
        Assert.assertTrue(mergeAssembly.hasNext());
    }

    @Test
    public void findMinimumInListHasNext() {
        mergeAssembly = new MergeAssembly<>(array1, 1, Comparator.naturalOrder());
        mergeAssembly.pushToSlot(0, 0, array1.length);
        mergeAssembly.findMinimumInList(array1, 0);
        Assert.assertTrue(mergeAssembly.hasNext());
    }

    @Test
    public void afterPollHasNotNext() {
        mergeAssembly = new MergeAssembly<>(array1, 1, Comparator.naturalOrder());
        mergeAssembly.pushToSlot(0, 0, array1.length);
        mergeAssembly.poll();
        Assert.assertFalse(mergeAssembly.hasNext());
    }

    @Test
    public void pullTwice() {
        mergeAssembly = new MergeAssembly<>(array2, 1, Comparator.naturalOrder());
        mergeAssembly.pushToSlot(0, 0, array2.length);
        int i1 = mergeAssembly.poll();
        Assert.assertEquals(1, i1);
        Assert.assertTrue(mergeAssembly.hasNext());
        int i2 = mergeAssembly.poll();
        Assert.assertEquals(2, i2);
        Assert.assertFalse(mergeAssembly.hasNext());
    }

    @Test
    public void testTriangularMatrix() {
        Integer[] test = {1, 2, 3, 3, 4, 5};
        Integer[] expected = {1, 2, 3, 3, 4, 5};

        mergeAssembly = new MergeAssembly<>(test, 3, Comparator.naturalOrder());
        mergeAssembly.pushToSlot(0, 0, 1);
        mergeAssembly.pushToSlot(1, 1, 3);
        mergeAssembly.pushToSlot(2, 3, test.length);

        List<Integer> testResult = new ArrayList<>();
        while (mergeAssembly.hasNext()) {
            testResult.add(mergeAssembly.poll());
        }
        Assert.assertArrayEquals(expected, testResult.toArray());
    }

    @Test
    public void test100() {
        int size = 100;
        int numberPieces = 10;

        Integer[] test = new Integer[size];

        for (int idx = 0; idx < size; ++idx) {
            test[idx] = idx;
        }

        mergeAssembly = new MergeAssembly<>(test, numberPieces, Comparator.naturalOrder());

        int interval = size / numberPieces;

        for (int idx = 0; idx < numberPieces; ++idx) {
            int from = idx*interval;
            int to =  from + interval;
            mergeAssembly.pushToSlot(idx, from, to);
        }

        Integer expected = 0;
        while (mergeAssembly.hasNext()) {
            Assert.assertEquals(expected++, mergeAssembly.poll());
        }
    }
}