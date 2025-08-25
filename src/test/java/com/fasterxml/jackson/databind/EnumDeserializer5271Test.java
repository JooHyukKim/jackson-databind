package com.fasterxml.jackson.databind;

import org.junit.Assert;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class EnumDeserializer5271Test
    extends BaseMapTest
{
    public enum MyEnum {
        T10("10%"), T20("20%"), T30("30%");

        private final String code;

        MyEnum(String code) {
            this.code = code;
        }

        @JsonValue
        public String getCode() {
            return code;
        }
    }


    public void testConvertStringToEnum() {
        _testConvert(JsonMapper.builder().disable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                .build()
        );
        _testConvert(JsonMapper.builder().enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
                .build()
        );
    }

    private void _testConvert(JsonMapper mapper) {
        Assert.assertEquals(mapper.convertValue("10%", MyEnum.class), MyEnum.T10);
    }

}
