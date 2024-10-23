package com.hahsm.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MergeSortTests {
    @Test
    public void sortEmptyList() {
        Sorter<Integer> sorter = new MergeSort<Integer>();
        List<Integer> actual = new ArrayList<Integer>(0);
        sorter.sort(actual);

        List<Integer> expected = new ArrayList<Integer>(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sortOneElementList() {
        Sorter<Integer> sorter = new MergeSort<Integer>();
        List<Integer> actual = Arrays.asList(0);
        sorter.sort(actual);

        List<Integer> expected = Arrays.asList(0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sortZerosElementList() {
        Sorter<Integer> sorter = new MergeSort<Integer>();
        List<Integer> actual = Arrays.asList(0, 0, 0, 0, 0);
        sorter.sort(actual);

        List<Integer> expected = Arrays.asList(0, 0, 0, 0, 0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sortOrderedElementList() {
        Sorter<Integer> sorter = new MergeSort<Integer>();
        List<Integer> actual = Arrays.asList(1, 2, 3, 4, 5);
        sorter.sort(actual);

        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void sortReversedElementList() {
        Sorter<Integer> sorter = new MergeSort<Integer>();
        List<Integer> actual = Arrays.asList(5, 4, 3, 2, 1);
        sorter.sort(actual);

        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);

        Assertions.assertEquals(expected, actual);
    }
}
