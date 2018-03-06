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

public class ParallelMergeIntSortBenchmark {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
            .include(ParallelMergeIntSortBenchmark.class.getSimpleName())
            .warmupIterations(1)
            .measurementIterations(5)
            .forks(1)
            .build();
        new Runner(opt).run();
    }

    @State(Scope.Thread)
    public static class BenchmarkExecutionPlan {

        @Param({ "500000" })
        public int size;

        @Param({ "1", "5" })
        public int iterations;

        @Param({ "1", "2", "4" })
        public int threads;

        public int[] values;

        @Setup(Level.Invocation)
        public void setUp() {
            List<Integer> list = new ArrayList<>(size);
            values = new int[size];
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

        ParallelMergeIntSort sorter = new ParallelMergeIntSort(plan.values);
        for (int i = 0; i < plan.iterations; i++) {
            sorter.sort();
        }
    }
}
