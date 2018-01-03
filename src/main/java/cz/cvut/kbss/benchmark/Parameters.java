package cz.cvut.kbss.benchmark;

public enum Parameters {

    WARMUPS("w", "Number of warm-up rounds."),
    ROUNDS("r", "Number of evaluated rounds."),
    OUTPUT("o", "File to output raw execution results. Default is empty.");

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
