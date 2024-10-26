package com.hahsm.datastructure;

public class ArrayWrapper<T> implements IndexedCollection<T> {

    private final T[] array;

    public ArrayWrapper(T[] array) {
        this.array = array;
    }

    @Override
    public T get(int index) {
        return array[index];
    }

    @Override
    public T set(int index, T item) {
        final T previous = array[index];
        array[index] = item;
        return previous;
    }

    @Override
    public int size() {
        return array.length;
    }

}
