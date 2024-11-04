package com.hahsm.datastructure.adt;

public interface Map<K, V> {
    void clear();
    boolean containsKey(K key);
    boolean containsValue(V value);
    boolean put(K key, V value);
    boolean isEmpty();
    V get(K key);
    V remove(K key);
    int size();
}
