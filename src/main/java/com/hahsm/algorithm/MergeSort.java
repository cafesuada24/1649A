package com.hahsm.algorithm;


import java.util.Comparator;

import com.hahsm.datastructure.ArrayList;
import com.hahsm.datastructure.adt.List;

public class MergeSort implements SortStrategy {

    @Override
    public <T extends Comparable<T>> void sort(List<T> data) {
        sort(data, Comparator.naturalOrder());
    }

	@Override
	public <T> void sort(List<T> data, Comparator<T> comparator) {
        final List<T> temp = new ArrayList<T>(data);
        sort(data, temp, comparator);
	}

    private <T> void sort(List<T> source, List<T> temp, Comparator<T> comparator) {
        final int size = source.size();

        for (int width = 1; width < size; width <<= 1) {
            for (int index = 0; index < size; index += 2 * width) {
                merge(source, index, Math.min(index + width, size), Math.min(index + 2 * width, size), temp, comparator);
            }

            copy(temp, source);
        }
    }

    private <T> void merge(List<T> collection, int indexLeft, int indexMiddle, int indexRight,
            List<T> temp, Comparator<T> comparator) {
        for (int i = indexLeft, j = indexMiddle, k = indexLeft; k < indexRight; ++k) {
            if (i < indexMiddle && (j >= indexRight || comparator.compare(collection.get(i), collection.get(j)) == -1)) {
                temp.set(k, collection.get(i++));
            } else {
                temp.set(k, collection.get(j++));
            }
        }
    };

    private <T> void copy(final List<T> from, final List<T> to) {
        if (from.size() != to.size()) {
            throw new IllegalArgumentException(
                    "Inconsistent list sizes: from size = " + from.size() + ", to size = " + to.size());
        }
        for (int i = 0; i < from.size(); ++i) {
            to.set(i, from.get(i));
        }
    }
}
