package com.hahsm.datastructure;

import javax.management.openmbean.InvalidKeyException;

import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.adt.Map;

public class HashMap<K, V> implements Map<K, V> {

    public static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public K getKey() {
            return key;
        }
        
        @Override
        public V getValue() {
            return value;
        }
        
        @Override
        public V setValue(V value) {
            final V old = this.value;
            this.value = value;
            return old;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public void setNext(Entry<K, V> next) {
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }

            if (o.getClass() != this.getClass()) {
                return false;
            }

            final Entry<K, V> other = (Entry<K, V>) o;
            return getKey() == other.getKey() && getValue() == other.getValue();
            
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAUTL_LOAD_FACTOR = 0.75f;

    private final float loadFactor;
    private final int initialCapacity;
    private int capacity;
    private int size;

    private ArrayList<Entry<K, V>> table;

    public HashMap() {
        this(DEFAULT_CAPACITY, DEFAUTL_LOAD_FACTOR);
    }

    public HashMap(int capacity, float loadFactor) {
        this.initialCapacity = capacity;
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        this.table = new ArrayList<>(capacity, loadFactor);
        this.table.resize(capacity);
         size = 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.size(); ++i) {
            table.set(i, null);
        }
        size = 0;

        if (isSparse()) {
            sizeDown();
        }
    }

    @Override
    public boolean containsKey(K key) {
        final int index = getIndex(key);
        Entry<K, V> entry = table.get(index);

        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return true;
            }
            entry = entry.getNext();
        }

        return false;

    }

    @Override
    public boolean containsValue(V value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsValue'");
    }

    @Override
    public V get(K key) throws InvalidKeyException {
        final int index = getIndex(key);
        Entry<K, V> entry = table.get(index);

        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
            entry = entry.getNext();
        }

        return null;
    }

    @Override
    public boolean put(K key, V value) {
        if (isFull()) {
            sizeUp();
        }

        final int index = getIndex(key);
        Entry<K, V> existingEntry = table.get(index);

        while (existingEntry != null) {
            if (existingEntry.getKey().equals(key)) {
                existingEntry.value = value;
                return true;
            }
            existingEntry = existingEntry.getNext();
        }

        if (table.get(index) == null) {
            ++size;
        }

        final Entry<K, V> newEntry = new Entry<K, V>(key, value);
        newEntry.setNext(table.get(index));
        table.set(index, newEntry);

        return true;
    }

    @Override
    public V remove(K key) {
        final int index = getIndex(key);
        Entry<K, V> entry = table.get(index);
        Entry<K, V> prevEntry = null;

        while (entry != null) {
            if (entry.getKey().equals(key)) {
                break;
            }
            prevEntry = entry;
            entry = entry.getNext();
            continue;
        }

        if (entry == null) {
            return null;
        }

        if (prevEntry == null) {
            table.set(index, entry.getNext());
        } else {
            prevEntry.setNext(entry.getNext());
        }
        entry.setNext(null);
        --size;

        if (isSparse()) {
            sizeDown();
        }

        return entry.getValue();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("HashMap { ");
        for (int i = 0; i < table.size(); ++i) {
            Entry<K, V> entry = table.get(i);
            while (entry != null) {
                builder.append(" {key=").append(entry.getKey()).append(",value=").append(entry.getValue()).append("} ");
                entry = entry.getNext();
            }
        }
        builder.append("}");
        return builder.toString();
    }

    private void changeCapacity(int newCapacity) {
        final ArrayList<Entry<K, V>> oldTable = table;
        table = new ArrayList<>(newCapacity);
        table.resize(newCapacity);
        size = 0;
        capacity = newCapacity;

        for (int i = 0; i < oldTable.size(); ++i) {
            if (oldTable.get(i) != null) {
                put(oldTable.get(i).getKey(), oldTable.get(i).getValue());
            }
        }
    }

    private void sizeUp() {
        changeCapacity(capacity << 1);
    }

    private void sizeDown() {
        changeCapacity(capacity >> 1);
    }

    private int getIndex(Object key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode() % capacity;

        return hash + (hash < 0 ? capacity : 0);
    }

    private boolean isFull() {
        return size() >= (int) (table.size() * loadFactor);
    }

    private boolean isSparse() {
        if (size() <= capacity) {
            return false;
        }
        return size() < table.size() * loadFactor / 2;
    }

	@Override
	public List<com.hahsm.datastructure.adt.Map.Entry<K, V>> entries() {
        final List<com.hahsm.datastructure.adt.Map.Entry<K, V>> entries = new ArrayList<>(size());

        for (int i = 0; i < capacity; ++i) {
            Entry<K, V> entry = table.get(i);

            while (entry != null) {
                entries.add(entry);
                entry = entry.getNext();
            }
        }

        return entries;
	}
}
