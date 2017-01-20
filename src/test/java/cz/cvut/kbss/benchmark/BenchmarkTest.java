package cz.cvut.kbss.benchmark;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
}