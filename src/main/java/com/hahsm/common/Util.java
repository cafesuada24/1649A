package com.hahsm.common;

import com.hahsm.datastructure.adt.List;

public class Util {
    public static <T> void swapList(List<T> list, final int first, final int second) {
        final T temp = list.get(first);
        list.set(first,  list.get(second));
        list.set(second, temp);
    }
}
