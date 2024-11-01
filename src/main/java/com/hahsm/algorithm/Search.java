package com.hahsm.algorithm;

import java.util.Comparator;

import com.hahsm.datastructure.adt.List;

public interface Search {
    default <T extends Comparable<T>> int search(List<T> data, T target) {
        return search(data, target, Comparator.naturalOrder());
    }

    <T> int search(List<T> data, T target, Comparator<T> comparator);

    default <T extends Comparable<T>> List<Integer> searchAll(List<T> data, T target) {
        return searchAll(data, target, Comparator.naturalOrder());
    }

    <T> List<Integer> searchAll(List<T> data, T target, Comparator<T> comparator);
}
