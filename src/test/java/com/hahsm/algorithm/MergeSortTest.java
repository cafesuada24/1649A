package com.hahsm.algorithm;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.ArrayList;

public class MergeSortTest {
    private final SortStrategy sorter = new MergeSort();

    @Test
    public void sortEmptyList() {
        // SortStrategy<Integer> sorter = new MergeSort<Integer>();
        List<Integer> actual = new ArrayList<Integer>(0);
        sorter.sort(actual);

        List<Integer> expected = new ArrayList<Integer>(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sortOneElementList() {
        // SortStrategy<Integer> sorter = new MergeSort<Integer>();
        List<Integer> actual = new ArrayList<>(0);
        sorter.sort(actual);

        List<Integer> expected = new ArrayList<>(0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sortZerosElementList() {
        // SortStrategy<Integer> sorter = new MergeSort<Integer>();
        List<Integer> actual = new ArrayList<Integer>(0, 0, 0, 0, 0);
        sorter.sort(actual);

        List<Integer> expected = new ArrayList<Integer>(0, 0, 0, 0, 0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sortOrderedElementList() {
        // SortStrategy<Integer> sorter = new MergeSort<Integer>();
        List<Integer> actual = new ArrayList<>(1, 2, 3, 4, 5);
        sorter.sort(actual);

        List<Integer> expected = new ArrayList<>(1, 2, 3, 4, 5);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sortReversedElementList() {
        // SortStrategy<Integer> sorter = new MergeSort<Integer>();
        List<Integer> actual = new ArrayList<Integer>(5, 4, 3, 2, 1);
        sorter.sort(actual);

        List<Integer> expected = new ArrayList<>(1, 2, 3, 4, 5);

        Assertions.assertEquals(expected, actual);
    }
}
