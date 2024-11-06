package com.hahsm.datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;

import javax.management.openmbean.InvalidKeyException;

import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.adt.Map;

public class HashMap<K, V> implements Map<K, V> {

    public static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        public Entry(final K key, final V value) {
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
        public V setValue(final V value) {
            final V old = this.value;
            this.value = value;
            return old;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public void setNext(final Entry<K, V> next) {
            this.next = next;
        }

        @Override
        public boolean equals(final Object o) {
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

    public HashMap(final int capacity, final float loadFactor) {
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
    public boolean containsKey(final K key) {
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
    public boolean containsValue(final V value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'containsValue'");
    }

    @Override
    public V get(final K key) throws InvalidKeyException {
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
    public boolean put(final K key, final V value) {
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


        final Entry<K, V> newEntry = new Entry<K, V>(key, value);
        newEntry.setNext(table.get(index));
        table.set(index, newEntry);
        ++size;

        return true;
    }

    @Override
    public V remove(final K key) {
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
        final StringBuilder builder = new StringBuilder();
        builder.append("HashMap { ");
        for (final var entry: this) {
            builder.append(" {key=").append(entry.getKey()).append(",value=").append(entry.getValue()).append("} ");
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

        for (final var entry: oldTable) {
            if (entry == null) {
                continue;
            }

            var iter = entry;
            while (iter != null) {
                put(iter.getKey(), iter.getValue());
                final var temp = entry;
                iter = iter.getNext();
                temp.setNext(null);
            }
        }
    }

    private void sizeUp() {
        changeCapacity(capacity << 1);
    }

    private void sizeDown() {
        changeCapacity(capacity >> 1);
    }

    private int getIndex(final Object key) {
        if (key == null) {
            return 0;
        }
        final int hash = key.hashCode() % capacity;

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

        forEach((entry) -> entries.add(entry));

        return entries;
    }

    @Override
    public List<V> values() {
        final List<V> values = new ArrayList<>(size());

        forEach((k, v) -> values.add(v));

        return values;
    }

    @Override
    public List<K> keys() {
        final List<K> keys = new ArrayList<>(size());

        forEach((k, v) -> keys.add(k));

        return keys;
    }


	@Override
	public Iterator<com.hahsm.datastructure.adt.Map.Entry<K, V>> iterator() {
        return new Iterator<Map.Entry<K,V>>() {
            private int index = 0;
            private int currentPos = 0;
            private Entry<K, V> currentEntry = null;

			@Override
			public boolean hasNext() {
                return index < size() && currentPos < table.size();
			}

			@Override
			public com.hahsm.datastructure.adt.Map.Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (currentEntry == null) {
                    while (currentPos < table.size() && table.get(currentPos) == null) {
                        ++currentPos;
                    }
                    if (currentPos == table.size()) {
                        throw new IllegalStateException();
                    }

                    currentEntry = table.get(currentPos++);
                }

                final var toReturn = currentEntry;
                currentEntry = currentEntry.getNext();
                ++index;
                return toReturn;
			} 
        };
	}
}
