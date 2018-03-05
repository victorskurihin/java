package ru.otus.l141;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.otus.l141.sort.Sorter;
import ru.otus.l141.sort.ForkJoinSorterImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ForkJoinSorterBenchmark {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
            .include(ForkJoinSorterBenchmark.class.getSimpleName())
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

        public List<Integer> values;

        @Setup(Level.Invocation)
        public void setUp() {
            values = new ArrayList<>(size);
            for(int i = 1; i <= size; i++) {
                values.add(i);
            }
            Collections.shuffle(values);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void timeParallelSortRunner(BenchmarkExecutionPlan plan)
        throws ExecutionException, InterruptedException {

        Sorter sorter = new ForkJoinSorterImpl();
        for (int i = 0; i < plan.iterations; i++) {
            sorter.sort(plan.values, plan.threads);
        }
    }
}
