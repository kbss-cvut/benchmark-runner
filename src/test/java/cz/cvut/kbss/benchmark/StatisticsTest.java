package cz.cvut.kbss.benchmark;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StatisticsTest {

    @Mock
    private Logger logger;

    private Statistics statistics;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void calculatesSimpleStatisticsCorrectly() throws Exception {
        final List<Long> values = Arrays.asList(5L, 7L, 4L, 4L, 6L, 2L, 8L);
        this.statistics = new Statistics(values);
        statistics.print(logger);
        assertEquals(36L, getValue("total"));
        assertEquals(2L, getValue("fastest"));
        assertEquals(8L, getValue("slowest"));
        assertEquals(36L / 7, getValue("average"));
        assertEquals(5L, getValue("median"));
        assertEquals(4L, getValue("qOne"));
        assertEquals(7L, getValue("qThree"));
    }

    private long getValue(String fieldName) throws Exception {
        final Field f = Statistics.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        return (long) f.get(statistics);
    }

    @Test
    public void calculatesStatisticsCorrectlyWhenSizeIsEven() throws Exception {
        final List<Long> values = Arrays.asList(1L, 3L, 3L, 4L, 5L, 6L, 6L, 7L, 8L, 8L);
        this.statistics = new Statistics(values);
        statistics.print(logger);
        assertEquals(6L, getValue("median"));   // median 5.5, mathematically rounded to 6
        assertEquals(3L, getValue("qOne"));
        assertEquals(7L, getValue("qThree"));
    }
}