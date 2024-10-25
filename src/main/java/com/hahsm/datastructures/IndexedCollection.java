package com.hahsm.datastructures;

public interface IndexedCollection<T> {
    public T get(int index);

    public T set(int index, T item);

    public int size();
}
