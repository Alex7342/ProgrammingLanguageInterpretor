package com.example.a7;

import com.example.a7.controller.Controller;
import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.statements.IStmt;
import com.example.a7.model.types.Type;
import com.example.a7.model.values.StringValue;
import com.example.a7.model.values.Value;
import com.example.a7.repository.IRepository;
import com.example.a7.repository.MemoryRepository;
import com.example.a7.utils.*;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.util.*;

public class MainController {
    Map<String, IStmt> nameToProgram;

    public static Controller controller;

    @FXML
    private ListView<String> listView;

    @FXML
    protected void initialize() {
        nameToProgram = new HashMap<>();
        List<String> programsList = new ArrayList<>();

        programsList.add(Programs.program1().toString());
        nameToProgram.put(Programs.program1().toString(), Programs.program1());

        programsList.add(Programs.program2().toString());
        nameToProgram.put(Programs.program2().toString(), Programs.program2());

        programsList.add(Programs.program3().toString());
        nameToProgram.put(Programs.program3().toString(), Programs.program3());

        programsList.add(Programs.program4().toString());
        nameToProgram.put(Programs.program4().toString(), Programs.program4());

        programsList.add(Programs.program5().toString());
        nameToProgram.put(Programs.program5().toString(), Programs.program5());

        ObservableList<String> observableList = javafx.collections.FXCollections.observableArrayList(programsList);
        listView.setItems(observableList);
    }

    @FXML
    protected void onItemClicked() {
        String clickedProgram = listView.getSelectionModel().getSelectedItem();
        IStmt programToRun = nameToProgram.get(clickedProgram);

        /*try {
            programToRun.typeCheck(new MyDictionary<String, Type>());
        }
        catch (MyException e) {
            MainApplication.displayAlert(e.getMessage());
            return;
        }*/

        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIDictionary<String, Value> symbolTable = new MyDictionary<>();
        MyIList<Value> outputList = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeapDictionary<Value> heapTable = new MyHeap<>();

        PrgState prg = new PrgState(exeStack, symbolTable, outputList, fileTable, heapTable, programToRun);
        IRepository repo = new MemoryRepository("log.txt");
        repo.add(prg);

        controller = new Controller(repo);

        MainApplication.startMainWindow();
    }


}