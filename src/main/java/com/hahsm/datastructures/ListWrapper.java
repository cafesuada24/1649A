package com.hahsm.datastructures;

import java.util.List;

public class ListWrapper<T> implements IndexedCollection<T> {

    private final List<T> list;

    public ListWrapper(List<T> list) {
        this.list = list;
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T item) {
        return list.set(index, item);
    }

    @Override
    public int size() {
        return list.size();
    }

}
