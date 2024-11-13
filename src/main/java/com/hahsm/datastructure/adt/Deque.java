package com.hahsm.datastructure.adt;

public interface Deque<E> extends Queue<E>, Stack<E> {
    void addFirst(E element);
    E removeFirst();

    default void addLast(E element) {
        add(element);    
    }

    default E removeLast() {
        return remove();
    }

    abstract E back();
}
