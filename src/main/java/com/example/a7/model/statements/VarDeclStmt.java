package com.example.a7.model.statements;


import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.types.*;
import com.example.a7.model.values.BoolValue;
import com.example.a7.model.values.IntValue;
import com.example.a7.model.values.StringValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;

public class VarDeclStmt implements IStmt {
    String name;
    Type typ;

    public VarDeclStmt(String name, Type typ) {
        this.name = name;
        this.typ = typ;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();

        if (symTable.isDefined(this.name)) {
            throw new MyException("The variable is already defined! (VarDeclStmt) ");
        }
        else {
            if (typ.equals(new IntType())) {
                symTable.put(name, new IntValue(0));
            }
            else if (typ.equals(new BoolType())) {
                symTable.put(name, new BoolValue(false));
            }
            else if (typ.equals(new StringType())) {
                symTable.put(name, new StringValue(""));
            }
            else if (typ instanceof RefType) {
                symTable.put(name, typ.defaultValue());
            }
            else
                throw new MyException("Variable type doesn't exist! ");
        }
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name, typ);
        return typeEnv;
    }

    @Override
    public IStmt deepcopy() {
        return new VarDeclStmt(this.name, this.typ);
    }

    @Override
    public String toString() {
        return "Declaration: " + this.name + ", " + this.typ + " ";
    }
}