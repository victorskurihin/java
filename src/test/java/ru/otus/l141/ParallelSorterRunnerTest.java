package ru.otus.l141;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParallelSorterRunnerTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() {
        //noinspection unchecked
        ParallelSortRunner parallelSortRunner = new ParallelSortRunner(
            Collections.singleton(new int[]{0}), 2
        );
    }

    @Test
    public void correctStepValues() {
        //noinspection unchecked
        ParallelSortRunner parallelSortRunner1 = new ParallelSortRunner(
            new Integer[]{3, 2, 1}, 2
        );
        parallelSortRunner1.print();
    }

    @Test
    public void testRunnerRun() {
        ArrayList<Integer> x = new ArrayList<Integer>();
        for(int i = 1; i <= 1000; i++) {
            x.add(i);
        }
        ArrayList<Integer> expected = new ArrayList<Integer>(x);
        Collections.shuffle(x);
        ParallelSortRunner<Integer> parallelSortRunner = new ParallelSortRunner<Integer>(
            x, 2
        );
        try {
            parallelSortRunner.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Integer> list = parallelSortRunner.getResultToList();
        Assert.assertEquals(expected, list);
    }
}