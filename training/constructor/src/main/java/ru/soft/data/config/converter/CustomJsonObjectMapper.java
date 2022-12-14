package ru.soft.data.config.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;

public final class CustomJsonObjectMapper extends ObjectMapper {

    private static final CustomJsonObjectMapper INSTANCE = new CustomJsonObjectMapper();

    private CustomJsonObjectMapper() {
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        this.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        this.setAccessorNaming(new DefaultAccessorNamingStrategy.Provider().withGetterPrefix("").withSetterPrefix(""));
    }

    public static CustomJsonObjectMapper instance() {
        return INSTANCE;
    }
}
