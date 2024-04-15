package com.example.a7.utils;

import java.util.List;

public interface MyIList<T> {
    List<T> getList();
    void add(T e);
    void clear();
}
