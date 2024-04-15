package com.example.a7.model.expressions;

import com.example.a7.model.MyException;
import com.example.a7.model.types.BoolType;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.BoolValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;

public class LogicExp implements Exp {
    Exp e1;
    Exp e2;
    int op;

    public LogicExp(Exp ex1, Exp ex2, int oper) {
        e1 = ex1;
        e2 = ex2;
        op = oper;
    }

    @Override
    public Value eval(MyIDictionary<String,Value> symbolTable, MyIHeapDictionary<Value> heapTable) throws MyException {
        Value v1 = this.e1.eval(symbolTable, heapTable);

        if (v1.getType().equals(new BoolType())) {
            Value v2 = this.e2.eval(symbolTable, heapTable);

            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;

                boolean n1 = b1.getVal();
                boolean n2 = b2.getVal();

                if (op == 1)
                    return new BoolValue(n1 && n2);

                if (op == 2)
                    return new BoolValue(n1 || n2);

                throw new MyException("Invalid expression operator!");
            }
            else
                throw new MyException("Second operand is not a boolean!");
        }
        else
            throw new MyException("First operand is not a boolean!");
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = e1.typeCheck(typeEnv);
        Type type2 = e2.typeCheck(typeEnv);

        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            }
            else {
                throw new MyException("Second operand is not boolean! ");
            }
        }
        else {
            throw new MyException("First operand is not boolean! ");
        }
    }

    @Override
    public String toString() {
        return switch(this.op) {
            case 1 -> this.e1 + " && " + this.e2;
            case 2 -> this.e1 + " || " + this.e2;
            default -> "Invalid logic expression! ";
        };
    }
}