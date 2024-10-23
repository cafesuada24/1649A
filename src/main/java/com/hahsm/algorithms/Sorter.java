package com.hahsm.algorithms;

import java.util.List;

public interface Sorter<T extends Comparable<T>> {
    public void sort(List<T> data);

    public void sort(T[] data);
}