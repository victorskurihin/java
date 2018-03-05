package ru.otus.l141.sort;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ThreadsSortRunnerTest {
    private Integer[] array3;
    private Integer[] array4;
    private Integer[] array7;
    private Integer[] array8;
    private Integer[] array9;
    private final Integer[] expected3 = new Integer[]{0, 1, 2};
    private final Integer[] expected4 = new Integer[]{0, 1, 2, 3};
    private final Integer[] expected7 = new Integer[]{0, 1, 2, 3, 4, 5, 6};
    private final Integer[] expected8 = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7};
    private final Integer[] expected9 = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    private ThreadsSortRunner runner3;
    private ThreadsSortRunner runner4;
    private ThreadsSortRunner runner7;
    private ThreadsSortRunner runner8;
    private ThreadsSortRunner runner9;

    @Before
    public void setUp() throws Exception {
        array3 = new Integer[]{2, 1, 0};
        array4 = new Integer[]{3, 2, 1, 0};
        array7 = new Integer[]{6, 5, 4, 3, 2, 1, 0};
        array8 = new Integer[]{7, 6, 5, 4, 3, 2, 1, 0};
        array9 = new Integer[]{8, 7, 6, 5, 4, 3, 2, 1, 0};
    }

    @After
    public void tearDown() throws Exception {
        runner3 = null; array9 = null;
        runner4 = null; array8 = null;
        runner7 = null; array7 = null;
        runner8 = null; array4 = null;
        runner9 = null; array3 = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() {
        //noinspection unchecked
        ThreadsSortRunner runner = new ThreadsSortRunner(
            Collections.singleton(new int[]{0}), 2
        );
    }

    @SuppressWarnings("unchecked")
    private void initThreadsSortRunners() {
        runner3 = new ThreadsSortRunner( array3, 2);
        runner4 = new ThreadsSortRunner( array4, 4);
        runner7 = new ThreadsSortRunner( array7, 2);
        runner8 = new ThreadsSortRunner( array8, 3);
        runner9 = new ThreadsSortRunner( array9, 4);
    }

    @Test
    public void correctIntervalValues() {
        initThreadsSortRunners();
        Assert.assertEquals(1, runner3.getInterval());
        Assert.assertEquals(1, runner4.getInterval());
        Assert.assertEquals(3, runner7.getInterval());
        Assert.assertEquals(2, runner8.getInterval());
        Assert.assertEquals(2, runner9.getInterval());
    }

    @Test
    public void correctLastIntervalValues() {
        initThreadsSortRunners();
        Assert.assertEquals(2, runner3.getLastInterval());
        Assert.assertEquals(1, runner4.getLastInterval());
        Assert.assertEquals(4, runner7.getLastInterval());
        Assert.assertEquals(4, runner8.getLastInterval());
        Assert.assertEquals(3, runner9.getLastInterval());
    }

    @Test
    public void testRun() throws InterruptedException {
        initThreadsSortRunners();
        runner3.run();
        array3 = (Integer[]) runner3.getResultToArray();
        Assert.assertArrayEquals(expected3, array3);

        runner4.run();
        array4 = (Integer[]) runner4.getResultToArray();
        Assert.assertArrayEquals(expected4, array4);

        runner7.run();
        array7 = (Integer[]) runner7.getResultToArray();
        Assert.assertArrayEquals(expected7, array7);

        runner8.run();
        array8 = (Integer[]) runner8.getResultToArray();
        Assert.assertArrayEquals(expected8, array8);

        runner9.run();
        array9 = (Integer[]) runner9.getResultToArray();
        Assert.assertArrayEquals(expected9, array9);
    }

    @Test
    public void getResultToArray() throws ExecutionException, InterruptedException {
        Random random = new Random();

        int size = (random.nextInt() & 0xffff) % 1023
                 + (random.nextInt() & 0xffff) % 2047;
        System.err.println("array size = " + size);

        List<Integer> list = new ArrayList<>(size);

        for(int i = 1; i <= size; i++) {
            list.add(i);
        }

        Integer[] expected = list.toArray(new Integer[size]);

        Collections.shuffle(list);

        Integer[] values = list.toArray(new Integer[size]);

        ThreadsSortRunner<Integer> runner = new ThreadsSortRunner<>(values, 2);
        runner.run();
        values = runner.getResultToArray();

        Assert.assertArrayEquals(expected, values);

    }

    @Test
    public void getResultToList() throws InterruptedException {
        Random random = new Random();

        int size = (random.nextInt() & 0xffff) % 1023
                 + (random.nextInt() & 0xffff) % 2047;
        System.err.println("list size = " + size);

        List<Integer> values =  IntStream.range(1, size)
            .boxed().collect(Collectors.toCollection(ArrayList::new));

        List<Integer> expected = new ArrayList<>(values);
        Collections.shuffle(values);

        ThreadsSortRunner<Integer> runner = new ThreadsSortRunner<>(values, 2);
        runner.run();
        values = runner.getResultToList();
        Assert.assertEquals(expected, values);
    }

}