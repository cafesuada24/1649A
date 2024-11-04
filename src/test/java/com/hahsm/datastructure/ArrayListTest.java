package com.hahsm.datastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.hahsm.datastructure.adt.List;

public class ArrayListTest {

    private ArrayList<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
    }

    @Test
    public void testAddSingleElement() {
        list.add(5);
        assertEquals(1, list.size());
        assertEquals(5, list.get(0));
    }

    @Test
    public void testAddAllElements() {
        List<Integer> elements = new ArrayList<>(1, 2, 3);
        list.addAll(elements);
        assertEquals(3, list.size());
        assertEquals(elements, list);
    }

    @Test
    public void testAddAtSpecificIndex() {
        list.add(1);
        list.add(2);
        list.add(1, 3); // Adds 3 at index 1
        assertEquals(3, list.size());
        assertEquals(3, list.get(1));
        assertEquals(2, list.get(2));
    }

    @Test
    public void testSize() {
        assertEquals(0, list.size());
        list.add(10);
        list.add(20);
        assertEquals(2, list.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add(10);
        assertFalse(list.isEmpty());
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGet() {
        list.add(5);
        list.add(10);
        assertEquals(5, list.get(0));
        assertEquals(10, list.get(1));
    }

    @Test
    public void testSet() {
        list.add(5);
        list.add(10);
        int oldValue = list.set(1, 15); // Changes 10 to 15
        assertEquals(10, oldValue); // Verifies the previous value
        assertEquals(15, list.get(1));
    }

    @Test
    public void testRemoveByIndex() {
        list.add(5);
        list.add(10);
        int removedElement = list.remove(0); // Removes element at index 0
        assertEquals(5, removedElement);
        assertEquals(1, list.size());
        assertEquals(10, list.get(0));
    }

    @Test
    public void testEnsureCapacity() {
        int initialCapacity = 5;
        list.ensureCapacity(initialCapacity);
        list.add(1);
        list.add(2);
        assertEquals(2, list.size());
        // Note: Capacity is not directly testable; we trust ensureCapacity does not affect size
    }

    @Test
    public void testResize() {
        int newSize = 5;
        for (int i = 0; i < newSize; i++) {
            list.add(i);
        }
        assertEquals(newSize, list.size());
        list.add(5);
        assertEquals(newSize + 1, list.size()); // Verifies size increases as we add beyond original limit
    }
}
