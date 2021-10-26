# Benchmark Runner

Benchmark Runner (BR) is a generic tool for running performance benchmarks.

The runner measures the following statistics:

* Fastest round,
* Slowest round,
* Average round execution time,
* Q1, median and Q3 of execution times,
* Total time execution (of measured rounds),
* Standard deviation.

## How to Use

To use this library, two things are required:

* Provide your own implementation of the `BenchmarkRunner` interface,
* Create an instance of `Benchmark`, passing in a `BenchmarkRunner` instance, and run it using `execute`.

### BenchmarkRunner

It is very similar to the old `TestCase` class from JUnit. It contains methods for global setup and tear down and
methods for setup and tear down for each execution. These methods all have default implementation, which does nothing 
(except for `setUp`, which executes garbage collection). Therefore, these need not be implemented.

The main part is the `execute` method, which has to be implemented and which represents the actual round execution. Only
runtime of this method is measure by the benchmark.

### Benchmark

`Benchmark` is the infrastructure of the benchmark. It calls the setup and tear down methods and executes the actual benchmark
run. As mentioned above, only the execution of `execute` is measured. Also, it is possible to configure warm-up rounds (see below), whose
performance is not measured, as they are used to let the JVM reach some kind of equilibrium, where code is optimized and no
unexpected events occur.


## Configuration

The benchmark accepts three arguments:

* **-w** represents number of warm-up rounds. Warm-up rounds are intended to let the JVM stabilize, perform code optimizations and JIT compilations,
so that these do not skew results of the measured rounds. Defaults to _20_.
* **-r** represents number of measured rounds. Defaults to _500_.
* **-o** file to which running times of all results should be written. Can be used to calculate additional statistics from the measured data.

Additional parameters can be added by registering them in the `Configuration` class.

### Example

`java -jar benchmark.jar -w 10 -r 100 -o benchmark.log`

Will execute benchmark with 10 warm-up rounds and 100 measured rounds. Execution time of each round will be written into file `benchmark.log`
(each round on a separate line).

## Where to Get

Via Maven. The project is published in the KBSS maven repository, so add the following repository configuration:
```xml
<repository>
    <id>KBSS</id>
    <url>https://kbss.felk.cvut.cz/m2repo</url>
</repository>
```

And then use the following dependency:
```xml
<dependency>
    <groupId>cz.cvut.kbss.benchmark</groupId>
    <artifactId>benchmark-runner</artifactId>
    <version>0.0.6</version>
</dependency>
```

## License

MIT