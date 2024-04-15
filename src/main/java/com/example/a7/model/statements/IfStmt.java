package com.example.a7.model.statements;


import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.expressions.Exp;
import com.example.a7.model.types.BoolType;
import com.example.a7.model.types.IntType;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.BoolValue;
import com.example.a7.model.values.IntValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {
        exp = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        Value expEval = this.exp.eval(state.getSymTable(), state.getHeapTable());

        if (expEval.getType().equals(new BoolType())) {
            BoolValue bv = (BoolValue) expEval;

            if (bv.getVal()) {
                return this.thenS.execute(state);
            }
            else {
                return this.elseS.execute(state);
            }
        }
        else if (expEval.getType().equals(new IntType())) {
            IntValue iv = (IntValue) expEval;

            if (iv.getVal() != 0) {
                return this.thenS.execute(state);
            }
            else {
                return this.elseS.execute(state);
            }
        }
        else
            throw new MyException("Invalid if expression type! ");
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            thenS.typeCheck(typeEnv.deepcopy());
            thenS.typeCheck(typeEnv.deepcopy());
            return typeEnv;
        }
        else {
            throw new MyException("The if condition must be of bool type! ");
        }
    }

    @Override
    public IStmt deepcopy() {
        return new IfStmt(this.exp, this.thenS.deepcopy(), this.elseS.deepcopy());
    }

    @Override
    public String toString() {
        return "If ( " + this.exp + " ) " + " Then { " + this.thenS + " } Else { " + this.elseS + " } ";
    }
}