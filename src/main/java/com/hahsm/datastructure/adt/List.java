package com.hahsm.datastructure.adt;

public interface List<T> extends Container<T> {
    void add(int index, T element);
    void add(List<T> newElements);
    T remove(int index);
    T set(int index, T element);
    T get(int index);
    void sort();
}
