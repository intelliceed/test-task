package com.william.task.domain.event.intergation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T asObject(final String str, Class<T> class_) {
        try {
            return new ObjectMapper().readValue(str, class_);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T asObject(final String str, TypeReference<T> ref) {
        try {
            return new ObjectMapper().readValue(str, ref);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
