package com.example.a7.model.expressions;


import com.example.a7.model.MyException;
import com.example.a7.model.types.RefType;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.RefValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;

public class ReadHeapExp implements Exp {
    Exp expression;

    public ReadHeapExp(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Value> heapTable) throws MyException {
        Value expEval = this.expression.eval(symbolTable, heapTable);
        Type expType = expEval.getType();

        if (!(expType instanceof RefType))
            throw new MyException("Expression type not RefType! ");

        RefValue value = (RefValue) expEval;
        Integer address = value.getAddress();
        if (!heapTable.isDefined(address))
            throw new MyException("Address violation error! ");

        return heapTable.getValue(address);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = expression.typeCheck(typeEnv);
        if (type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInnerType();
        }
        else {
            throw new MyException("The read heap argument is not a RefType! ");
        }
    }

    @Override
    public String toString() {
        return "ReadHeap(" + this.expression.toString() + ") ";
    }
}
