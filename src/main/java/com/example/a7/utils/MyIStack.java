package com.example.a7.utils;
import java.util.List;

public interface MyIStack<T> {
    T pop();
    void push(T v);
    boolean isEmpty();
    List<T> reverse();
}
