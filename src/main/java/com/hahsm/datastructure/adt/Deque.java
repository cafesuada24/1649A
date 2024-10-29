package com.hahsm.datastructure.adt;

public interface Deque<E> extends Queue<E> {
    void addFirst(E element);
    void addLast(E element);
    E back();
}
