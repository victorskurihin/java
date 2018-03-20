package ru.otus.l141.sort;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ThreadsSorterBenchmark {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
            .include(ThreadsSorterBenchmark.class.getSimpleName())
            .warmupIterations(1)
            .measurementIterations(5)
            .forks(1)
            .build();
        new Runner(opt).run();
    }

    @State(Scope.Thread)
    public static class BenchmarkExecutionPlan {

        @Param({ "1000000" })
        public int size;

        @Param({ "1", "2" })
        public int iterations;

        @Param({ "1", "2", "4" })
        public int threads;

        public Integer[] values;

        @Setup(Level.Invocation)
        public void setUp() {
            List<Integer> list = new ArrayList<>(size);
            values = new Integer[size];
            for(int i = 1; i <= size; i++) {
                list.add(i);
            }
            Collections.shuffle(list);
            for (int i = 0; i < list.size(); i++) {
                values[i] = list.get(i);
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void timeParallelSortRunner(BenchmarkExecutionPlan plan)
        throws ExecutionException, InterruptedException {

        Sorter sorter = new ThreadsSorterImpl();
        for (int i = 0; i < plan.iterations; i++) {
            sorter.sort(plan.values, plan.threads);
        }
    }
}
