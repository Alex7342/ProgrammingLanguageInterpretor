package com.example.a7.model.statements;



import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.StringValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.*;

import java.io.BufferedReader;

public class ForkStmt implements IStmt {
    IStmt childStatement;

    public ForkStmt(IStmt childStatement) {
        this.childStatement = childStatement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String, Value> symTable = state.getSymTable().deepcopy();
        MyIList<Value> out = state.getOut();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();
        PrgState childState = new PrgState(exeStack, symTable, out, fileTable, heapTable, this.childStatement);
        return childState;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        childStatement.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepcopy() {
        return new ForkStmt(this.childStatement.deepcopy());
    }

    @Override
    public String toString() {
        return "ForkStmt(" + childStatement.toString() + ") ";
    }
}
