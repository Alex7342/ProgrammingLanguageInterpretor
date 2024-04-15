package com.example.a7.repository;


import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.statements.IStmt;
import com.example.a7.model.values.StringValue;
import com.example.a7.model.values.Value;
import com.example.a7.utils.MyIDictionary;
import com.example.a7.utils.MyIHeapDictionary;
import com.example.a7.utils.MyIList;
import com.example.a7.utils.MyIStack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MemoryRepository implements IRepository {
    List<PrgState> programStateList;
    String logFilePath;

    public MemoryRepository(String logFilePath) {
        programStateList = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public void add(PrgState programState) {
        programStateList.add(programState);
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.programStateList;
    }

    @Override
    public void setPrgList(List<PrgState> newPrgList) {
        this.programStateList = newPrgList;
    }

    private void printStack(PrintWriter writer, PrgState state) {
        MyIStack<IStmt> stack = state.getExeStack();
        Vector<IStmt> toPrint = new Vector<>();

        while (!stack.isEmpty()) {
            toPrint.add(stack.pop().deepcopy());
        }

        writer.println("ExeStack: ");
        for (IStmt i : toPrint) {
            writer.println(i);
        }
        writer.println();

        for (int i = toPrint.size() - 1; i >= 0; i--)
            stack.push(toPrint.get(i).deepcopy());
    }

    private void printSymbolTable(PrintWriter writer, PrgState state) {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        Map<String, Value> map = symTable.getMap();

        writer.println("SymbolTable: ");
        map.forEach((K, V) -> {
            writer.println(K + " --> " + V.toString());
        });
        writer.println();
    }

    private void printOutput(PrintWriter writer, PrgState state) {
        MyIList<Value> output = state.getOut();
        List<Value> outputList = output.getList();

        writer.println("Output: ");
        for (Value v : outputList) {
            writer.println(v.toString());
        }
        writer.println();
    }

    private void printFileTable(PrintWriter writer, PrgState state) {
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        Map<StringValue, BufferedReader> map = fileTable.getMap();

        writer.println("FileTable: ");
        map.forEach((K, V) -> {
            writer.println(K.toString() + " --> " + V.toString());
        });
        writer.println();
    }

    private void printHeapTable(PrintWriter writer, PrgState state) {
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();
        Map<Integer, Value> map = heapTable.getMap();

        writer.println("HeapTable: ");
        map.forEach((K, V) -> {
            writer.println(K.toString() + " --> " + V.toString());
        });
        writer.println();
    }

    private void printThreadId(PrintWriter writer, PrgState state) {
        writer.println("ID: " + state.getThreadId());
        writer.println();
    }

    @Override
    public void logPrgStateExec(PrgState programToLog) throws MyException {
        PrintWriter logFile = null;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        }
        catch (IOException exception) {
            throw new MyException(exception.getMessage());
        }

        printThreadId(logFile, programToLog);
        printStack(logFile, programToLog);
        printSymbolTable(logFile, programToLog);
        printOutput(logFile, programToLog);
        printFileTable(logFile, programToLog);
        printHeapTable(logFile, programToLog);
        logFile.println("-----------------------------------------------------");
        logFile.flush();
    }

    @Override
    public String toString() {
        return "Repository {" +
                "programStateList = " + programStateList +
                " }";
    }
}
