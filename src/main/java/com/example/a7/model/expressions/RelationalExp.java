package com.example.a7.model.expressions;


import com.example.a7.model.MyException;
import com.example.a7.model.types.BoolType;
import com.example.a7.model.types.IntType;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.BoolValue;
import com.example.a7.model.values.IntValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;

public class RelationalExp implements  Exp {
    Exp expression1;
    Exp expression2;
    int operator;

    public RelationalExp(Exp expression1, Exp expression2, int operator) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }


    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Value> heapTable) throws MyException {
        if (!expression1.eval(symbolTable, heapTable).getType().equals(new IntType()))
            throw new MyException("Expression 1 is not an integer! ");

        if (!expression2.eval(symbolTable, heapTable).getType().equals(new IntType()))
            throw new MyException("Expression 2 is not an integer! ");

        if (this.operator < 1 || this.operator > 6)
            throw new MyException("Invalid operator! ");

        Value val1 = expression1.eval(symbolTable, heapTable);
        Value val2 = expression2.eval(symbolTable, heapTable);

        IntValue intValue1 = (IntValue) val1;
        IntValue intValue2 = (IntValue) val2;

        BoolValue returnValue = null;

        switch (this.operator) {
            case 1:
                if (intValue1.getVal() < intValue2.getVal())
                    returnValue = new BoolValue(true);
                else
                    returnValue = new BoolValue(false);
                break;

            case 2:
                if (intValue1.getVal() <= intValue2.getVal())
                    returnValue = new BoolValue(true);
                else
                    returnValue = new BoolValue(false);
                break;

            case 3:
                if (intValue1.getVal() == intValue2.getVal())
                    returnValue = new BoolValue(true);
                else
                    returnValue = new BoolValue(false);
                break;

            case 4:
                if (intValue1.getVal() != intValue2.getVal())
                    returnValue = new BoolValue(true);
                else
                    returnValue = new BoolValue(false);
                break;

            case 5:
                if (intValue1.getVal() > intValue2.getVal())
                    returnValue = new BoolValue(true);
                else
                    returnValue = new BoolValue(false);
                break;

            case 6:
                if (intValue1.getVal() >= intValue2.getVal())
                    returnValue = new BoolValue(true);
                else
                    returnValue = new BoolValue(false);
                break;

            default:
                returnValue = new BoolValue(false);
                break;
        }

        return returnValue;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (operator < 1 || operator > 6)
            throw new MyException("Invalid operator in relational expression! ");

        Type type1 = expression1.typeCheck(typeEnv);
        Type type2 = expression2.typeCheck(typeEnv);

        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
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
        return switch (this.operator) {
            case 1 -> this.expression1 + " < " + this.expression2;
            case 2 -> this.expression1 + " <= " + this.expression2;
            case 3 -> this.expression1 + " == " + this.expression2;
            case 4 -> this.expression1 + " != " + this.expression2;
            case 5 -> this.expression1 + " > " + this.expression2;
            case 6 -> this.expression1 + " >= " + this.expression2;
            default -> "Invalid relational expression! ";
        };
    }
}
