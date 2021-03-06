package cz.cvut.kbss.benchmark;

/**
 * This interface is intended to be implemented by the concrete benchmarks.
 */
public interface BenchmarkRunner {

    /**
     * Setup method executed only once, before the whole benchmark (i.e. even before the warm-up rounds).
     */
    default void setUpBeforeBenchmark() {
    }

    /**
     * Benchmark setup, executed before each run.
     * <p>
     * Setup method runtime is not counted in the measured benchmark times.
     * <p>
     * Default implementation runs garbage collection to clean up heap before executing the round.
     */
    default void setUp() {
        // Execute garbage collection to clear up memory
        System.gc();
        System.gc();
    }

    /**
     * Benchmark tear down, executed after each run.
     * <p>
     * Teardown method runtime is not counted in the measured benchmark times.
     * <p>
     * Default implementation does nothing.
     */
    default void tearDown() {
    }

    /**
     * Teardown method executed only once, after the whole benchmark.
     */
    default void tearDownAfterBenchmark() {
    }

    /**
     * Executes the benchmark code.
     * <p>
     * This method is called repeatedly every round.
     */
    void execute();

    /**
     * Allows the runner to be provided with access to the benchmark configuration.
     *
     * @param configuration Benchmark configuration
     */
    void setConfiguration(Configuration configuration);

    /**
     * Callback executed just before the first measured round (after the setup).
     *
     * Default implementation does nothing.
     */
    default void beforeFirstMeasured() {
        // Do nothing
    }
}
