package com.fasterxml.jackson.databind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumTest {

    enum ColorMode {
        RGB,
        RGBa,
        RGBA
    }

    static class Bug {
        public ColorMode colorMode;
    }

    @Test
    public void testEnumAndPropertyNamingStrategy() throws Exception {
        ObjectMapper mapper = BaseMapTest.jsonMapperBuilder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

        // Ser
        Bug bug = new Bug();
        bug.colorMode = ColorMode.RGBa;
        assertEquals(
            "{\"color_mode\":\"RGBa\"}",
            mapper.writeValueAsString(bug)
        );

        // Deser
        Bug bug2 = mapper.readValue("{\"color_mode\":\"RGBa\"}", Bug.class);
        assertEquals(ColorMode.RGBa, bug2.colorMode);
    }
}
