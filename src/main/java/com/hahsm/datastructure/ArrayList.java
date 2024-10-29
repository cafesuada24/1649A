package com.hahsm.datastructure;

import com.hahsm.datastructure.adt.List;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private static final double SHRINK_RATIO = 0.5;
    private static final int GROW_RATIO = 2;
    public static Object[] copyOf(Object array[], int newSize) {
        assert array.length <= newSize;

        Object resizedArray[] = new Object[newSize]; 

        for (int i = 0; i < array.length; ++i) {
            resizedArray[i] = array[i];
        }

        return resizedArray;
    }


    private int size = 0;
    private int capacity = DEFAULT_CAPACITY;
    private Object elements[];

    public ArrayList() {
       this(DEFAULT_CAPACITY); 
    }

    public ArrayList(int capacity) {
        elements = new Object[capacity];
        this.capacity = capacity;
    }

    public ArrayList(List<T> data) {
        this.capacity = data.size();
        elements = new Object[this.capacity];
        for (int i = 0; i < this.capacity; ++i) {
            elements[i] = data.get(i);
        }
    }

	@Override
	public void add(T value) {
        ensureCapacity();
        elements[size++] = value;
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
	public void add(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size" + size);
        }
        ensureCapacity();
        for (int i = size; i > index; --i) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        ++size;
	}

	@Override
	public void sort() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'sort'");
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size" + size);
        }
        return (T) elements[index];
	}

    @Override
	public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size" + size);
        }
        @SuppressWarnings("unchecked")
		final T old = (T)elements[index];
        elements[index] = element;
        return old;
	}

	private void ensureCapacity() {
        double ratio = 1;
        if (capacity == size) {
            ratio = GROW_RATIO;
        } else if (size * 3 <= capacity) {
            ratio = SHRINK_RATIO;
        } else {
            return;
        }

        int newSize = (int)(capacity * ratio);
        elements = ArrayList.copyOf(elements, newSize);
    }
}
