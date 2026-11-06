package com.navapbc.piqi.model.eob;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Objects;

/** PIQI Simple Attribute: a single value that is a plain string in the JSON payload. */
public class PiqiSimpleAttribute {

    private final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public PiqiSimpleAttribute(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PiqiSimpleAttribute s && Objects.equals(value, s.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
