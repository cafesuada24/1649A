package com.hahsm.datastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hahsm.datastructure.HashMap.Entry;
import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.adt.Map;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {
    private HashMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
    }

    @Test
    void testClear() {
        map.put("key1", 1);
        map.put("key2", 2);
        map.clear();
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
    }

    @Test
    void testContainsKey() {
        map.put("key1", 1);
        assertTrue(map.containsKey("key1"));
        assertFalse(map.containsKey("key2"));
    }

    @Test
    void testGet() {
        map.put("key1", 1);
        assertEquals(1, map.get("key1"));
        assertNull(map.get("key2")); // Should return null for non-existent key
    }

    @Test
    void testPut() {
        map.put("key1", 1);
        assertEquals(1, map.get("key1"));
        assertEquals(1, map.size());

        // Test overwriting existing key
        map.put("key1", 2);
        assertEquals(2, map.get("key1"));
    }

    @Test
    void testRemove() {
        map.put("key1", 1);
        map.put("key2", 2);
        assertEquals(1, map.remove("key1"));
        assertNull(map.get("key1"));
        assertEquals(1, map.size());

        // Test removing non-existent key
        assertNull(map.remove("key3"));
    }

    @Test
    void testIsEmpty() {
        assertTrue(map.isEmpty());
        map.put("key1", 1);
        assertFalse(map.isEmpty());
    }

    @Test
    void testSize() {
        assertEquals(0, map.size());
        map.put("key1", 1);
        assertEquals(1, map.size());
        map.put("key2", 2);
        assertEquals(2, map.size());
        map.remove("key1");
        assertEquals(1, map.size());
    }

    @Test
    void testEntries() {
        map.put("key1", 1);
        map.put("key2", 2);
        List<Map.Entry<String, Integer>> entries = map.entries();
        assertEquals(2, entries.size());
        assertEquals(1, entries.filter(e -> e.getKey().equals("key2") && e.getValue() == 2).size());
        assertEquals(1, entries.filter(e -> e.getKey().equals("key1") && e.getValue() == 1).size());

    }

    @Test
    void testValues() {
        map.put("key1", 1);
        map.put("key2", 2);
        List<Integer> values = map.values();
        assertEquals(2, values.size());
        assertTrue(values.contains(1));
        assertTrue(values.contains(2));
    }

    @Test
    void testKeys() {
        map.put("key1", 1);
        map.put("key2", 2);
        List<String> keys = map.keys();
        assertEquals(2, keys.size());
        assertTrue(keys.contains("key1"));
        assertTrue(keys.contains("key2"));
    }

    @Test
    void testIterator() {
        map.put("key1", 1);
        map.put("key2", 2);
        var iterator = map.iterator();
        assertNotNull(iterator);

        // Count entries via iterator
        int count = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            assertTrue(map.containsKey(entry.getKey()));
            assertEquals(map.get(entry.getKey()), entry.getValue());
            count++;
        }
        assertEquals(map.size(), count);
    }
}
