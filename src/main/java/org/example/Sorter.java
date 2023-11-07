package org.example;

import java.util.ArrayList;
import java.util.List;

public class Sorter {
    protected List<StrategyOfSorting> sortingStrategies;

    public Sorter(List<StrategyOfSorting> sortingStrategies) {
        this.sortingStrategies = sortingStrategies;
    }

    public List<Integer> sort(List<Integer> list, SortingTypes sortingType) {
        for (StrategyOfSorting strategy : sortingStrategies) {
            if (strategy.getSortingType() != sortingType) {
                continue;
            }
            try {
                strategy.checkLength(list);
                List<Integer> sortedList = new ArrayList<>(list);
                return strategy.sort(sortedList);
            } catch (RuntimeException e) {
                //
            }


        }
        throw new RuntimeException("Strategy has not found");
    }
}
