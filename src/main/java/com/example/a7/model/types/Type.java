package com.example.a7.model.types;

import com.example.a7.model.values.Value;

public interface Type {
    boolean equals(Object another);
    Value defaultValue();
}
