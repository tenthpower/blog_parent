package com.blog.config;

import com.blog.util.ObjectMapperEx;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {
    public ObjectMapperConfiguration() {
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapperEx objectMapperEx = new ObjectMapperEx();
        objectMapperEx.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapperEx.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        return objectMapperEx;
    }
}
