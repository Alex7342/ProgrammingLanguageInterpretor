package com.example.a7.utils;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<V> implements MyIHeapDictionary<V> {
    Map<Integer, V> map;
    static Integer currentAddress;

    public MyHeap() {
        this.map = new HashMap<>();
        currentAddress = 0;
    }

    @Override
    public Map<Integer, V> getMap() {
        return this.map;
    }

    @Override
    public void add(V value) {
        currentAddress++;
        this.map.put(currentAddress, value);
    }

    @Override
    public Integer getCurrentAddress() {
        return currentAddress;
    }

    @Override
    public boolean isDefined(Integer key) {
        return this.map.containsKey(key);
    }

    @Override
    public V getValue(Integer key) {
        return this.map.get(key);
    }

    @Override
    public void update(Integer key, V value) {
        this.map.put(key, value);
    }

    @Override
    public void delete(Integer key) {
        this.map.remove(key);
    }

    @Override
    public void setContent(Map<Integer, V> newMap) {
        this.map = newMap;
    }

    @Override
    public String toString() {
        return "HeapTable = { " + map + " }";
    }
}
