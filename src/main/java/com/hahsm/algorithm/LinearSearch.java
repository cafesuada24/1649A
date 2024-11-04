package com.hahsm.algorithm;

import java.util.Comparator;
import java.util.function.Predicate;

import com.hahsm.datastructure.adt.List;

public class LinearSearch implements Search {

    @Override
    public <T extends Comparable<T>> int search(List<T> data, T target) {
        Predicate<T> isEqual = x -> x.compareTo(target) == 0;
        return searchHelper(data, isEqual);
    }

    @Override
    public <T> int search(List<T> data, T target, Comparator<T> comparator) {
        Predicate<T> isEqual = x -> comparator.compare(x, target) == 0;
        return searchHelper(data, isEqual);
    }

    //@Override
    //public <T> int search(List<T> data, Object target) {
    //    Predicate<T> isEqual = x -> x.equals(target);
    //    return searchHelper(data, isEqual);
    //}

    private <T> int searchHelper(List<T> data, Predicate<T> isEqual) {
        for (int i = 0; i < data.size(); ++i) {
            if (isEqual.test(data.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
