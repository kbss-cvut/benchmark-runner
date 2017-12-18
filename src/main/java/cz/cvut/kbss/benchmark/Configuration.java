package cz.cvut.kbss.benchmark;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Configuration {

    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    private static final OptionParser OPTION_PARSER = initOptionParser();

    private int warmups;
    private int rounds;
    private String outputFile;

    public Configuration(String[] args) {
        final OptionSet options = OPTION_PARSER.parse(args);
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
}
