package com.example.a7.controller;

import com.example.a7.MainWindowController;
import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;
import com.example.a7.model.values.RefValue;
import com.example.a7.model.values.Value;
import com.example.a7.repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    IRepository repo;
    ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public IRepository getRepository() {
        return this.repo;
    }

    private List<PrgState> removeCompletedPrograms(List<PrgState> programList) {
        return programList.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap) {
        return heap.entrySet().stream()
                .filter(e-> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapTableAddr, Map<Integer,Value> heap) {
        return heap.entrySet().stream()
                .filter(e-> symTableAddr.contains(e.getKey()) || heapTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private Collection<Value> joinAllSymTables(List<PrgState> programList) {
        List<Collection<Value>> list = programList.stream().map(prg -> prg.getSymTable().getMap().values()).collect(Collectors.toList());
        List<Value> col = new ArrayList<>(list.get(0).stream().toList());
        for (int i = 1; i < list.size(); i++)
            col.addAll(list.get(i).stream().toList());
        return col;
    }

    private List<Integer> getAddrFromHeapTable(Collection<Value> heapTableValues) {
        return heapTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    /*public void allSteps() throws MyException {
        PrgState state = this.repo.getCurrentPrgState();
        System.out.println(state);
        this.repo.logPrgStateExec();

        while (!state.getExeStack().isEmpty()) {
            this.oneStep(state);

            this.repo.logPrgStateExec();

            state.getHeapTable().setContent(safeGarbageCollector(
                    getAddrFromSymTable(state.getSymTable().getMap().values()),
                    getAddrFromHeapTable(state.getHeapTable().getMap().values()),
                    state.getHeapTable().getMap()
            ));

            this.repo.logPrgStateExec();
        }

        System.out.println(state);

        MyIList<Value> out = state.getOut();
        System.out.println("The output of the program: ");
        System.out.println(out + "\n");
    }*/

    public void oneStepForAllPrograms(List<PrgState> programList) throws InterruptedException {
        programList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });

        List<Callable<PrgState>> callList = programList.stream().map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();})).collect(Collectors.toList());

        List<PrgState> newProgramList = executor.invokeAll(callList).stream().map(future -> {
            try {
                return future.get();
            } catch (Exception exception) {
                try {
                    throw new MyException(exception.getMessage());
                } catch (MyException e) {
                    throw new RuntimeException(e);
                }
            }
        }).filter(p -> p != null).collect(Collectors.toList());

        programList.addAll(newProgramList);

        programList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });

        repo.setPrgList(programList);
    }

    public void executeOneStep() throws InterruptedException {
        List<PrgState> programList = removeCompletedPrograms(repo.getPrgList());

        if (!programList.isEmpty()) {
            oneStepForAllPrograms(programList);
            MainWindowController.lastExecutedState = programList.getFirst();

            PrgState state = programList.get(0);
            state.getHeapTable().setContent(safeGarbageCollector(
                    getAddrFromSymTable(joinAllSymTables(programList)),
                    getAddrFromHeapTable(state.getHeapTable().getMap().values()),
                    state.getHeapTable().getMap()
            ));

            programList = removeCompletedPrograms(programList);
            repo.setPrgList(programList);
        }
        else if (!executor.isShutdown()) {
            executor.shutdownNow();
        }
    }

    public void initExecutor() {
        executor = Executors.newFixedThreadPool(2);
    }

    public void allSteps() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programList = removeCompletedPrograms(repo.getPrgList());
        while (!programList.isEmpty()) {
            oneStepForAllPrograms(programList);

            PrgState state = programList.get(0);
            state.getHeapTable().setContent(safeGarbageCollector(
                    getAddrFromSymTable(joinAllSymTables(programList)),
                    getAddrFromHeapTable(state.getHeapTable().getMap().values()),
                    state.getHeapTable().getMap()
            ));

            programList = removeCompletedPrograms(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(programList);
    }

}
