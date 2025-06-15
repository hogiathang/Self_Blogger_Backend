package com.techblog.backend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techblog.backend.dto.blog.BlogContent;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

@Converter
public class BlogContentConverter implements AttributeConverter<BlogContent, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(BlogContent attribute) {
        return objectMapper.writeValueAsString(attribute);
    }

    @Override
    public BlogContent convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, BlogContent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
