package com.hahsm.datastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hahsm.datastructure.adt.List;

import java.util.Iterator;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;


class ArrayListTest {
    private ArrayList<String> list;

    @BeforeEach
    void setUp() {
        list = new ArrayList<>();
    }

    @Test
    void testAdd() {
        list.add("A");
        assertEquals(1, list.size());
        assertEquals("A", list.get(0));
    }

    @Test
    void testAddAll() {
        list.add("A");
        list.addAll(new ArrayList<>("B", "C"));
        assertEquals(3, list.size());
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    void testAddAtIndex() {
        list.add("A");
        list.add(0, "B");
        assertEquals(2, list.size());
        assertEquals("B", list.get(0));
        assertEquals("A", list.get(1));
    }

    @Test
    void testRemoveElement() {
        list.add("A");
        list.add("B");
        assertTrue(list.remove("A"));
        assertFalse(list.contains("A"));
        assertEquals(1, list.size());
    }

    @Test
    void testSize() {
        assertEquals(0, list.size());
        list.add("A");
        assertEquals(1, list.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add("A");
        assertFalse(list.isEmpty());
    }

    @Test
    void testGet() {
        list.add("A");
        assertEquals("A", list.get(0));
    }

    @Test
    void testSet() {
        list.add("A");
        list.set(0, "B");
        assertEquals("B", list.get(0));
    }

    @Test
    void testRemoveByIndex() {
        list.add("A");
        list.add("B");
        assertEquals("A", list.remove(0));
        assertEquals(1, list.size());
        assertEquals("B", list.get(0));
    }

    @Test
    void testClear() {
        list.add("A");
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void testEquals() {
        ArrayList<String> otherList = new ArrayList<>();
        list.add("A");
        otherList.add("A");
        assertEquals(list, otherList);

        otherList.add("B");
        assertNotEquals(list, otherList);
    }

    @Test
    void testEnsureCapacity() {
        list.ensureCapacity(100);
        // Assuming this affects internal structure but doesn't change visible properties
        list.add("A");
        assertEquals("A", list.get(0));
    }

    @Test
    void testResize() {
        list.add("A");
        list.add("B");
        list.resize(1);
        assertEquals(1, list.size());
        assertEquals("A", list.get(0));
    }

    @Test
    void testFilter() {
        list.add("A");
        list.add("BB");
        list.add("CCC");
        List<String> filteredList = list.filter(s -> s.length() == 2);
        assertEquals(1, filteredList.size());
        assertEquals("BB", filteredList.get(0));
    }

    @Test
    void testIterator() {
        list.add("A");
        list.add("B");
        Iterator<String> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testContains() {
        list.add("A");
        assertTrue(list.contains("A"));
        assertFalse(list.contains("B"));
    }

    @Test
    void testIndexOf() {
        list.add("A");
        list.add("B");
        assertEquals(0, list.indexOf("A"));
        assertEquals(1, list.indexOf("B"));
        assertEquals(-1, list.indexOf("C"));
    }
}
