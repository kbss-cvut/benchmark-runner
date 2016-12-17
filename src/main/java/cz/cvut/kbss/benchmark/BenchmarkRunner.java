package cz.cvut.kbss.benchmark;

/**
 * This interface is intended to be implemented by the concrete benchmarks.
 */
public abstract class BenchmarkRunner {

    /**
     * Setup method executed only once, before the whole benchmark (i.e. even before the warm-up rounds).
     */
    public void setUpBeforeBenchmark() {
    }

    /**
     * Benchmark setup, executed before each run.
     * <p>
     * Setup method runtime is not counted in the measured benchmark times.
     * <p>
     * Default implementation does nothing.
     */
    public void setUp() {
    }

    /**
     * Benchmark tear down, executed after each run.
     * <p>
     * Teardown method runtime is not counted in the measured benchmark times.
     * <p>
     * Default implementation does nothing.
     */
    public void tearDown() {
    }

    /**
     * Teardown method executed only once, after the whole benchmark.
     */
    public void tearDownAfterBenchmark() {
    }

    /**
     * Executes the benchmark code.
     * <p>
     * This method is called repeatedly every round.
     */
    public abstract void execute();
}
