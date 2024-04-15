package com.example.a7.repository;

import com.example.a7.model.MyException;
import com.example.a7.model.PrgState;

import java.util.List;

public interface IRepository {
    void add(PrgState programState);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newPrgList);
    void logPrgStateExec(PrgState programToLog) throws MyException;
}
