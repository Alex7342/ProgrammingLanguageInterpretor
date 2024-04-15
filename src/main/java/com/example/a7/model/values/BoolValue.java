package com.example.a7.model.values;

import com.example.a7.model.types.BoolType;
import com.example.a7.model.types.Type;

public class BoolValue implements Value {
    boolean value;

    public BoolValue(boolean val) {
        value = val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    public boolean getVal() {
        return value;
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolValue;
    }

    @Override
    public String toString() {
        return "bool = " + Boolean.toString(value);
    }
}
