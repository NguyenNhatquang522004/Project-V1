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
import java.util.UUID;

public class AutoDeserializer<T> extends JsonDeserializer<T> implements ContextualDeserializer {

    private Class<T> targetType;

    // Constructor mặc định cần có cho Jackson
    public AutoDeserializer() {
    }

    public AutoDeserializer(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        String value = node.asText().trim();

        if (value.isEmpty()) {
            return null;
        }

        // Nếu kiểu là enum thì chuyển đổi bằng Enum.valueOf
        if (targetType.isEnum()) {
            @SuppressWarnings("unchecked")
            T enumValue = (T) Enum.valueOf((Class<Enum>) targetType, value.toUpperCase());
            return enumValue;
        }
        // Nếu kiểu là UUID thì chuyển đổi bằng UUID.fromString
        else if (targetType.equals(UUID.class)) {
            return (T) UUID.fromString(value);
        } else {
            throw new JsonMappingException(jsonParser,
                    "Unsupported target type for auto conversion: " + targetType.getName());
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {
        if (property == null) {
            throw new JsonMappingException(ctxt.getParser(), "Property is null in AutoDeserializer");
        }
        JavaType type = property.getType();
        @SuppressWarnings("unchecked")
        Class<T> targetType = (Class<T>) type.getRawClass();
        return new AutoDeserializer<>(targetType);
    }
}
