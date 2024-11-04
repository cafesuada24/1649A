package com.hahsm.algorithm;

import java.util.Comparator;

import com.hahsm.datastructure.adt.List;

public interface Search {
    //<T> int search(List<T> data, Object target);

    <T extends Comparable<T>> int search(List<T> data, T target);

    <T> int search(List<T> data, T target, Comparator<T> comparator);
}
