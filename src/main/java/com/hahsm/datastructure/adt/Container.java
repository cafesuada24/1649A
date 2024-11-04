package com.hahsm.datastructure.adt;

public interface Container<E> {
    void add(E element);
    boolean remove(Object o);
    int size();
    boolean isEmpty();
}


