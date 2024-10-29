package com.hahsm.datastructure.adt;

public interface List<T> extends Container<T> {
    void add(int index, T element);
    void sort();
}
