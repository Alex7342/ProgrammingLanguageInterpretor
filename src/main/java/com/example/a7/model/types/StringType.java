package com.example.a7.model.types;

import com.example.a7.model.values.StringValue;
import com.example.a7.model.values.Value;

public class StringType implements Type {
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    public String toString() {
        return "string";
    }
}
