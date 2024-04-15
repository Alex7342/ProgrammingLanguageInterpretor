package com.example.a7.model.statements;



import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.expressions.Exp;
import com.example.a7.model.types.StringType;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.StringValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt {
    Exp exp;

    public OpenRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();
        Value expEval = this.exp.eval(symTable, heapTable);

        if (expEval.getType().equals(new StringType())) {
            StringValue expValue = (StringValue) expEval;

            if (!fileTable.isDefined(expValue)) {
                String fileName = expValue.getValue();
                BufferedReader bufferedReader = null;

                try {
                    bufferedReader = new BufferedReader(new FileReader(fileName));
                }
                catch (IOException exception) {
                    throw new MyException(exception.getMessage());
                }

                fileTable.put(expValue, bufferedReader);
            }
            else {
                throw new MyException("The file is already opejn! ");
            }
        }
        else {
            throw new MyException("The name of the file must be a string! ");
        }

        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expType = exp.typeCheck(typeEnv);
        if (expType.equals(new StringType())) {
            return typeEnv;
        }
        else {
            throw new MyException("CloseRFile error: Expression must be a string! ");
        }
    }

    @Override
    public IStmt deepcopy() {
        return new OpenRFile(this.exp);
    }

    @Override
    public String toString() {
        return "OpenRFile(" + this.exp.toString() + ") ";
    }
}
