package com.hahsm.datastructure;

import java.util.NoSuchElementException;

import com.hahsm.datastructure.adt.Deque;

/**
 * LinkedList
 */
public class LinkedList<T> implements Deque<T> {
    /**
     * ListkedList<T>.Node
     */
    public class Node {
        private T value;

		private Node next;
        private Node prev;

        public Node(T value) {
            this(value, null, null);
        }

        public Node(T value, Node next, Node prev) {
            setValue(value);
            setNext(next);
            setPrev(prev);
        }

        public Node() {
            setNext(null);
            setPrev(null);
        }

        public void setValue(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
			return prev;
		}

		public void setPrev(Node prev) {
			this.prev = prev;
		}
    }

    private Node head;
    private Node tail;
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
        T value = tail.getValue();
        
        if (tail.getPrev() == null) {
            tail = null;
            head = null;
            size = 0;
            return value;
        }
        
        Node prevTail = tail.getPrev();
        prevTail.setNext(null);
        tail.setPrev(null);
        tail = prevTail;
        prevTail = null;
        size -= 1;

        return value;
    }

    @Override
    public void add(T element) {
        Node newNode = new Node(element);
    
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
    public void remove(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
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
    public void addFirst(T element) {
        Node newNode = new Node(element);

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

        T value = head.getValue();
        
        if (head.getNext() == null) {
            tail = null;
            head = null;
            size = 0;
            return value;
        }
        
        Node nextHead = head.getNext(); 
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
}
