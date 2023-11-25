package org.example;

import org.example.SortingTypes;

import java.util.List;

public interface StrategyOfSorting {
    SortingTypes getSortingType();

    void checkLength(List<Integer> list);
    List<Integer> sort(List<Integer> list);
}
