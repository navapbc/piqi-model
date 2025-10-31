
package com.navapbc.piqi.model;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonValue;

@Generated("jsonschema2pojo")
public class SimpleAttribute {

    public SimpleAttribute() {}

    public SimpleAttribute(String value) {
        this.value = value;
    }

    private String value;

    @JsonValue
    public String getSerializedValue() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SimpleAttribute.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("value");
        sb.append('=');
        sb.append(((this.value == null)?"<null>":this.value));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SimpleAttribute) == false) {
            return false;
        }
        SimpleAttribute rhs = ((SimpleAttribute) other);
        return ((this.value == rhs.value)||((this.value!= null)&&this.value.equals(rhs.value)));
    }

}
