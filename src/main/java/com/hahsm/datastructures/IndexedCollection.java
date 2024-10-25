package com.hahsm.datastructures;

public interface IndexedCollection<T> {
    public T get(int index);

    public void set(int index, T item);

    public int size();
}
