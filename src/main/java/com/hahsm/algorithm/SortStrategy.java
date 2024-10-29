package com.hahsm.algorithm;

import com.hahsm.datastructure.adt.List;

public interface SortStrategy<T extends Comparable<T>> {

    public void sort(List<T> data);
}
