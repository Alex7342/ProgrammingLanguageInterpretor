package com.example.a7.model.statements;


import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.expressions.Exp;
import com.example.a7.model.types.IntType;
import com.example.a7.model.types.StringType;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.IntValue;
import com.example.a7.model.values.StringValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    Exp exp;
    String var_name;

    public ReadFile(Exp exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();
        Value expEval = this.exp.eval(symTable, heapTable);

        if (!symTable.isDefined(this.var_name))
            throw new MyException("Variable not defined! ");

        Value var = symTable.lookup(this.var_name);
        if (!var.getType().equals(new IntType()))
            throw new MyException("The variable must be of type int! ");

        if (!expEval.getType().equals(new StringType()))
            throw new MyException("The file name must be a string! ");

        StringValue expValue = (StringValue) expEval;
        BufferedReader bufferedReader = fileTable.lookup(expValue);

        String readLine;
        try {
            readLine = bufferedReader.readLine();
        }
        catch (IOException exception) {
            throw new MyException(exception.getMessage());
        }

        int valueRead = 0;
        if (readLine != null) {
            valueRead = Integer.parseInt(readLine);
        }

        symTable.update(this.var_name, new IntValue(valueRead));
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expType = exp.typeCheck(typeEnv);
        Type varType = typeEnv.lookup(var_name);

        if (expType.equals(new StringType())) {
            if (varType != null) {
                return typeEnv;
            }
            else {
                throw new MyException("ReadFile error: variable does not exist! ");
            }
        }
        else {
            throw new MyException("ReadFile error: Expression must be a string! ");
        }
    }

    @Override
    public IStmt deepcopy() {
        return new ReadFile(this.exp, this.var_name);
    }

    @Override
    public String toString() {
        return "ReadFile(" + this.exp.toString() + this.var_name + ") ";
    }
}
