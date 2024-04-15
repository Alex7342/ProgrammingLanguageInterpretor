package com.example.a7.model.statements;


import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.expressions.Exp;
import com.example.a7.model.types.RefType;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.RefValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;

public class WriteHeapStmt implements IStmt {
    String variableName;
    Exp expression;

    public WriteHeapStmt(String variableName, Exp expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();

        if (!symbolTable.isDefined(this.variableName))
            throw new MyException("Variable not defined! ");

        if (!(symbolTable.lookup(this.variableName).getType() instanceof RefType))
            throw new MyException("Variable not of RefType! ");

        Value variableValue = symbolTable.lookup(variableName);
        RefType variableType = (RefType) variableValue.getType();
        Integer address = ((RefValue) variableValue).getAddress();
        if (!heapTable.isDefined(address))
            throw new MyException("Address not defined! ");

        Value expEval = this.expression.eval(symbolTable, heapTable);
        Type expType = expEval.getType();

        if (!variableType.getInnerType().equals(expType))
            throw new MyException("Location type and expression type do not match! ");

        heapTable.update(address, expEval);
        state.setHeapTable(heapTable);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(variableName);
        Type typeExp = expression.typeCheck(typeEnv);

        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        }
        else {
            throw new MyException("WriteHeap statement error: Variable must be of reference type! ");
        }
    }

    @Override
    public IStmt deepcopy() {
        return new WriteHeapStmt(this.variableName, this.expression);
    }

    @Override
    public String toString() {
        return "WriteHeap: " + this.variableName + " = " + this.expression;
    }
}
