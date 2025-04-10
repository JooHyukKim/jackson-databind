package com.fasterxml.jackson.databind;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.testutil.DatabindTestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

// [databind#5084]: Allow both Delegating and Properties
public class AllowDelegatingAndProperties5084Test
    extends DatabindTestUtil
{

    static class ValueTypeSeparate5084 {
        private int value;

        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        public ValueTypeSeparate5084(int value) {
            this.value = value;
        }

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        static ValueTypeSeparate5084 of(@JsonProperty("value") int value) {
            return new ValueTypeSeparate5084(value);
        }

    }

    static class ValueTypeTogether5084 {
        private int value;

        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        public ValueTypeTogether5084(int value) {
            this.value = value;
        }
    }

    private final ObjectMapper MAPPER = newJsonMapper();

    @Test
    public void testDeser()
        throws Exception
    {
        assertEquals(42, MAPPER.readValue("{\"value\": 42}", ValueTypeSeparate5084.class).value);
        assertEquals(42, MAPPER.readValue("42", ValueTypeSeparate5084.class).value);
    }

}
