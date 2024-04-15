package com.example.a7.model.statements;


import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.expressions.Exp;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;
import com.example.a7.utils.MyIStack;

public class AssignStmt implements IStmt {
    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();
        if (symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl, heapTable);
            Type typId = (symTbl.lookup(id)).getType();
            if ((val.getType()).equals(typId))
                symTbl.update(id, val);
            else
                throw new MyException("declared type of variable " + id + " and type of the assigned expression do not match");
        } else throw new MyException("the used variable " + id + " was not declared before");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = exp.typeCheck(typeEnv);

        if (typeVar.equals(typeExp)) {
            return typeEnv;
        }
        else {
            throw new MyException("Assignment error: Right hand side and left hand side have different types! ");
        }
    }

    @Override
    public IStmt deepcopy() {
        return new AssignStmt(this.id, this.exp);
    }

    @Override
    public String toString() {
        return "Assignment: " + id + " = " + exp.toString() + " ";
    }
}