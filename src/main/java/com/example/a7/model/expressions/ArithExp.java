package com.example.a7.model.expressions;


import com.example.a7.model.MyException;
import com.example.a7.model.types.IntType;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.IntValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;

public class ArithExp implements Exp {
    Exp e1;
    Exp e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(Exp ex1, Exp ex2, int oper) {
        e1 = ex1;
        e2 = ex2;
        op = oper;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Value> heapTable) throws MyException {
        Value v1 = e1.eval(symbolTable, heapTable);

        if (v1.getType().equals(new IntType())) {
            Value v2 = e2.eval(symbolTable, heapTable);

            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();

                if (op == 1)
                    return new IntValue(n1 + n2);

                if (op == 2)
                    return new IntValue(n1 - n2);

                if (op == 3)
                    return new IntValue(n1 * n2);

                if (op == 4) {
                    if (n2 == 0)
                        throw new MyException("Division by zero");
                    else
                        return new IntValue(n1 / n2);
                }

                throw new MyException("Invalid expression operator! ");
            }
            else
                throw new MyException("Second operand is not an integer! ");
        }
        else
            throw new MyException("First operand is not an integer! ");
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = e1.typeCheck(typeEnv);
        Type type2 = e2.typeCheck(typeEnv);

        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            }
            else {
                throw new MyException("Second operand is not an integer! ");
            }
        }
        else {
            throw new MyException("First operand is not an integer! ");
        }
    }

    @Override
    public String toString() {
        return switch (this.op) {
            case 1 -> this.e1 + " + " + this.e2;
            case 2 -> this.e1 + " - " + this.e2;
            case 3 -> this.e1 + " * " + this.e2;
            case 4 -> this.e1 + " / " + this.e2;
            default -> "Invalid arithmetic expression! ";
        };
    }
}