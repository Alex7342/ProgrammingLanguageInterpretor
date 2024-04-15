package com.example.a7.model.types;


import com.example.a7.model.values.RefValue;
import com.example.a7.model.values.Value;

public class RefType implements Type {
    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInnerType() {
        return this.inner;
    }

    public boolean equals(Object obj) {
        if (obj instanceof RefType)
            return inner.equals(((RefType) obj).getInnerType());
        return false;
    }

    public Value defaultValue() {
        return new RefValue(0, this.inner);
    }

    public String toString() {
        return "Ref(" + this.inner.toString() + ") ";
    }
}
