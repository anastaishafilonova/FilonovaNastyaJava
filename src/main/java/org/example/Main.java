package org.example;

import org.example.BubbleSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Input size of your list");
        int n;
        n = input.nextInt();
        System.out.println("Input your list");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(input.nextInt());
        }
        //sort(list, typeOfSort);
        BubbleSort bubbleSort = new BubbleSort(7);
        MergeSort mergeSort = new MergeSort(100);
        Sorter sorter = new Sorter(List.of(bubbleSort, mergeSort));
        List<Integer> sortedList = new ArrayList<>();
        sortedList = sorter.sort(list, SortingTypes.MERGE);
        System.out.println(sortedList);
    }

}
