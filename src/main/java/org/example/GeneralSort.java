package org.example;

import java.util.List;

public abstract class GeneralSort implements StrategyOfSorting {
    int maxLengthOfArray;

    public GeneralSort(int maxLengthOfArray) {
        this.maxLengthOfArray = maxLengthOfArray;
    }

    public void checkLength(List<Integer> array){
        if (array.size() > maxLengthOfArray) {
            throw new RuntimeException("Your list is too large");
        }
    }
}
