package com.example.a7.model.statements;


import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.expressions.Exp;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;
import com.example.a7.utils.MyIList;

public class PrintStmt implements IStmt {
    Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Value> outList = state.getOut();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();
        outList.add(this.exp.eval(symTable, heapTable));
        state.setOut(outList);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepcopy() {
        return new PrintStmt(this.exp);
    }

    @Override
    public String toString(){
        return "Print( " + exp.toString() + " ) ";
    }
}
