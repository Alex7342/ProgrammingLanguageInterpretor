package com.example.a7;

import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.expressions.*;
import com.example.a7.model.statements.*;
import com.example.a7.model.types.IntType;
import com.example.a7.model.types.RefType;
import com.example.a7.model.types.StringType;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.IntValue;
import com.example.a7.model.values.StringValue;
import com.example.a7.model.values.Value;
import com.example.a7.repository.IRepository;
import com.example.a7.repository.MemoryRepository;
import com.example.a7.utils.*;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {
        //View view = new View();
        //view.mainLoop();

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));

        MyIStack<IStmt> exeStack1 = new MyStack<>();
        MyIDictionary<String, Value> symbolTable1 = new MyDictionary<>();
        MyIList<Value> outputList1 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable1 = new MyDictionary<>();
        MyIHeapDictionary<Value> heapTable1 = new MyHeap<>();

        try {
            MyDictionary<String, Type> d = new MyDictionary<String, Type>();
            ex1.typeCheck(d);
        }
        catch (MyException error) {
            System.out.println(error.getMessage());
        }

        PrgState prg1 = new PrgState(exeStack1, symbolTable1, outputList1, fileTable1, heapTable1, ex1);
        IRepository repo1 = new MemoryRepository("log1.txt");
        repo1.add(prg1);

        //Controller controller1 = new Controller(repo1);


        IStmt ex2 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenRFile(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFile(new VarExp("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        MyIStack<IStmt> exeStack2 = new MyStack<>();
        MyIDictionary<String, Value> symbolTable2 = new MyDictionary<>();
        MyIList<Value> outputList2 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable2 = new MyDictionary<>();
        MyIHeapDictionary<Value> heapTable2 = new MyHeap<>();

        try {
            ex2.typeCheck(new MyDictionary<String, Type>());
        }
        catch (MyException error) {
            System.out.println(error.getMessage());
        }

        PrgState prg2 = new PrgState(exeStack2, symbolTable2, outputList2, fileTable2, heapTable2, ex2);
        IRepository repo2 = new MemoryRepository("log2.txt");
        repo2.add(prg2);

        //Controller controller2 = new Controller(repo2);


        IStmt ex3 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
                                        )
                                )
                        )
                )
        );

        MyIStack<IStmt> exeStack3 = new MyStack<>();
        MyIDictionary<String, Value> symbolTable3 = new MyDictionary<>();
        MyIList<Value> outputList3 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable3 = new MyDictionary<>();
        MyIHeapDictionary<Value> heapTable3 = new MyHeap<>();

        try {
            ex3.typeCheck(new MyDictionary<String, Type>());
        }
        catch (MyException error) {
            System.out.println(error.getMessage());
        }

        PrgState prg3 = new PrgState(exeStack3, symbolTable3, outputList3, fileTable3, heapTable3, ex3);
        IRepository repo3 = new MemoryRepository("log3.txt");
        repo3.add(prg3);

        //Controller controller3 = new Controller(repo3);


        IStmt ex4 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new CompStmt(
                                    new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), 5)),
                                    new CompStmt(
                                            new PrintStmt(new VarExp("v")),
                                            new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 2))
                                    )
                                ),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );

        MyIStack<IStmt> exeStack4 = new MyStack<>();
        MyIDictionary<String, Value> symbolTable4 = new MyDictionary<>();
        MyIList<Value> outputList4 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable4 = new MyDictionary<>();
        MyIHeapDictionary<Value> heapTable4 = new MyHeap<>();

        try {
            ex4.typeCheck(new MyDictionary<String, Type>());
        }
        catch (MyException error) {
            System.out.println(error.getMessage());
        }

        PrgState prg4 = new PrgState(exeStack4, symbolTable4, outputList4, fileTable4, heapTable4, ex4);
        IRepository repo4 = new MemoryRepository("log4.txt");
        repo4.add(prg4);

        //Controller controller4 = new Controller(repo4);


        IStmt ex5 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(new CompStmt(
                                                        new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                        new CompStmt(
                                                                new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                new CompStmt(
                                                                    new PrintStmt(new VarExp("v")),
                                                                    new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                                )
                                                        )
                                                )),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        MyIStack<IStmt> exeStack5 = new MyStack<>();
        MyIDictionary<String, Value> symbolTable5 = new MyDictionary<>();
        MyIList<Value> outputList5 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable5 = new MyDictionary<>();
        MyIHeapDictionary<Value> heapTable5 = new MyHeap<>();

        try {
            ex5.typeCheck(new MyDictionary<String, Type>());
        }
        catch (MyException error) {
            System.out.println(error.getMessage());
        }

        PrgState prg5 = new PrgState(exeStack5, symbolTable5, outputList5, fileTable5, heapTable5, ex5);
        IRepository repo5 = new MemoryRepository("log5.txt");
        repo5.add(prg5);

        //Controller controller5 = new Controller(repo5);


        /*TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), controller1));
        menu.addCommand(new RunExample("2", ex2.toString(), controller2));
        menu.addCommand(new RunExample("3", ex3.toString(), controller3));
        menu.addCommand(new RunExample("4", ex4.toString(), controller4));
        menu.addCommand(new RunExample("5", ex5.toString(), controller5));
        menu.show();*/
    }
}
