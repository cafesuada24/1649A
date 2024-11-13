package com.hahsm.datastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.NoSuchElementException;

class LinkedListTest {

    private LinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new LinkedList<>();
    }

    @Test
    void testAdd() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.size());
        assertEquals(1, list.front());
        assertEquals(3, list.back());
    }

    @Test
    void testAddAtIndex() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(1, 99);  // Insert at index 1
        assertEquals(4, list.size());
        assertEquals(1, list.get(0));
        assertEquals(99, list.get(1));
        assertEquals(2, list.get(2));
    }

    @Test
    void testAddFirst() {
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        assertEquals(3, list.size());
        assertEquals(3, list.front());
        assertEquals(1, list.back());
    }

    @Test
    void testAddAll() {
        list.addAll(new ArrayList<>(1, 2, 3, 4));
        assertEquals(4, list.size());
        assertEquals(1, list.get(0));
        assertEquals(4, list.back());
    }

    @Test
    void testRemove() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.remove());
        assertEquals(2, list.size());
        assertEquals(2, list.back());
    }

    @Test
    void testRemoveAtIndex() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(2, list.remove(1));  // Remove at index 1
        assertEquals(2, list.size());
        assertEquals(3, list.back());
    }

    @Test
    void testRemoveByElement() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertTrue(list.remove(Integer.valueOf(2)));  // Remove element by value
        assertEquals(2, list.size());
        assertFalse(list.remove(Integer.valueOf(99)));  // Non-existent element
    }

    @Test
    void testRemoveFirst() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(1, list.removeFirst());
        assertEquals(2, list.size());
        assertEquals(2, list.front());
    }

    @Test
    void testGet() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(2));
    }

    @Test
    void testSet() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.set(1, 99);  // Set index 1 to 99
        assertEquals(3, list.size());
        assertEquals(99, list.get(1));
    }

    @Test
    void testSize() {
        assertEquals(0, list.size());
        list.add(1);
        list.add(2);
        assertEquals(2, list.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add(1);
        assertFalse(list.isEmpty());
    }

    @Test
    void testFrontThrowsExceptionWhenEmpty() {
        assertThrows(NoSuchElementException.class, () -> list.front());
    }

    @Test
    void testBackThrowsExceptionWhenEmpty() {
        assertThrows(NoSuchElementException.class, () -> list.back());
    }

    @Test
    void testRemoveThrowsExceptionWhenEmpty() {
        assertThrows(NoSuchElementException.class, () -> list.remove());
    }

    @Test
    void testRemoveFirstThrowsExceptionWhenEmpty() {
        assertThrows(NoSuchElementException.class, () -> list.removeFirst());
    }

    @Test
    void testTop() {
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.top());  // Top should return the back of the list
    }

    @Test
    void testClear() {
        list.add(1);
        list.add(2);
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void testIterator() {
        list.add(1);
        list.add(2);
        list.add(3);

        int sum = 0;
        for (int value : list) {
            sum += value;
        }
        assertEquals(6, sum);
    }

    @Test
    void testAddAtIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 99));
    }

    @Test
    void testRemoveAtIndexOutOfBounds() {
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
    }

    @Test
    void testGetOutOfBounds() {
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
    }

    @Test
    void testSetOutOfBounds() {
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(5, 99));
    }
}
