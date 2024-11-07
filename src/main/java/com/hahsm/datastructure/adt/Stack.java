package com.hahsm.datastructure.adt;

public interface Stack<E> extends Container<E> {
    E top();
    E remove();
}
