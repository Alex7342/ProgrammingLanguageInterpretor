package com.example.a7.utils;
import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    List<T> list;

    public MyList() {
        list = new ArrayList<>();
    }

    @Override
    public List<T> getList() {
        return this.list;
    }
    @Override
    public void add(T e) {
        list.add(e);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public String toString() {
        return "List = { " + list + " } ";
    }
}
