package org.example;

import java.util.Collections;
import java.util.List;

public class BubbleSort extends GeneralSort {

    public BubbleSort(int maxLengthOfArray) {
        super(maxLengthOfArray);
    }

    public SortingTypes getSortingType() {
        return SortingTypes.BUBBLE;
    }


    @Override
    public List<Integer> sort(List<Integer> arr) {
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i + 1; j < arr.size(); j++) {
                if (arr.get(i) > arr.get(j)) {
                    Collections.swap(arr, i, j);
                }
            }
        }
        return arr;
    }
}
