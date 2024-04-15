package com.example.a7.model.expressions;


import com.example.a7.model.MyException;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;

public interface Exp {
    Value eval(MyIDictionary<String,Value> symbolTable, MyIHeapDictionary<Value> heapTable) throws MyException;
    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}