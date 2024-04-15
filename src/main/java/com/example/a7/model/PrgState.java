package com.example.a7.model;


import com.example.a7.model.statements.IStmt;
import com.example.a7.model.values.StringValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;
import com.example.a7.utils.MyIList;
import com.example.a7.utils.MyIStack;

import java.io.BufferedReader;

public class PrgState {
    private static int stateId = 0;
    private int threadId;
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    MyIHeapDictionary<Value> heapTable;
    IStmt originalProgram;

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeapDictionary<Value> heapTable, IStmt originalProgram) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        //this.originalProgram = deepCopy(originalProgram);
        exeStack.push(originalProgram);

        increment();
        this.threadId = stateId;
    }

    private static synchronized void increment() {
        stateId++;
    }

    public int getThreadId() {
        return this.threadId;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public MyIHeapDictionary<Value> getHeapTable() {
        return this.heapTable;
    }

    public void setHeapTable(MyIHeapDictionary<Value> heapTable) {
        this.heapTable = heapTable;
    }

    @Override
    public String toString() {
        return "PrgState = {\n" +
                "ID = " + threadId + "\n" +
                "exeStack = " + exeStack + "\n" +
                "symTable = " + symTable + "\n" +
                "out = " + out + "\n" +
                "heapTable = " + heapTable +
                " \n}\n";
    }

    public boolean isNotCompleted() {
        return !this.exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if (exeStack.isEmpty())
            throw new MyException("The execution stack is empty! ");
        else {
            IStmt currentStmt = exeStack.pop();
            return currentStmt.execute(this);
        }
    }

}
