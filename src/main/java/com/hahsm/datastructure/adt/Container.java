package com.hahsm.datastructure.adt;

public interface Container<E> {
    void add(E element);
    boolean remove(E target);
    boolean contains(E target);
    int size();
    boolean isEmpty();
    void clear();
}


