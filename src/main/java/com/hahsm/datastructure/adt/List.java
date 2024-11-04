package com.hahsm.datastructure.adt;

import com.hahsm.algorithm.Search;

public interface List<T> extends Container<T> {
    void add(int index, T element);
    void addAll(List<T> newElements);
    T remove(int index);
    T set(int index, T element);
    T get(int index);
    int indexOf(T target);
    void setSearchStrategy(Search strategy);
}
