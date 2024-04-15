package com.example.a7.model.types;


import com.example.a7.model.values.BoolValue;
import com.example.a7.model.values.Value;

public class BoolType implements Type {
    @Override
    public boolean equals(Object another) {
        if (another instanceof BoolType)
            return true;
        return false;
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public String toString() {
        return "bool";
    }
}
