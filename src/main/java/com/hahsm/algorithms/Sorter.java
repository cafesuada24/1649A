package com.hahsm.algorithms;

import java.util.Comparator;
import java.util.List;

public interface Sorter<T> {
    public void sort(List<T> data, Comparator<T> comparator);
}