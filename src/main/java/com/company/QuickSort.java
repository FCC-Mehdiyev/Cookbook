package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class QuickSort<E extends Comparable<E>> {

    /*
     * Time Complexity Analysis:
     * Worst case: T(n) = (n - 1) + (n - 2) + ... + 2 + 1
     * Worst case: T(n) = O(n^2) [Quadratic time]
     *
     * Best case = T(n) = T(n/2) + T(n/2) + n
     * The T(n/2) represents the recursive quick sort call on the two subarrays
     * The n represents the partition time
     * Best case: T(n) = O(nlogn) [Linearithmic time]
     *
     * Average case: T(n) = O(nlogn) [Linearithmic time]
     */

    /**
     * Sort any given array via the QuickSort algorithm
     *
     * @param arr the array to sort
     */
    public void sort(E[] arr) {
        // Convert the array into a list
        ArrayList<E> list = new ArrayList<E>(Arrays.asList(arr));
        this.sort(list); // Sort the list
        // Set the array's values to the list's
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
    }

    /**
     * Sort any given array via the QuickSort algorithm
     *
     * @param arr the array to sort
     * @param first the first index
     * @param last the last index
     */
    public void sort(E[] arr, int first, int last) {
        // Convert the array into a list
        ArrayList<E> list = new ArrayList<E>(Arrays.asList(arr));
        this.sort(list, first, last); // Sort the list
        // Set the array's values to the list's
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
    }

    /**
     * Sort any given ArrayList via the QuickSort algorithm
     *
     * @param list the ArrayList to sort
     */
    public void sort(ArrayList<E> list) {
        this.sort(list, 0, list.size() - 1);
    }

    /**
     * Sort any given ArrayList via the QuickSort algorithm
     *
     * @param list the ArrayList to sort
     * @param first the first index
     * @param last the last index
     */
    public void sort(ArrayList<E> list, int first, int last) {

        // Edge cases
        if (list == null || list.size() == 0) {
            return;
        }

        if(first < last) {
            // Get the pivot index
            int pivot = partition(list, first, last);

            // Sort the elements before and after the partition
            this.sort(list, first, pivot); // Left sub-array
            this.sort(list,pivot + 1, last); // Right sub-array
        }
    }


    /**
     * Select the middle element as the pivot
     * Place it in its proper position in the array
     * Place all smaller elements to the left and greater elements to the right of the pivot
     *
     * @param list the list to partition
     * @param first the first index
     * @param last the last index
     * @return the list's pivot
     */
    private int partition(ArrayList<E> list, int first, int last) {
        int pivot = (first + last) / 2; // Choose the middle of the list as the pivot index
        E pivotElement = list.get(pivot); // Get the element at the pivot
        first--;
        last++;

        while (true) {
            // Search forward from left for a value more than the pivot element
            first++;
            while (list.get(first).compareTo(pivotElement) < 0) {
                first++;
            }

            // Search backward from right for a value less than the pivot element
            last--;
            while (list.get(last).compareTo(pivotElement) > 0) {
                last--;
            }

            if (first >= last) {
                return last;
            }

            // Swap the last and first value elements
            E temp = list.get(first);
            list.set(first, list.get(last));
            list.set(last, temp);
        }
    }

}