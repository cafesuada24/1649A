package com.hahsm.common.util;

import com.hahsm.datastructure.adt.List;

public final class Util {
    public static final <T> void swap(List<T> list, final int i, final int j) {
        if (list == null || i < 0 || j < 0 || i >= list.size() || j >= list.size()) {
            throw new IndexOutOfBoundsException("Invalid index for swap operation");
        }

        final T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
