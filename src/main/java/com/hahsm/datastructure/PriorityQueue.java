package com.hahsm.datastructure;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.hahsm.common.Util;
import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.adt.Queue;

public class PriorityQueue<T> implements Queue<T> {

    private static final int DEFAULT_CAPAICITY = 11;

    private List<T> queueList;

    private Comparator<T> comparator;

    public PriorityQueue(final Comparator<T> comparator) {
        setComparator(comparator);
        queueList = new ArrayList<>(DEFAULT_CAPAICITY);
    }

    public PriorityQueue(int capacity, final Comparator<T> comparator) {
        setComparator(comparator);
        queueList = new ArrayList<>(capacity);
    }

    @Override
    public void add(T value) {
        queueList.add(value);
        swim(queueList.size() - 1);
    }

    @Override
    public int size() {
        return queueList.size();
    }

    @Override
    public boolean isEmpty() {
        return queueList.size() == 0;
    }

    @Override
    public T front() throws NoClassDefFoundError {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        return queueList.get(0);
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }

        return removeAt(0);

        //final T front = queueList.get(0);
        //Util.swapList(queueList, 0, queueList.size() - 1);
        //queueList.remove(queueList.size() - 1);
        //heapify(0);
        //
        //return front;
    }

    @Override
    public void clear() {
        queueList.clear();
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private void heapify(int i) {
        if (i >= size()) {
            return;
        }
        final int left = i << 1;
        final int right = (i << 1) | 1;
        int largest = i;

        if (left < size() && comparator.compare(queueList.get(left), queueList.get(largest)) == -1) {
            largest = left;
        }

        if (right < size() && comparator.compare(queueList.get(right), queueList.get(largest)) == -1) {
            largest = right;
        }

        if (largest != i) {
            Util.swapList(queueList, largest, i);
            heapify(largest);
        }
    }

    private void swim(final int i) {
        if (i == 0) {
            return;
        }
        final int parent = i >> 1;
        if (comparator.compare(queueList.get(parent), queueList.get(i)) <= 0) {
            return;
        }
        Util.swapList(queueList, parent, i);
        swim(parent);
    }

    @Override
    public boolean remove(T order) {
        int idx = queueList.indexOf(order);
        if (idx == -1) {
            throw new IllegalArgumentException("Cannot find order");
        }
        removeAt(idx);
        return true;
    }

	@Override
	public boolean contains(T target) {
        return queueList.contains(target); 
	}

	@Override
	public Iterator<T> iterator() {
        return queueList.iterator();
	}

    private T removeAt(int index) {
        final T value = queueList.get(index);
        Util.swapList(queueList, index, queueList.size() - 1);
        queueList.remove(queueList.size() - 1);
        heapify(0);

        return value;
    }
}
