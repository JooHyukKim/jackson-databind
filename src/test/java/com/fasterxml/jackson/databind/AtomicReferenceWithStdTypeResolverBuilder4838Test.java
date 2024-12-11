package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceWithStdTypeResolverBuilder4838Test
        extends BaseMapTest
{
    static class Wrapper4383Test {
        public AtomicReference<Long> ref;
    }

    public void testPropertyAnnotationForReferences() throws Exception {
        Wrapper4383Test w = new ObjectMapper()
                .setDefaultTyping(
                        new StdTypeResolverBuilder()
                                .init(JsonTypeInfo.Id.CLASS, null)
                                .inclusion(JsonTypeInfo.As.WRAPPER_OBJECT))
                .readValue("{\"ref\": 99}", Wrapper4383Test.class);

        assertNotNull(w);
        assertNotNull(w.ref);
    }

}
