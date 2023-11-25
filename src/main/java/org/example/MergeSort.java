package org.example;
import java.util.Collections;
import java.util.List;

public class MergeSort extends GeneralSort {


    public MergeSort(int maxLengthOfArray) {
        super(maxLengthOfArray);
    }

    public SortingTypes getSortingType() {
        return SortingTypes.MERGE;
    }

    @Override
    public List<Integer> sort(List<Integer> list) {

        Collections.sort(list);
        return list;
    }
}
