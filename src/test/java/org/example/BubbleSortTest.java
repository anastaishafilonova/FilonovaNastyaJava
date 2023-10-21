package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BubbleSortTest {
    BubbleSort bubbleSort = new BubbleSort(10);
    Sorter sorter = new Sorter(List.of(bubbleSort));
    @Test
    public void getSortingTypeTest() {
        assertEquals(bubbleSort.getSortingType(), SortingTypes.BUBBLE);
    }

    @Test
    public void SortTest() {
        List<Integer> list = new ArrayList<>(List.of(1, 5, 3, 8, 1, 2, 7));
        List<Integer> sortedList = new ArrayList<>(bubbleSort.sort(list));
        assertEquals(sortedList, List.of(1, 1, 2, 3, 5, 7, 8));
    }


}
