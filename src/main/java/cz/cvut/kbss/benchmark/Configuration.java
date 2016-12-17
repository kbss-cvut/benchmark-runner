package cz.cvut.kbss.benchmark;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Configuration {

    private int warmups;
    private int rounds;

    public Configuration(String[] args) {
        final OptionParser optionParser = initOptionParser();
        final OptionSet options = optionParser.parse(args);
        this.warmups = (int) options.valueOf(Parameters.WARMUPS.getArg());
        this.rounds = (int) options.valueOf(Parameters.ROUNDS.getArg());
    }

    private OptionParser initOptionParser() {
        final OptionParser p = new OptionParser();
        p.accepts(Parameters.WARMUPS.getArg(), Parameters.WARMUPS.getDescription()).withRequiredArg()
         .ofType(Integer.class).defaultsTo(Constants.WARMUP_ROUNDS);
        p.accepts(Parameters.ROUNDS.getArg(), Parameters.ROUNDS.getDescription()).withRequiredArg()
         .ofType(Integer.class).defaultsTo(Constants.ROUNDS);
        return p;
    }

    public int getWarmups() {
        return warmups;
    }

    public int getRounds() {
        return rounds;
    }
}
