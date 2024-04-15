package com.example.a7.model.expressions;


import com.example.a7.model.MyException;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;

public class VarExp implements Exp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String,Value> symbolTable, MyIHeapDictionary<Value> heapTable) throws MyException {
        if (!symbolTable.isDefined(this.id)) {
            throw new MyException("Variable id not defined!");
        }
        return symbolTable.lookup(id);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
            return typeEnv.lookup(id);
    }

    @Override
    public String toString() {
        return "variable = " + this.id;
    }
}