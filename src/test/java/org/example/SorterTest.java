package org.example;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThrows;

public class SorterTest {
    BubbleSort bubbleSort = new BubbleSort(7);
    MergeSort mergeSort = new MergeSort(15);
    Sorter sorter = new Sorter(List.of(bubbleSort, mergeSort));

    @Test
    public void maxLengthTest() {
        assertThrows(RuntimeException.class, () -> sorter.sort(List.of(1, 2, 55, 3, 7, 6, 9, 10, 8), SortingTypes.BUBBLE));
    }
    @Test
    public void lackOfStrategy() {
        assertThrows(RuntimeException.class, () -> sorter.sort(List.of(1, 2, 3), SortingTypes.BASIC));
    }
}
