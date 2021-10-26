package cz.cvut.kbss.benchmark;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Configuration {

    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    private static final OptionParser OPTION_PARSER = initOptionParser();

    private final OptionSet options;

    private final int warmups;
    private final int rounds;
    private final String outputFile;

    public Configuration(String[] args) {
        this.options = OPTION_PARSER.parse(args);
        this.warmups = (int) options.valueOf(Parameters.WARMUPS.getArg());
        this.rounds = (int) options.valueOf(Parameters.ROUNDS.getArg());
        this.outputFile = (String) options.valueOf(Parameters.OUTPUT.getArg());
    }

    private static OptionParser initOptionParser() {
        final OptionParser p = new OptionParser();
        p.accepts(Parameters.WARMUPS.getArg(), Parameters.WARMUPS.getDescription()).withRequiredArg()
                .ofType(Integer.class).defaultsTo(Constants.WARMUP_ROUNDS);
        p.accepts(Parameters.ROUNDS.getArg(), Parameters.ROUNDS.getDescription()).withRequiredArg()
                .ofType(Integer.class).defaultsTo(Constants.ROUNDS);
        p.accepts(Parameters.OUTPUT.getArg(), Parameters.OUTPUT.getDescription()).withOptionalArg()
                .ofType(String.class);
        return p;
    }

    /**
     * Registers additional option that should be supported by this configuration.
     *
     * @param argument     Argument name
     * @param description  Description of the option
     * @param type         Type of the option value
     * @param defaultValue Default value, can be {@code null}
     * @param <T>          Type of the option value
     */
    public static <T> void registerOption(String argument, String description, Class<T> type, T defaultValue) {
        OPTION_PARSER.accepts(argument, description).withRequiredArg().ofType(type).defaultsTo(defaultValue);
    }

    /**
     * Registers additional option that should be supported by this configuration.
     * <p>
     * The option will be optional, so if no value is specified, {@link #getValue(String, Class)} will return null.
     *
     * @param argument    Argument name
     * @param description Description of the option
     * @param type        Type of the option value
     * @param <T>         Type of the option value
     */
    public static <T> void registerOptionalOption(String argument, String description, Class<T> type) {
        OPTION_PARSER.accepts(argument, description).withOptionalArg().ofType(type);
    }

    /**
     * Prints help for the configuration options.
     */
    public static void printHelp() {
        try {
            OPTION_PARSER.printHelpOn(System.out);
        } catch (IOException e) {
            LOG.error("Unable to print help to stdout.", e);
        }
    }

    public int getWarmups() {
        return warmups;
    }

    public int getRounds() {
        return rounds;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public <T> T getValue(String option, Class<T> returnType) {
        return returnType.cast(options.valueOf(option));
    }
}
