package za.ac.sun.cs.algos;

import java.util.Arrays;
import java.util.Comparator;

public final class Utilities {
    private static final int SORT_THRESHOLD = 32;
    private static final int MERGESORT_THRESHOLD = 16;

    private Utilities() {
        // What goes in here?
    }

    public static <T> void sort(T[] data, Comparator<? super T> cmp) {
        // Sorts the data array, using the cmp comparator.
        if (cmp == null || data == null) {
            throw new NullPointerException();
        }

        // No sorting needed
        if (data.length <= 1) {
            return;
        }

        if (data.length < SORT_THRESHOLD) {
            insertionSort(data, cmp, 0, data.length - 1);

        } else {
            @SuppressWarnings("unchecked")
            T[] aux = (T[]) new Object[data.length];
            mergeSort(data, aux, cmp, 0, data.length - 1);
        }

    }

    public static <T extends Comparable<? super T>> void sort(T[] data) {
        // Sorts the data array of the mutually comparable elements by their natural
        // order
        if (data == null) {
            throw new NullPointerException();
        }

        sort(data, Comparator.naturalOrder());
    }

    private static <T> void insertionSort(T[] data, Comparator<? super T> cmp,
            int low, int high) {
        // System.out.println("INSERTION SORT");

        for (int i = low + 1; i <= high; i++) {
            T currElem = data[i];
            int j = i - 1;

            while (j >= low && cmp.compare(data[j], currElem) > 0) {
                data[j + 1] = data[j];
                j--;
            }
            data[j + 1] = currElem;
        }
    }

    private static <T> void mergeSort(T[] data, T[] aux, Comparator<? super T> cmp, int low, int high) {
        // System.out.println("MERGE SORT");
        if (low < high) {
            // Insertion Sort Threshold
            if (high - low + 1 < MERGESORT_THRESHOLD) {
                insertionSort(data, cmp, low, high);
            } else {
                int mid = (low + high) / 2;
                // Sort 1st and 2nd halves
                mergeSort(data, aux, cmp, low, mid);
                mergeSort(data, aux, cmp, mid + 1, high);
                merge(data, aux, cmp, low, mid, high);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> void merge(T[] data, T[] aux, Comparator<? super T> cmp, int low, int mid, int high) {
        // Copy data to aux array
        for (int k = low; k <= high; k++) {
            aux[k] = data[k];
        }

        int a = low;
        int b = mid + 1;

        for (int i = low; i <= high; i++) {
            if (a > mid) {
                data[i] = aux[b];
                b++;

            } else if (b > high) {
                data[i] = aux[a];
                a++;

            } else if (cmp.compare(aux[a], aux[b]) <= 0) {
                data[i] = aux[a];
                a++;

            } else {
                data[i] = aux[b];
                b++;
            }
        }
    }

}
