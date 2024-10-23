package com.hahsm.algorithms;

import java.util.ArrayList;
import java.util.List;

public class MergeSort<T extends Comparable<T>> implements Sorter<T> {

    @Override
    public void sort(T[] data) {
        throw new UnsupportedOperationException("Unimplemented method 'sort'");
    }

    @Override
    public void sort(List<T> arr) {
        final int size = arr.size();

        List<T> temp = new ArrayList<>(arr);
        for (int width = 1; width < size; width <<= 1) {
            for (int index = 0; index < size; index += 2 * width) {
                merge(arr, index, Math.min(index + width, size), Math.min(index + 2 * width, size), temp);
            }

            copy(temp, arr);
        }
    }

    private void merge(List<T> arr, int indexLeft, int indexMiddle, int indexRight,
            List<T> temp) {
        for (int i = indexLeft, j = indexMiddle, k = indexLeft; k < indexRight; ++k) {
            if (i < indexMiddle && (j >= indexRight || arr.get(i).compareTo(arr.get(j)) == -1)) {
                temp.set(k, arr.get(i++));
            } else {
                temp.set(k, arr.get(j++));
            }
        }
    };

    private void copy(List<T> from, List<T> to) {
        if (from.size() != to.size()) {
            throw new IllegalArgumentException(
                    "Inconsistent list sizes: from size = " + from.size() + ", to size = " + to.size());
        }
        for (int i = 0; i < from.size(); ++i) {
            to.set(i, from.get(i));
        }
    }

}
