package com.hahsm.algorithm;

import java.util.Comparator;

import com.hahsm.datastructure.adt.List;

public interface SortStrategy {
    public <T extends Comparable<T>> void sort(List<T> data);
    public <T> void sort(List<T> data, Comparator<T> comparator);
}
