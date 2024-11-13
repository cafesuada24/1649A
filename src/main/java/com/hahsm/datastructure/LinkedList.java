package com.hahsm.datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.hahsm.datastructure.adt.Deque;
import com.hahsm.datastructure.adt.List;

/**
 * LinkedList
 */
public class LinkedList<T> implements Deque<T>, List<T> {
    /**
     * ListkedList<T>.Node
     */
    public static class Node<T> {
        private T value;

        private Node<T> next;
        private Node<T> prev;

        public Node(final T value) {
            this(value, null, null);
        }

        public Node(final T value, final Node<T> next, final Node<T> prev) {
            setValue(value);
            setNext(next);
            setPrev(prev);
        }

        public Node() {
            setNext(null);
            setPrev(null);
        }

        public void setValue(final T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(final Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(final Node<T> prev) {
            this.prev = prev;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public T front() throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException("No value to read");
        }
        return head.getValue();
    }

    @Override
    public T remove() throws NoSuchElementException {
        if (tail == null) {
            throw new NoSuchElementException("No element to remove");
        }
        final T value = tail.getValue();

        if (tail.getPrev() == null) {
            tail = null;
            head = null;
            size = 0;
            return value;
        }

        Node<T> prevTail = tail.getPrev();
        prevTail.setNext(null);
        tail.setPrev(null);
        tail = prevTail;
        prevTail = null;
        size -= 1;

        return value;
    }

    @Override
    public void add(final T element) {
        Node<T> newNode = new Node<>(element);

        if (tail == null) {
            head = newNode;
            tail = newNode;
            size = 1;
            return;
        }

        newNode.setPrev(tail);
        tail.setNext(newNode);
        tail = newNode;
        newNode = null;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void addFirst(final T element) {
        Node<T> newNode = new Node<>(element);

        if (head == null) {
            head = newNode;
            tail = newNode;
            size = 1;
            return;
        }

        newNode.setNext(head);
        head.setPrev(newNode);
        head = newNode;
        newNode = null;
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("No element to remove");
        }

        final T value = head.getValue();

        if (head.getNext() == null) {
            tail = null;
            head = null;
            size = 0;
            return value;
        }

        Node<T> nextHead = head.getNext();
        head.setNext(null);
        nextHead.setPrev(null);
        head = nextHead;
        nextHead = null;
        size -= 1;
        return value;
    }

    @Override
    public T back() throws NoSuchElementException {
        if (tail == null) {
            throw new NoSuchElementException("No value to read");
        }
        return tail.getValue();
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> temp = head;

            @Override
            public boolean hasNext() {
                return temp != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Empty linked list");
                }
                final T value = temp.getValue();
                temp = temp.getNext();
                return value;
            }
        };
    }

    @Override
    public T top() {
        return back();
    }

    @Override
    public boolean remove(final T target) {
        Node<T> current = head;
        while (current != null) {
            if (current.getValue().equals(target)) {
                if (current == head) {
                    removeFirst();
                } else if (current == tail) {
                    remove();
                } else {
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
                    size--;
                }
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public void add(final int index, final T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            addFirst(element);
            return;
        }
        if (index == size) {
            add(element);
            return;
        }

        final Node<T> newNode = new Node<>(element);
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.getNext();
        }
        newNode.setNext(current.getNext());
        newNode.setPrev(current);
        current.getNext().setPrev(newNode);
        current.setNext(newNode);
        size++;
    }

    @Override
    public void addAll(final List<T> newElements) {
        for (final T element : newElements) {
            add(element);
        }
    }

    @Override
    public T remove(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return remove();
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        final T value = current.getValue();
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        size--;
        return value;
    }

    @Override
    public T set(final int index, final T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        final T oldValue = current.getValue();
        current.setValue(element);
        return oldValue;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size" + size);
        }
        Node<T> iter = head;
        while (index > 0) {
            iter = iter.getNext();
            --index;
        }
        return iter.getValue();
    }
}
