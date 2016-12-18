package cz.cvut.kbss.benchmark;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;

public class Configuration {

    private static final OptionParser OPTION_PARSER = initOptionParser();

    private int warmups;
    private int rounds;

    public Configuration(String[] args) {
        final OptionSet options = OPTION_PARSER.parse(args);
        this.warmups = (int) options.valueOf(Parameters.WARMUPS.getArg());
        this.rounds = (int) options.valueOf(Parameters.ROUNDS.getArg());
    }

    private static OptionParser initOptionParser() {
        final OptionParser p = new OptionParser();
        p.accepts(Parameters.WARMUPS.getArg(), Parameters.WARMUPS.getDescription()).withRequiredArg()
         .ofType(Integer.class).defaultsTo(Constants.WARMUP_ROUNDS);
        p.accepts(Parameters.ROUNDS.getArg(), Parameters.ROUNDS.getDescription()).withRequiredArg()
         .ofType(Integer.class).defaultsTo(Constants.ROUNDS);
        return p;
    }

    /**
     * Prints help for the configuration options.
     *
     * @throws IOException When access to output stream fails
     */
    public static void printHelp() throws IOException {
        OPTION_PARSER.printHelpOn(System.out);
    }

    public int getWarmups() {
        return warmups;
    }

    public int getRounds() {
        return rounds;
    }
}
