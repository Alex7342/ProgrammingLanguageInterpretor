package com.example.a7.model.expressions;


import com.example.a7.model.MyException;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;

public class ValueExp implements Exp {
    Value val;

    public ValueExp(Value v) {
        val = v;
    }

    @Override
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeapDictionary<Value> heapTable) throws MyException {
        return val;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return val.getType();
    }

    @Override
    public String toString() {
        return "Value :" + val;
    }
}