package com.example.a7;

import com.example.a7.controller.Controller;
import com.example.a7.model.PrgState;
import com.example.a7.model.statements.IStmt;
import com.example.a7.model.values.IntValue;
import com.example.a7.model.values.StringValue;
import com.example.a7.model.values.Value;
import com.example.a7.repository.IRepository;
import com.example.a7.utils.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MainWindowController {
    public static PrgState lastExecutedState;
    private Controller controller;
    private IRepository repository;

    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private ListView<String> outputTableListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<String> programStatesListView;

    @FXML
    private TableView<Pair<Integer, Value>> heapTableTableView;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> addressColumn;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> valueColumn;

    @FXML
    private TextField numberOfProgramStatesTextField;

    private String numberOfProgramStates() {
        return "Number of program states: " + this.repository.getPrgList().size();
    }

    private void initializeNumberOfProgramStatesTextField() {
        numberOfProgramStatesTextField.setText(this.numberOfProgramStates());
    }

    private void initializeProgramStatesListView() {
        List<String> programsList = new ArrayList<>();
        programsList.add(this.repository.getPrgList().getFirst().toString());
        ObservableList<String> observableList = javafx.collections.FXCollections.observableArrayList(programsList);
        programStatesListView.setItems(observableList);
    }

    private void initializeExeStackListView() {
        MyIStack<IStmt> stack = this.repository.getPrgList().getFirst().getExeStack();
        Vector<IStmt> toPrint = new Vector<>();
        List<String> exeStackList = new ArrayList<>();

        while (!stack.isEmpty())
            toPrint.add(stack.pop().deepcopy());

        for (IStmt i : toPrint)
            exeStackList.add(i.toString());

        for (int i = toPrint.size() - 1; i >= 0; i--)
            stack.push(toPrint.get(i).deepcopy());

        ObservableList<String> observableList = javafx.collections.FXCollections.observableArrayList(exeStackList);
        exeStackListView.setItems(observableList);
    }

    private void updateOutputTableListView() {
        PrgState selectedState;

        if (this.repository.getPrgList().isEmpty())
            selectedState = lastExecutedState;
        else
            selectedState = this.repository.getPrgList().getFirst();

        MyIList<Value> outputList = selectedState.getOut();
        List<String> toUpdateList = new ArrayList<>();
        for (int i = 0; i < outputList.getList().size(); i++) {
            toUpdateList.add(outputList.getList().get(i).toString());
        }

        ObservableList<String> observableList = javafx.collections.FXCollections.observableArrayList(toUpdateList);
        outputTableListView.setItems(observableList);
    }

    private void updateExeStackListView() {
        if (this.repository.getPrgList().isEmpty()) {
            ObservableList<String> observableList = javafx.collections.FXCollections.observableArrayList(new ArrayList<>());
            exeStackListView.setItems(observableList);
            return;
        }

        int selectedStateIndex = this.programStatesListView.getSelectionModel().getSelectedIndex();
        PrgState selectedState;

        if (selectedStateIndex == -1)
            selectedState = this.repository.getPrgList().getFirst();
        else
            selectedState = this.repository.getPrgList().get(selectedStateIndex);


        MyIStack<IStmt> stack = selectedState.getExeStack();
        Vector<IStmt> toPrint = new Vector<>();
        List<String> exeStackList = new ArrayList<>();

        while (!stack.isEmpty())
            toPrint.add(stack.pop().deepcopy());

        for (IStmt i : toPrint)
            exeStackList.add(i.toString());

        for (int i = toPrint.size() - 1; i >= 0; i--)
            stack.push(toPrint.get(i).deepcopy());

        ObservableList<String> observableList = javafx.collections.FXCollections.observableArrayList(exeStackList);
        exeStackListView.setItems(observableList);
    }

    private void updateFileTableListView() {
        PrgState selectedState;

        if (this.repository.getPrgList().isEmpty())
            selectedState = lastExecutedState;
        else
            selectedState = this.repository.getPrgList().getFirst();

        MyIDictionary<StringValue, BufferedReader> fileTable = selectedState.getFileTable();
        Map<StringValue, BufferedReader> map = fileTable.getMap();
        List<String> fileTableList = new ArrayList<>();

        map.forEach((K, V) -> {
            fileTableList.add(K.toString());
        });

        ObservableList<String> observableList = javafx.collections.FXCollections.observableArrayList(fileTableList);
        fileTableListView.setItems(observableList);
    }

    private void updateProgramStatesListView() {
        List<String> programsList = new ArrayList<>();

        for (int i = 0; i < this.repository.getPrgList().size(); i++)
            programsList.add(this.repository.getPrgList().get(i).toString());

        ObservableList<String> observableList = javafx.collections.FXCollections.observableArrayList(programsList);
        programStatesListView.setItems(observableList);
    }

    private void updateNumberOfProgramStatesTextField() {
        numberOfProgramStatesTextField.setText(this.numberOfProgramStates());
    }

    private void updateHeapTableTableView() {
        heapTableTableView.getItems().clear();


        PrgState selectedState;

        if (this.repository.getPrgList().isEmpty())
            selectedState = lastExecutedState;
        else
            selectedState = this.repository.getPrgList().getFirst();

        MyIHeapDictionary<Value> heapTable = selectedState.getHeapTable();
        Map<Integer, Value> map = heapTable.getMap();

        map.forEach((K, V) -> {
            Pair<Integer, Value> pair = new Pair<>(K, V);
            heapTableTableView.getItems().add(pair);
        });
    }

    @FXML
    protected void initialize() {
        controller = MainController.controller;
        repository = controller.getRepository();

        this.initializeNumberOfProgramStatesTextField();
        this.initializeProgramStatesListView();
        this.initializeExeStackListView();
        this.controller.initExecutor();

        addressColumn.setCellValueFactory(new PropertyValueFactory<Pair<Integer, Value>, String>("first"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Pair<Integer, Value>, String>("second"));
    }

    @FXML
    protected void onListItemSelection() {
        updateOutputTableListView();
        updateExeStackListView();
        updateFileTableListView();
        updateNumberOfProgramStatesTextField();
    }

    @FXML
    protected void onButtonClick() {
        try {
            this.controller.executeOneStep();
        }
        catch (InterruptedException e) {
            MainApplication.displayAlert(e.getMessage());
            return;
        }


        updateOutputTableListView();
        updateExeStackListView();
        updateFileTableListView();
        updateProgramStatesListView();
        updateHeapTableTableView();
        updateNumberOfProgramStatesTextField();
    }
}
