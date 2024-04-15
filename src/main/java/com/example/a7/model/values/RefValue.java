package com.example.a7.model.values;


import com.example.a7.model.types.RefType;
import com.example.a7.model.types.Type;

public class RefValue implements Value {
    int locationAddress;
    Type locationType;

    public RefValue(int address, Type type) {
        this.locationAddress = address;
        this.locationType = type;
    }

    public int getAddress() {
        return this.locationAddress;
    }

    public Type getType() {
        return new RefType(locationType);
    }

    public String toString() {
        return "RefValue(address = " + this.locationAddress + ", " + "type = " + this.locationType.toString() + ") ";
    }
}
