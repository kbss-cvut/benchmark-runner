package cz.cvut.kbss.benchmark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class Benchmark {

    private static final Logger LOG = LoggerFactory.getLogger(Benchmark.class);

    private static final int NANOS_TO_MILLIS = 1_000_000;

    private final Configuration configuration;

    private final BenchmarkRunner runner;

    private long totalTime = 0L;
    private long fastestTime = System.currentTimeMillis();
    private long slowestTime = 0L;

    private File outputFile;

    public Benchmark(BenchmarkRunner runner, String... args) {
        this.runner = Objects.requireNonNull(runner);
        this.configuration = new Configuration(Objects.requireNonNull(args));
    }

    /**
     * Executes the benchmark.
     */
    public void execute() {
        LOG.info("Running benchmark.");
        initRawOutputFile();
        LOG.debug("Running global setup.");
        runner.setUpBeforeBenchmark();
        try {
            executeRounds();
        } finally {
            LOG.debug("Running global teardown.");
            runner.tearDownAfterBenchmark();
        }
        LOG.info("Benchmark execution finished.");
        printStatistics();
    }

    private void initRawOutputFile() {
        if (configuration.getOutputFile() == null) {
            return;
        }
        this.outputFile = new File(configuration.getOutputFile());
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            throw new BenchmarkException("Unable to create output file " + configuration.getOutputFile());
        }
    }

    private void executeRounds() {
        final int totalRounds = configuration.getWarmups() + configuration.getRounds();
        for (int round = 0; round < totalRounds; round++) {
            LOG.trace("Running round setup.");
            runner.setUp();
            LOG.debug("Running {} round {}.", isWarmup(round) ? "warm-up" : "measured",
                    isWarmup(round) ? (round + 1) : (round + 1 - configuration.getWarmups()));
            try {

                long start = System.nanoTime();
                runner.execute();
                long end = System.nanoTime();

                if (!isWarmup(round)) {
                    final long duration = end - start;
                    this.totalTime += duration;
                    if (duration > slowestTime) {
                        this.slowestTime = duration;
                    }
                    if (duration < fastestTime) {
                        this.fastestTime = duration;
                    }
                    outputDuration(duration);
                }
            } catch (RuntimeException e) {
                LOG.error("Exception caught: {}.", e.getMessage());
            } finally {
                LOG.trace("Running round teardown.");
                runner.tearDown();
            }
        }
    }

    private boolean isWarmup(int round) {
        return round < configuration.getWarmups();
    }

    /**
     * Outputs round duration in milliseconds into the output file (if configured).
     *
     * @param duration Duration in nanoseconds
     */
    private void outputDuration(long duration) {
        if (outputFile == null) {
            return;
        }
        try {
            final String line = Long.toString(toMillis(duration)) + System.lineSeparator();
            Files.write(outputFile.toPath(), line.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new BenchmarkException("Unable to write round duration to file " + outputFile);
        }
    }

    private void printStatistics() {
        LOG.info("**********************************************");
        LOG.info("Benchmark results:");
        LOG.info("Warm-up rounds: {}.", configuration.getWarmups());
        LOG.info("Measured rounds: {}.", configuration.getRounds());
        LOG.info("Total measured time: {} ms.", toMillis(totalTime));
        LOG.info("Average round time: {} ms.", toMillis(totalTime / configuration.getRounds()));
        LOG.info("Fastest round time: {} ms.", toMillis(fastestTime));
        LOG.info("Slowest round time: {} ms.", toMillis(slowestTime));
        LOG.info("**********************************************");
    }

    private static long toMillis(long nanos) {
        return nanos / NANOS_TO_MILLIS;
    }
}
