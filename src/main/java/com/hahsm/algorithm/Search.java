package com.hahsm.algorithm;

import java.util.Comparator;
import java.util.function.Predicate;

import com.hahsm.datastructure.adt.List;

public interface Search {
    <T extends Comparable<T>> int search(List<T> data, T target);

    <T> int search(List<T> data, Object target);

    <T> int search(List<T> data, T target, Comparator<T> comparator);

    <T> List<T> filter(List<T> data, Predicate<T> filter);
}
