package ru.otus.l141.sort;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class ThreadsSortRunnerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() {
        //noinspection unchecked
        ThreadsSortRunner runner = new ThreadsSortRunner(
            Collections.singleton(new int[]{0}), 2
        );
    }

    @Test
    public void correctStepValues() {
        //noinspection unchecked
        ThreadsSortRunner runner = new ThreadsSortRunner(
            new Integer[]{3, 2, 1}, 2
        );
    }


    @Test
    public void getResultToArray() throws ExecutionException, InterruptedException {
        Random random = new Random();

        int size = (int) ((random.nextLong() & 0xffffL) % 1023 +
                         ((random.nextLong() & 0xffffL) % 2047));
        List<Integer> list = new ArrayList<>(size);

        for(int i = 1; i <= size; i++) {
            list.add(i);
        }

        Integer[] values = new Integer[size];
        Integer[] expected = list.toArray(new Integer[size]);

        Collections.shuffle(list);

        for (int i = 0; i < list.size(); i++) {
            values[i] = list.get(i);
        }

        Sorter sorter = new ThreadsSorterImpl();
        values = sorter.sort(values, 2);

        Assert.assertArrayEquals(expected, values);

    }

    @Test
    public void getResultToList() {
    }

}