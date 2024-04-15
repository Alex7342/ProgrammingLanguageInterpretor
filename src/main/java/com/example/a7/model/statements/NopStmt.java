package com.example.a7.model.statements;


import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.types.Type;
import com.example.a7.utils.MyIDictionary;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepcopy() {
        return new NopStmt();
    }

    @Override
    public String toString() {
        return "NOP ";
    }
}