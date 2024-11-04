package com.hahsm.datastructure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

    private Map<String, Integer> map;

    @BeforeEach
    public void setUp() {
        map = new HashMap<>();
    }

    @Test
    public void testIsEmpty() {
        // Check that the map is initially empty
        assertTrue(map.isEmpty(), "Map should be empty initially");

        // Add an element and check that it's no longer empty
        map.put("Apple", 1);
        assertFalse(map.isEmpty(), "Map should not be empty after adding an element");

        // Clear the map and check that it is empty again
        map.clear();
        assertTrue(map.isEmpty(), "Map should be empty after clearing all elements");
    }

    @Test
    public void testSize() {
        // Check the initial size
        assertEquals(0, map.size(), "Initial size should be 0");

        // Add elements and check the updated size
        map.put("Apple", 1);
        map.put("Banana", 2);
        assertEquals(2, map.size(), "Size should be 2 after adding two elements");

        // Remove an element and check the updated size
        map.remove("Apple");
        assertEquals(1, map.size(), "Size should be 1 after removing one element");

        // Clear the map and check that the size is 0 again
        map.clear();
        assertEquals(0, map.size(), "Size should be 0 after clearing all elements");
    }

    @Test
    public void testPutAndGet() {
        // Add an element and verify it can be retrieved
        map.put("Apple", 1);
        assertEquals(1, map.get("Apple"), "Expected value for 'Apple' is 1");

        // Add another element and verify retrieval
        map.put("Banana", 2);
        assertEquals(2, map.get("Banana"), "Expected value for 'Banana' is 2");

        // Update an element's value and verify
        map.put("Apple", 3);
        assertEquals(3, map.get("Apple"), "Updated value for 'Apple' should be 3");

        // Try to get a nonexistent key (should return null)
        assertNull(map.get("Orange"), "Expected null for a key that doesn't exist");
    }

    @Test
    public void testContainsKey() {
        // Initially, the map should not contain any keys
        assertFalse(map.containsKey("Apple"), "Map should not contain 'Apple' initially");

        // Add an element and check that it contains the key
        map.put("Apple", 1);
        assertTrue(map.containsKey("Apple"), "Map should contain 'Apple' after adding it");

        // Check that it does not contain a different key
        assertFalse(map.containsKey("Banana"), "Map should not contain 'Banana'");

        // Remove the key and verify it no longer exists
        map.remove("Apple");
        assertFalse(map.containsKey("Apple"), "Map should not contain 'Apple' after removal");
    }

    @Test
    public void testRemove() {
        // Add elements and verify they are in the map
        map.put("Apple", 1);
        map.put("Banana", 2);

        // Remove an element and check that it’s no longer in the map
        Integer removedValue = map.remove("Apple");
        assertEquals(1, removedValue, "Removed value should be 1 for 'Apple'");
        assertFalse(map.containsKey("Apple"), "'Apple' should no longer be in the map");

        // Try to remove a nonexistent key (should return null)
        assertNull(map.remove("Orange"), "Expected null when removing a nonexistent key");
    }
}
