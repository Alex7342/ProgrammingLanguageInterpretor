package com.example.a7.utils;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements MyIDictionary<K,V>{
    protected Map<K,V> map;

    public MyDictionary() {
        map = new HashMap<>();
    }

    @Override
    public Map<K, V> getMap() {
        return this.map;
    }

    @Override
    public void setMap(Map<K, V> newMap) {
        this.map = newMap;
    }

    @Override
    public V lookup(K key) {
        return map.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return map.get(key) != null;
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void remove(K key) {
        this.map.remove(key);
    }

    @Override
    public void update(K key, V value) {
        map.put(key, value);
    }

    public Map<K, V> cloneMap() {
        return new HashMap<>(this.map);
    }

    @Override
    public MyIDictionary<K, V> deepcopy() {
        MyIDictionary<K, V> copy = new MyDictionary<K, V>();
        copy.setMap(this.cloneMap());
        return copy;
    }

    @Override
    public String toString() {
        return "Dictionary = { " + map + " } ";
    }
}
