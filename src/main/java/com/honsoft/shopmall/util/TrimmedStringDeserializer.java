package com.honsoft.shopmall.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class TrimmedStringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        if (value != null) {
            String trimmed = value.trim();
            return trimmed.isEmpty() ? null : trimmed; // or just `return trimmed;` if you don't want null
        }
        return null;
    }
}
