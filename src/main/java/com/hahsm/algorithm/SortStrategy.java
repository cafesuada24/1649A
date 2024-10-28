package com.hahsm.algorithm;

import java.util.List;

public interface SortStrategy<T extends Comparable<T>> {

    public void sort(List<T> data);

    public void sort(T[] data);
}
