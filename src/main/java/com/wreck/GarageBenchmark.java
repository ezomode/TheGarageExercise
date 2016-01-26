package com.wreck;


import com.wreck.garage.Garage;
import com.wreck.garage.GarageImpl;
import com.wreck.model.Motorbike;
import com.wreck.model.Vehicle;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/*
* Benchmark                                 Mode  Cnt  Score    Error   Units
* GarageBenchmark.measure1MInserts          avgt    5  2.788 ±  0.368   s/op
* GarageBenchmark.measure1MInsertsRemoves   avgt    5  3.036 ±  0.653   s/op
* GarageBenchmark.measure10MInserts         avgt    5  53.158 ± 10.960  s/op
* */

@BenchmarkMode(Mode.AverageTime)
public class GarageBenchmark {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(GarageBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measure1MInserts() throws InterruptedException {
        Garage garage1M = GarageImpl.create(1000, 1000);

        while (garage1M.insert(new Motorbike())) {
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measure1MInsertsRemoves() throws InterruptedException {
        Garage garage1M = GarageImpl.create(1000, 1000);

        List<Vehicle> vehicles = new ArrayList<>();
        IntStream.range(0, 1000000).forEach(i -> vehicles.add(new Motorbike()));

        vehicles.stream().forEach(garage1M::insert);
        vehicles.stream().forEach(garage1M::remove);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measure10MInserts() throws InterruptedException {
        Garage garage1M = GarageImpl.create(10000, 1000);

        while (garage1M.insert(new Motorbike())) {
        }
    }
}