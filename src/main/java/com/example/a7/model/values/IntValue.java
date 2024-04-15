package com.example.a7.model.values;

import com.example.a7.model.types.IntType;
import com.example.a7.model.types.Type;

public class IntValue implements Value {
    int val;

    public IntValue(int v) {
        val = v;
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "int = " + Integer.toString(val);
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof IntValue;
    }

    @Override
    public Type getType() {
        return new IntType();
    }
}
