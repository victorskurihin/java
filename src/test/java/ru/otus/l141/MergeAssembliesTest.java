package ru.otus.l141;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;

public class MergeAssembliesTest {
    MergeAssemblies<Integer> mergeAssemblies;
    private ArrayList<Integer>[] subList0;
    private ArrayList<Integer>[] subList1;
    private ArrayList<Integer>[] subList2;
    private ArrayList<Integer>[] subList100;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        subList0 = new ArrayList[0];
        subList1 = new ArrayList[1];
        subList2 = new ArrayList[1];
        subList1[0] = new ArrayList<>(1);
        subList1[0].add(1);
        subList2[0] = new ArrayList<>(2);
        subList2[0].add(1);
        subList2[0].add(2);
        subList100 = new ArrayList[10];
        for (int idx = 0; idx < 10; ++idx) {
            subList100[idx] = new ArrayList<>(10);
            for (int jdx = 0; jdx < 10; ++jdx) {
                subList100[idx].add(idx*10 + (jdx + 1));
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        mergeAssemblies = null;
        subList100 = null;
        subList2 = null;
        subList1 = null;
        subList0 = null;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testEmptyThrowExeption() {
        mergeAssemblies = new MergeAssemblies<>(subList0, Comparator.naturalOrder());
    }

    @Test
    public void testMergeAssembliesToOneHasNext() {
        mergeAssemblies = new MergeAssemblies<>(subList1, Comparator.naturalOrder());
        Assert.assertTrue(mergeAssemblies.hasNext());
    }

    @Test
    public void testFindMinimumInListHasNext() {
        mergeAssemblies = new MergeAssemblies<>(subList1, Comparator.naturalOrder());
        mergeAssemblies.findMinimumInList(subList1[0], 0);
        Assert.assertTrue(mergeAssemblies.hasNext());
    }

    @Test
    public void test3() {
        mergeAssemblies = new MergeAssemblies<>(subList1, Comparator.naturalOrder());
        mergeAssemblies.poll();
        Assert.assertFalse(mergeAssemblies.hasNext());
    }

    @Test
    public void test4() {
        mergeAssemblies = new MergeAssemblies<>(subList2, Comparator.naturalOrder());
        int i1 = mergeAssemblies.poll();
        Assert.assertEquals(1, i1);
        Assert.assertTrue(mergeAssemblies.hasNext());
        int i2 = mergeAssemblies.poll();
        Assert.assertEquals(2, i2);
    }

    @Test
    public void test10() {
        int expected = 1;
        mergeAssemblies = new MergeAssemblies<>(subList100, Comparator.naturalOrder());
        while (mergeAssemblies.hasNext()) {
            int i = mergeAssemblies.poll();
            Assert.assertEquals(expected++, i);
        }
    }
}