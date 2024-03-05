package com.fasterxml.jackson.databind.ser;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.fasterxml.jackson.databind.BaseTest.verifyException;

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

        // Ser : PropertyNamingStrategy NOT applied
        Bug bug = new Bug();
        bug.colorMode = ColorMode.RGBa;
        assertEquals(
            "{\"color_mode\":\"RGBa\"}",
            mapper.writeValueAsString(bug)
        );

        // Deser : PropertyNamingStrategy applied
        try {
            mapper.readValue("{\"color_mode\":\"rgba\"}", Bug.class);
        } catch (InvalidDefinitionException e) {
            verifyException(
                e,
                "Multiple fields representing property \"rgba\""
            );
        }
    }
}
