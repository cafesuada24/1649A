package com.hahsm.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hahsm.datastructure.ArrayWrapper;
import com.hahsm.datastructure.IndexedCollection;
import com.hahsm.datastructure.ListWrapper;

public class MergeSort<T extends Comparable<T>> implements SortStrategy<T> {

    @Override
    public void sort(T[] data) {
        final T[] temp = Arrays.copyOf(data, data.length);
        sort(new ArrayWrapper<>(data), new ArrayWrapper<>(temp));
    }

    @Override
    public void sort(List<T> data) {
        final List<T> temp = new ArrayList<>(data);
        sort(new ListWrapper<>(data), new ListWrapper<>(temp));
    }

    private void sort(IndexedCollection<T> source, IndexedCollection<T> temp) {
        final int size = source.size();

        for (int width = 1; width < size; width <<= 1) {
            for (int index = 0; index < size; index += 2 * width) {
                merge(source, index, Math.min(index + width, size), Math.min(index + 2 * width, size), temp);
            }

            copy(temp, source);
        }
    }

    private void merge(IndexedCollection<T> collection, int indexLeft, int indexMiddle, int indexRight,
            IndexedCollection<T> temp) {
        for (int i = indexLeft, j = indexMiddle, k = indexLeft; k < indexRight; ++k) {
            if (i < indexMiddle && (j >= indexRight || collection.get(i).compareTo(collection.get(j)) == -1)) {
                temp.set(k, collection.get(i++));
            } else {
                temp.set(k, collection.get(j++));
            }
        }
    };

    private void copy(IndexedCollection<T> from, IndexedCollection<T> to) {
        if (from.size() != to.size()) {
            throw new IllegalArgumentException(
                    "Inconsistent list sizes: from size = " + from.size() + ", to size = " + to.size());
        }
        for (int i = 0; i < from.size(); ++i) {
            to.set(i, from.get(i));
        }
    }

}
