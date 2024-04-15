package com.example.a7.model.values;


import com.example.a7.model.types.StringType;
import com.example.a7.model.types.Type;

public class StringValue implements Value {
    private final String value;

    public StringValue(String val) {
        this.value = val;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof StringValue;
    }

    public String toString() {
        return "string = " + this.value;
    }
}
