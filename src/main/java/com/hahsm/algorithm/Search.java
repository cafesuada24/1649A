package com.hahsm.algorithm;

import java.util.Comparator;
import java.util.function.Function;

import com.hahsm.datastructure.adt.List;

public interface Search {
    default <T extends Comparable<T>> int search(List<T> data, T target) {
        return search(data, target, Comparator.naturalOrder());
    }

    <T> int search(List<T> data, T target, Comparator<T> comparator);
    <T> int search(List<T> data, Function<T, Integer> func);

    default <T extends Comparable<T>> List<Integer> searchAll(List<T> data, T target) {
        return searchAll(data, target, Comparator.naturalOrder());
    }

    <T> List<Integer> searchAll(List<T> data, T target, Comparator<T> comparator);
    <T> List<Integer> searchAll(List<T> data, Function<T, Integer> func);
}
