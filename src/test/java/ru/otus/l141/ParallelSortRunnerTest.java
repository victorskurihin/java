package ru.otus.l141;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ParallelSortRunnerTest {
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
    public void test() {
        ArrayList<Integer> x = new ArrayList<Integer>();
        for(int i = 1; i <= 1000000; i++) {
            x.add(i);
        }
        Collections.shuffle(x);
        ParallelSortRunner<Integer> parallelSortRunner = new ParallelSortRunner<Integer>(
            x, 1
        );
        parallelSortRunner.print();
        try {
            parallelSortRunner.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        parallelSortRunner.print();
        List<Integer> list = parallelSortRunner.getResultFromCollection();
        list.forEach(e -> System.out.print(e + " "));
    }
}