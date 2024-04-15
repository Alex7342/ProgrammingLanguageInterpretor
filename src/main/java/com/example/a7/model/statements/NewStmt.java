package com.example.a7.model.statements;


import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.expressions.Exp;
import com.example.a7.model.types.RefType;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.RefValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;

public class NewStmt implements IStmt {
    String variableName;
    Exp expression;

    public NewStmt(String variableName, Exp expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();

        if (!symbolTable.isDefined(this.variableName))
            throw new MyException("Variable not defined! ");

        if (!(symbolTable.lookup(this.variableName).getType() instanceof RefType))
            throw new MyException("Variable not of RefType! ");

        Value expEval = this.expression.eval(symbolTable, heapTable);
        Type expType = expEval.getType();

        RefValue variableValue = (RefValue) symbolTable.lookup(this.variableName);
        RefType variableType = (RefType) variableValue.getType();
        Type innerType = variableType.getInnerType();

        if (!expType.equals(innerType))
            throw new MyException("Inner type does not match with expression type! ");

        heapTable.add(expEval);

        RefValue newValue = new RefValue(heapTable.getCurrentAddress(), innerType);
        symbolTable.update(this.variableName, newValue);

        state.setSymTable(symbolTable);
        state.setHeapTable(heapTable);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(variableName);
        Type typeExp = expression.typeCheck(typeEnv);

        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        }
        else {
            throw new MyException("New statement error: Right hand side and left hand side have different types! ");
        }
    }

    @Override
    public IStmt deepcopy() {
        return new NewStmt(this.variableName, this.expression);
    }

    @Override
    public String toString() {
        return "New: " + this.variableName + ", " + this.expression + " ";
    }
}
