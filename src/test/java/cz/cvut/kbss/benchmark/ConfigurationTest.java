package cz.cvut.kbss.benchmark;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ConfigurationTest {

    @Test
    public void getValueReturnsValueOfKnownOption() {
        final Configuration configuration = new Configuration(new String[]{"-w", "5"});
        assertEquals(configuration.getWarmups(),
                configuration.getValue(Parameters.WARMUPS.getArg(), Integer.class).intValue());
    }

    @Test
    public void registerOptionAddsNewRecognizedOption() {
        final String option = "rr";
        final String value = "testValue";
        Configuration.registerOption(option, "lorem ipsum", String.class, "default");
        final Configuration configuration = new Configuration(new String[]{"-" + option, value});
        assertEquals(value, configuration.getValue(option, String.class));
    }

    @Test
    public void getValueReturnsDefaultValueOfNewlyRegisteredOptionWhenValueIsNotSpecified() {
        final String option = "rrr";
        final String defaultValue = "testValue";
        Configuration.registerOption(option, "lorem ipsum", String.class, defaultValue);
        final Configuration configuration = new Configuration(new String[0]);
        assertEquals(defaultValue, configuration.getValue(option, String.class));
    }

    @Test
    public void getValueOfOptionalOptionReturnsNullForUnspecifiedValue() {
        final String option = "rrrr";
        Configuration.registerOptionalOption(option, "lorem ipsum", Integer.class);
        final Configuration configuration = new Configuration(new String[0]);
        assertNull(configuration.getValue(option, Integer.class));
    }
}