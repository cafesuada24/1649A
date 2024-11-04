package com.hahsm.datastructure;

import java.util.Comparator;

import com.hahsm.algorithm.LinearSearch;
import com.hahsm.algorithm.Search;
import com.hahsm.datastructure.adt.List;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_MIN_CAPACITY = 10;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public static Object[] copyOf(final Object array[], final int newSize) {
        final Object resizedArray[] = new Object[newSize];

        final int size = Math.min(array.length, newSize);

        for (int i = 0; i < size; ++i) {
            resizedArray[i] = array[i];
        }

        return resizedArray;
    }

    private int size = 0;
    private T elements[];
    private int minCapacity;
    private final float loadFactor;
    private Search searcher;

    public ArrayList() {
        this(DEFAULT_MIN_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public ArrayList(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    // @SuppressWarnings("unchecked")
    public ArrayList(final int capacity, final float loadFactor) {
        assert loadFactor > 0 && loadFactor < 1.0;
        this.loadFactor = loadFactor;
        this.ensureCapacity(capacity);
        this.setSearchStrategy(new LinearSearch());
    }

    public ArrayList(List<T> data) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        size = data.size();
        ensureCapacity(size);
        assert elements != null;
        this.setSearchStrategy(new LinearSearch());
        for (int i = 0; i < size; ++i) {
            elements[i] = data.get(i);
        }
    }

    // @SuppressWarnings("unchecked")
    public ArrayList(T... data) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        size = data.length;
        ensureCapacity(size);
        assert elements != null;
        this.setSearchStrategy(new LinearSearch());
        for (int i = 0; i < size; ++i) {
            elements[i] = data[i];
        }
    }

    @Override
    public void add(final T value) {
        if (isFull()) {
            sizeUp();
        }
        elements[size++] = value;
    }

    @Override
    public void addAll(List<T> newElements) {
        for (int i = 0; i < newElements.size(); ++i) {
            add(newElements.get(i));
        }
    }

    @Override
    public void add(final int index, final T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size" + size);
        }
        if (isFull()) {
            sizeUp();
        }

        for (int i = size; i > index; --i) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        ++size;
    }

    @Override
    public boolean remove(final T element) {
        final int index = indexOf(element);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
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
    public T get(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size" + size);
        }
        return elements[index];
    }

    @Override
    public T set(final int index, final T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size" + size);
        }
        final T old = elements[index];
        elements[index] = element;
        return old;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size" + size);
        }

        final T element = elements[index];
        for (int i = index; i < size - 1; ++i) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;

        if (isSparse()) {
            sizeDown();
        }

        return element;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; ++i) {
            elements[i] = null;
        }

        size = 0;

        if (isSparse()) {
            sizeDown();
        }
    }

	@Override
	public boolean contains(T target) {
        return indexOf(target) > -1;
	}

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!this.getClass().isAssignableFrom(o.getClass())) {
            return false;
        }
        
        final ArrayList<T> other = (ArrayList<T>) o;
        if (size() != other.size()) {
            return false;}
        for (int i = 0; i < size(); ++i) {
            if (!other.get(i).equals(get(i))) {
                return false;
            }
        }
        
        return true;
    }

    public void ensureCapacity(int minCapacity) {
        this.minCapacity = minCapacity;

        if (elements == null || elements.length < minCapacity) {
            changeCapacity(minCapacity);
        }
    }

    public void resize(int newSize) {
        if (elements == null || newSize > elements.length) {
            changeCapacity(newSize);
        }

        this.size = newSize;
    }

    @SuppressWarnings("unchecked")
    private void changeCapacity(int capacity) {
        capacity = Math.max(capacity, minCapacity);

        if (elements == null) {
            elements = (T[]) new Object[capacity];
        } else {
            elements = (T[]) ArrayList.copyOf(elements, capacity);
        }
    }

    private boolean isFull() {
        return size() >= (int) (elements.length * loadFactor);
    }

    private boolean isSparse() {
        if (size() <= minCapacity) {
            return false;
        }
        return size() < (elements.length * loadFactor / 2);
    }

    private void sizeUp() {
        changeCapacity(elements.length << 1);
    }

    private void sizeDown() {
        changeCapacity(elements.length >> 1);
    }

	@Override
	public int indexOf(T target) {
        assert searcher != null;
        return searcher.search(this, target);
	}

	@Override
	public void setSearchStrategy(Search strategy) {
        this.searcher = strategy;
	}
}
