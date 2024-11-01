package com.hahsm.algorithm;

import java.util.Comparator;

import com.hahsm.datastructure.ArrayList;
import com.hahsm.datastructure.adt.List;

public class LinearSearch implements Search {

    @Override
    public <E> int search(List<E> data, E target, Comparator<E> comparator) {
        for (int i = 0; i < data.size(); ++i) {
            if (comparator.compare(data.get(i), target) == 0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public <T> List<Integer> searchAll(List<T> data, T target, Comparator<T> comparator) {
        List<Integer> found = new ArrayList<>(data.size());

        for (int i = 0; i < data.size(); ++i) {
            if (comparator.compare(data.get(i), target) == 0) {
                found.add(i);
            }
        }

        return found;
    }
}
