package cz.cvut.kbss.benchmark;

public enum Parameters {

    WARMUPS("w", "Number of warm-up rounds. Default: " + Constants.WARMUP_ROUNDS),
    ROUNDS("r", "Number of evaluated rounds. Default: " + Constants.ROUNDS),
    OUTPUT("o", "File to output raw execution results. Default is empty");

    private final String arg;
    private final String description;

    Parameters(String arg, String description) {
        this.arg = arg;
        this.description = description;
    }

    public String getArg() {
        return arg;
    }

    public String getDescription() {
        return description;
    }
}
