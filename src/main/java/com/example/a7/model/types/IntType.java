package com.example.a7.model.types;


import com.example.a7.model.values.IntValue;
import com.example.a7.model.values.Value;

public class IntType implements Type {
    public boolean equals(Object another) {
        if (another instanceof IntType)
            return true;
        return false;
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public String toString() {
        return "int";
    }
}