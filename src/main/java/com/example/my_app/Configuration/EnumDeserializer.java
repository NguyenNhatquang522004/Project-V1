package com.example.my_app.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;

public class EnumDeserializer<T extends Enum<T>> extends JsonDeserializer<T> implements ContextualDeserializer {

    private Class<T> enumType;

    // Contructor mặc định cần có cho Jackson
    public EnumDeserializer() {
    }

    public EnumDeserializer(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        String value = node.asText();

        return Enum.valueOf(enumType, value.toUpperCase());
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {
        JavaType type = property.getType();
        Class<?> rawClass = type.getRawClass();
        if (!rawClass.isEnum()) {
            throw new IllegalArgumentException("EnumDeserializer chỉ áp dụng cho kiểu enum.");
        }
        @SuppressWarnings("unchecked")
        Class<T> enumClass = (Class<T>) rawClass;
        return new EnumDeserializer<>(enumClass);
    }
}
