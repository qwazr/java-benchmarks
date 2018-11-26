/*
 *  Copyright 2015-2018 Emmanuel Keller / QWAZR
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.qwazr.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 2)
@Measurement(iterations = 3)
@Fork(value = 1, warmups = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ForLoopBenchmark {

    @State(Scope.Thread)
    public static class LoopState {

        public final List<Integer> arrayList = new ArrayList<>();
        public final List<Integer> synchonizedList = Collections.synchronizedList(arrayList);

        @Setup(Level.Trial)
        public void doSetup() {
            final Random random = new Random();
            for (int i = 0; i < 10000; i++)
                arrayList.add(random.nextInt(10));
        }

    }

    private long usingSizeAndGetMethod(final List<Integer> list) {
        long total = 0;
        for (int i = 0; i < list.size(); i++)
            total += list.get(i);
        return total;
    }

    @Benchmark
    public long usingSizeAndGetMethod(final LoopState state) {
        return usingSizeAndGetMethod(state.arrayList);
    }

    @Benchmark
    public long usingSizeAndGetMethodSync(final LoopState state) {
        return usingSizeAndGetMethod(state.synchonizedList);
    }

    private long usingSizeVariableAndGetMethod(final List<Integer> list) {
        long total = 0;
        final int size = list.size();
        for (int i = 0; i < size; i++)
            total += list.get(i);
        return total;
    }

    @Benchmark
    public long usingSizeVariableAndGetMethod(final LoopState state) {
        return usingSizeVariableAndGetMethod(state.arrayList);
    }

    @Benchmark
    public long usingSizeVariableAndGetMethodSync(final LoopState state) {
        return usingSizeVariableAndGetMethod(state.synchonizedList);
    }

    private long usingIteratorWithFor(final List<Integer> list) {
        long total = 0;
        for (final Integer value : list)
            total += value;
        return total;
    }

    @Benchmark
    public long usingIteratorWithFor(final LoopState state) {
        return usingIteratorWithFor(state.arrayList);
    }

    @Benchmark
    public long usingIteratorWithForSync(final LoopState state) {
        return usingIteratorWithFor(state.synchonizedList);
    }

    private long usingIteratorMethods(final List<Integer> list) {
        long total = 0;
        final Iterator<Integer> it = list.iterator();
        while (it.hasNext())
            total += it.next();
        return total;
    }

    @Benchmark
    public long usingIteratorMethods(final LoopState state) {
        return usingIteratorMethods(state.arrayList);
    }

    @Benchmark
    public long usingIteratorMethodsSync(final LoopState state) {
        return usingIteratorMethods(state.synchonizedList);
    }
}
