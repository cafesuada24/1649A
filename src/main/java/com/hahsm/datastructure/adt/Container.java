package com.hahsm.datastructure.adt;

public interface Container<E> {
    void add(E value);
    void remove(Object o);
    int size();
    boolean isEmpty();
}


