package com.example.a7.model.statements;


import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.expressions.Exp;
import com.example.a7.model.types.BoolType;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.BoolValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;
import com.example.a7.utils.MyIStack;

public class WhileStmt implements IStmt {
    Exp expression;

    public WhileStmt(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack = state.getExeStack();
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();

        Value expEval = this.expression.eval(symbolTable, heapTable);
        Type expType = expEval.getType();

        if (!expType.equals(new BoolType()))
            throw new MyException("While expression not boolean! ");

        BoolValue expValue = (BoolValue) expEval;

        if (expValue.getVal()) {
            IStmt toExecute = exeStack.pop();

            exeStack.push(toExecute.deepcopy());
            exeStack.push(this.deepcopy());
            exeStack.push(toExecute);
        }
        else {
            exeStack.pop();
        }

        state.setExeStack(exeStack);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type expType = expression.typeCheck(typeEnv);

        if (expType.equals(new BoolType())) {
            return typeEnv;
        }
        else {
            throw new MyException("The while condition must be of bool type! ");
        }
    }

    @Override
    public IStmt deepcopy() {
        return new WhileStmt(this.expression);
    }

    @Override
    public String toString() {
        return "While (" + this.expression + ")";
    }
}
