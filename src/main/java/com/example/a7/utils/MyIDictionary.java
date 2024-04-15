package com.example.a7.utils;

import java.util.Map;

public interface MyIDictionary<K,V> {
    Map<K, V> getMap();
    void setMap(Map<K, V> newMap);
    V lookup(K key);
    boolean isDefined(K key);
    void put(K key, V value);
    void remove(K key);
    void update(K key, V value);

    MyIDictionary<K, V> deepcopy();
}
