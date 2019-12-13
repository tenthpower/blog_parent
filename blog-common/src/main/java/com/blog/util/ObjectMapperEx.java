package com.blog.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.TimeZone;

public class ObjectMapperEx extends ObjectMapper {
    private static final long serialVersionUID = 2997844125137070582L;

    public ObjectMapperEx() {
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }
}
