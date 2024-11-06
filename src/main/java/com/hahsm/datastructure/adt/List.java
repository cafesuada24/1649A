package com.hahsm.datastructure.adt;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import com.hahsm.datastructure.ArrayList;

public interface List<T> extends Container<T> {
    void add(int index, T element);

    void addAll(List<T> newElements);

    T remove(int index);

    T set(int index, T element);

    T get(int index);

    default int indexOf(T target) {
        for (int i = 0; i < size(); ++i) {
            if ((target == null && get(i) == null) || (get(i) != null && get(i).equals(target))) {
                return i;
            }
        }
        return -1;
    }

    default List<T> filter(Predicate<T> filter) {
        final List<T> matches = new ArrayList<>();
        forEach((x) -> {
            if (filter.test(x)) {
                matches.add(x);
            }
        });
        return matches;
    }

    @Override
    default Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return get(index++);
            }
        };
    }

    @Override
    default boolean contains(T target) {
        return indexOf(target) > -1;
    }
}
