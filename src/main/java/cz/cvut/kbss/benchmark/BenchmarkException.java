package cz.cvut.kbss.benchmark;

/**
 * Generic runtime exception indicating that an error occurred during execution of the benchmark.
 */
public class BenchmarkException extends RuntimeException {

    public BenchmarkException(String message) {
        super(message);
    }

    public BenchmarkException(String message, Throwable cause) {
        super(message, cause);
    }

    public BenchmarkException(Throwable cause) {
        super(cause);
    }
}
