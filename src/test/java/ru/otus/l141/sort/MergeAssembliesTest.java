package ru.otus.l141.sort;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class MergeAssembliesTest {
    private MergeAssemblies<Integer> mergeAssemblies;

    private Integer[][] array0;
    private Integer[][] array1;
    private Integer[][] array2;
    private Integer[][] array3;
    private Integer[][] array100;

    @Before
    public void setUp() throws Exception {
        array0 = new Integer[0][];

        array1 = new Integer[1][1];
        array1[0][0] = 1;

        array2 = new Integer[1][2];
        array2[0][0] = 1;
        array2[0][1] = 2;

        array3 = new Integer[3][];
        for (int idx = 0; idx < 3; ++idx) {
            array3[idx] = new Integer[idx + 1];
            for (int jdx = 0; jdx < idx + 1; ++jdx) {
                array3[idx][jdx] = idx + (jdx + 1);
            }
        }

        array100 = new Integer[10][10];
        for (int idx = 0; idx < 10; ++idx) {
            for (int jdx = 0; jdx < 10; ++jdx) {
                array100[idx][jdx] = idx*10 + (jdx + 1);
            }
        }

    }

    @After
    public void tearDown() throws Exception {
        array100 = null;
        array2 = null;
        array1 = null;
        array0 = null;
        mergeAssemblies = null;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void emptyThrowExeption() {
        mergeAssemblies = new MergeAssemblies<>(array0, Comparator.naturalOrder());
    }

    @Test
    public void mergeAssembliesToOneHasNext() {
        mergeAssemblies = new MergeAssemblies<>(array1, Comparator.naturalOrder());
        Assert.assertTrue(mergeAssemblies.hasNext());
    }

    @Test
    public void findMinimumInListHasNext() {
        mergeAssemblies = new MergeAssemblies<>(array1, Comparator.naturalOrder());
        mergeAssemblies.findMinimumInList(array1[0], 0);
        Assert.assertTrue(mergeAssemblies.hasNext());
    }

    @Test
    public void afterPollHasNotNext() {
        mergeAssemblies = new MergeAssemblies<>(array1, Comparator.naturalOrder());
        mergeAssemblies.poll();
        Assert.assertFalse(mergeAssemblies.hasNext());
    }

    @Test
    public void pullTwice() {
        mergeAssemblies = new MergeAssemblies<>(array2, Comparator.naturalOrder());
        int i1 = mergeAssemblies.poll();
        Assert.assertEquals(1, i1);
        Assert.assertTrue(mergeAssemblies.hasNext());
        int i2 = mergeAssemblies.poll();
        Assert.assertEquals(2, i2);
    }

    @Test
    public void testTriangularMatrix() {
        Integer[] expected = {1, 2, 3, 3, 4, 5};
        List<Integer> testResult = new ArrayList<>();
        mergeAssemblies = new MergeAssemblies<>(array3, Comparator.naturalOrder());
        while (mergeAssemblies.hasNext()) {
            testResult.add(mergeAssemblies.poll());
        }
        Assert.assertEquals(expected, testResult.toArray());
    }

    @Test
    public void test100() {
        int expected = 1;
        mergeAssemblies = new MergeAssemblies<>(array100, Comparator.naturalOrder());
        while (mergeAssemblies.hasNext()) {
            int i = mergeAssemblies.poll();
            Assert.assertEquals(expected++, i);
        }
    }
}