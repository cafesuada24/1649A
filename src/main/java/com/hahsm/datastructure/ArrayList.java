package com.hahsm.datastructure;

import com.hahsm.datastructure.adt.List;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private static final double SHRINK_RATIO = 0.5;
    private static final int GROW_RATIO = 2;

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

    public ArrayList() {
       this(DEFAULT_CAPACITY); 
    }

    public ArrayList(final int capacity) {
        elements = (T[]) (new Object[capacity]);
    }

    public ArrayList(final List<T> data) {
        size = data.size();
        elements = (T[]) (new Object[size]);
        for (int i = 0; i < size; ++i) {
            elements[i] = data.get(i);
        }
    }

    public ArrayList(final T[] data) {
        size = data.length;
        elements = (T[]) new Object[size];
        for (int i = 0; i < size; ++i) {
            elements[i] = data[i];
        }
    }

	@Override
	public void add(final T value) {
        ensureCapacity();
        elements[size++] = value;
	}

	@Override
	public void remove(final Object o) {
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
	public void add(final int index, final T element) {
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

	private void ensureCapacity() {
        double ratio = 1;
        if (elements.length == size) {
            ratio = GROW_RATIO;
        } else if (size * 3 <= elements.length) {
            ratio = SHRINK_RATIO;
        } else {
            return;
        }

        final int newSize = (int)Math.ceil((elements.length * ratio));
        elements = (T[]) ArrayList.copyOf(elements, newSize);
    }
}
