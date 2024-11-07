package com.hahsm.datastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueTest {

    private PriorityQueue<Integer> priorityQueue;

    @BeforeEach
    void setUp() {
        priorityQueue = new PriorityQueue<Integer>(Comparator.naturalOrder());
    }

    @Test
    void testAdd() {
        priorityQueue.add(5);
        assertEquals(1, priorityQueue.size(), "Size should be 1 after adding an element");
        assertTrue(priorityQueue.contains(5), "Queue should contain the element 5");
    }

    @Test
    void testSize() {
        assertEquals(0, priorityQueue.size(), "Size should be 0 initially");
        priorityQueue.add(10);
        priorityQueue.add(20);
        assertEquals(2, priorityQueue.size(), "Size should be 2 after adding two elements");
    }

    @Test
    void testIsEmpty() {
        assertTrue(priorityQueue.isEmpty(), "Queue should be empty initially");
        priorityQueue.add(10);
        assertFalse(priorityQueue.isEmpty(), "Queue should not be empty after adding an element");
    }

    @Test
    void testFront() {
        priorityQueue.add(5);
        priorityQueue.add(10);
        assertEquals(5, priorityQueue.front(), "Front element should be the smallest element");
    }

    @Test
    void testRemove() {
        priorityQueue.add(30);
        priorityQueue.add(10);
        priorityQueue.add(20);

        assertEquals(10, priorityQueue.remove(), "Remove should return the smallest element");
        assertEquals(2, priorityQueue.size(), "Size should decrease after removal");
    }

    @Test
    void testClear() {
        priorityQueue.add(1);
        priorityQueue.add(2);
        priorityQueue.add(3);

        priorityQueue.clear();
        assertTrue(priorityQueue.isEmpty(), "Queue should be empty after clear");
        assertEquals(0, priorityQueue.size(), "Size should be 0 after clear");
    }

    @Test
    void testContains() {
        priorityQueue.add(1);
        priorityQueue.add(2);
        assertTrue(priorityQueue.contains(1), "Queue should contain element 1");
        assertFalse(priorityQueue.contains(3), "Queue should not contain element 3");
    }

    @Test
    void testIterator() {
        priorityQueue.add(1);
        priorityQueue.add(3);
        priorityQueue.add(2);

        Iterator<Integer> iterator = priorityQueue.iterator();
        assertTrue(iterator.hasNext(), "Iterator should have elements");
        assertEquals(1, iterator.next(), "Iterator should return the smallest element first");
    }
}
