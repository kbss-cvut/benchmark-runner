package cz.cvut.kbss.benchmark;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class BenchmarkTest {

    @Mock
    private BenchmarkRunner runner;

    private Benchmark benchmark;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void benchmarkRunsGlobalSetupOnceBeforeAll() {
        this.benchmark = new Benchmark(runner, "-w", "2", "-r", "10");
        benchmark.execute();
        final InOrder order = inOrder(runner);
        order.verify(runner).setUpBeforeBenchmark();
        order.verify(runner).setUp();
        order.verify(runner).execute();
    }

    @Test
    public void benchmarkRunsSetupBeforeEachRound() {
        this.benchmark = new Benchmark(runner, "-w", "2", "-r", "10");
        benchmark.execute();
        verify(runner, times(12)).setUp();
        final InOrder order = inOrder(runner);
        order.verify(runner).setUp();
        order.verify(runner).execute();
    }

    @Test
    public void benchmarkRunsSpecifiedNumberOfRounds() {
        this.benchmark = new Benchmark(runner, "-w", "2", "-r", "10");
        benchmark.execute();
        verify(runner, times(12)).execute();
    }

    @Test
    public void benchmarkRunsTearDownAfterEachRound() {
        this.benchmark = new Benchmark(runner, "-w", "2", "-r", "10");
        benchmark.execute();
        verify(runner, times(12)).tearDown();
        final InOrder order = inOrder(runner);
        order.verify(runner).execute();
        order.verify(runner).tearDown();
    }

    @Test
    public void benchmarkRunsGlobalTeardownAfterWholeBenchmark() {
        this.benchmark = new Benchmark(runner, "-w", "2", "-r", "10");
        benchmark.execute();
        verify(runner).tearDownAfterBenchmark();
        final InOrder order = inOrder(runner);
        order.verify(runner).execute();
        order.verify(runner).tearDown();
        order.verify(runner).tearDownAfterBenchmark();
    }

    @Test
    public void benchmarkOutputsRunExecutionTimesWhenOutputFileIsConfigured() throws Exception {
        final File outputFile = File.createTempFile("benchmark", ".txt");
        outputFile.deleteOnExit();
        this.benchmark = new Benchmark(runner, "-w", "1", "-r", "2", "-o", outputFile.getPath());
        benchmark.execute();
        final List<String> content = Files.readAllLines(outputFile.toPath());
        assertFalse(content.isEmpty());
    }

    @Test
    public void benchmarkRunsBeforeFirstMeasuredCallbackBeforeExecutionOfFirstMeasuredRound() {
        this.benchmark = new Benchmark(runner, "-w", "1", "-r", "1");
        benchmark.execute();
        final InOrder inOrder = inOrder(runner);
        // Warmup
        inOrder.verify(runner).setUp();
        inOrder.verify(runner).execute();
        // Execution
        inOrder.verify(runner).setUp();
        inOrder.verify(runner).beforeFirstMeasured();
        inOrder.verify(runner).execute();
    }
}