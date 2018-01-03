package cz.cvut.kbss.benchmark;

import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Statistics {

    private static final double NANOS_TO_MILLIS = 1_000_000;

    private final List<Long> values;

    private long total;
    private long slowest;
    private long fastest;
    private long average;
    private long median;
    private long qOne;
    private long qThree;
    private long standardDeviation;

    public Statistics(List<Long> values) {
        this.values = Objects.requireNonNull(values);
    }

    public void print(Logger logger) {
        calculate(logger);
        logger.info("Total measured time: {} ms.", nanosToMillis(total));
        logger.info("Average round time: {} ms.", nanosToMillis(average));
        logger.info("Fastest round time: {} ms.", nanosToMillis(fastest));
        logger.info("Slowest round time: {} ms.", nanosToMillis(slowest));
        logger.info("Q1: {} ms.", nanosToMillis(qOne));
        logger.info("Median: {} ms.", nanosToMillis(median));
        logger.info("Q3: {} ms.", nanosToMillis(qThree));
        logger.info("Standard deviation: {} ms.", nanosToMillis(standardDeviation));
    }

    /**
     * Calculation of quartiles is done using Method 1 from
     * <a href="https://en.wikipedia.org/wiki/Quartile">https://en.wikipedia.org/wiki/Quartile</a>.
     */
    private void calculate(Logger logger) {
        if (values.isEmpty()) {
            logger.error("No values available for statistics.");
            return;
        }
        Collections.sort(values);
        this.total = values.stream().reduce(0L, Long::sum);
        this.average = total / values.size();
        this.fastest = values.get(0);
        this.slowest = values.get(values.size() - 1);
        this.median = median(values);
        if (values.size() % 2 != 0) {
            if (values.size() == 1) {
                this.qOne = this.qThree = median;
            } else {
                this.qOne = median(values.subList(0, values.indexOf(median)));
                this.qThree = median(values.subList(values.indexOf(median) + 1, values.size()));
            }
        } else {
            this.qOne = median(values.subList(0, values.size() / 2));
            this.qThree = median(values.subList(values.size() / 2, values.size()));
        }
        this.standardDeviation = calculateStandardDeviation();
    }

    private long median(List<Long> lst) {
        final int length = lst.size();
        if (length == 1) {
            return lst.get(0);
        }
        if (length % 2 != 0) {
            return lst.get(length / 2);
        } else {
            return Math.round((lst.get(length / 2 - 1) + lst.get(length / 2)) / 2.0);
        }
    }

    private long calculateStandardDeviation() {
        double sum = 0.0;
        for (Long v : values) {
            sum += Math.pow(v - average, 2);
        }
        return Math.round(Math.sqrt(sum / values.size()));
    }

    public static double nanosToMillis(long nanos) {
        return Math.round((nanos / NANOS_TO_MILLIS) * 100) / 100.0;
    }
}
