package cz.cvut.kbss.benchmark;

public final class Constants {

    /**
     * Default number of warm-up rounds, which are not measured.
     */
    public static final int WARMUP_ROUNDS = 20;

    /**
     * Default number of measured rounds.
     */
    public static final int ROUNDS = 500;

    private Constants() {
        throw new AssertionError();
    }
}
