package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MergeSortTest {
    MergeSort mergeSort = new MergeSort(100);
    @Test
    public void getSortingTypeTest() {
        assertEquals(mergeSort.getSortingType(), SortingTypes.MERGE);
    }

    @Test
    public void SortTest() {
        List<Integer> list = new ArrayList<>(List.of(1, 5, 3, 8, 1, 2, 7));
        List<Integer> sortedList = new ArrayList<>();
        sortedList = mergeSort.sort(list);
        assertEquals(sortedList, List.of(1, 1, 2, 3, 5, 7, 8));
    }
}
