package com.example.demo.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils
{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static
    {
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private JsonUtils()
    {
    }

    public static String toJson(Object o)
    {
        try
        {
            return objectMapper.writeValueAsString(o);
        }
        catch (JsonProcessingException e)
        {
            throw new JsonRuntimeException(e.getMessage(), e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference)
    {
        try
        {
            return objectMapper.readValue(json, typeReference);
        }
        catch (JsonProcessingException e)
        {
            throw new JsonRuntimeException(e.getMessage(), e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz)
    {
        try
        {
            return objectMapper.readValue(json, clazz);
        }
        catch (JsonProcessingException e)
        {
            throw new JsonRuntimeException(e.getMessage(), e);
        }
    }

    public static final class JsonRuntimeException extends RuntimeException
    {
        public JsonRuntimeException(String message, JsonProcessingException e)
        {
            super(message, e);
        }
    }
}

