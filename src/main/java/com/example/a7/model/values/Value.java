package com.example.a7.model.values;


import com.example.a7.model.types.Type;

public interface Value {
    Type getType();

    boolean equals(Object another);
}
