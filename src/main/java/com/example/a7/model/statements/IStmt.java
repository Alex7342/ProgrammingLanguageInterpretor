package com.example.a7.model.statements;


import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.types.Type;
import com.example.a7.utils.MyIDictionary;

public interface IStmt{
    PrgState execute(PrgState state) throws MyException;
        //which is the execution method for a statement.

    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;

    IStmt deepcopy();
}
