package com.daa;

import java.util.Random;

public class QuickSort {
    private static final Random random = new Random();

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) return;
        metrics.enterRecursion();
        quickSort(a, 0, a.length - 1, metrics);
        metrics.exitRecursion();
    }

    private static void quickSort(int[] a, int low, int high, Metrics metrics) {
        while (low < high) {
            int pivotIndex = low + random.nextInt(high - low + 1);
            swap(a, low, pivotIndex);
            int pivot = a[low];

            // 3-way partition
            int lt = low;
            int gt = high;
            int i = low + 1;

            while (i <= gt) {
                metrics.incrementComparisons();
                if (a[i] < pivot) {
                    swap(a, lt++, i++);
                } else if (a[i] > pivot) {
                    metrics.incrementComparisons();
                    swap(a, i, gt--);
                } else {
                    metrics.incrementComparisons();
                    i++;
                }
            }

            // Меньшую часть рекурсивно, большую — циклом
            if ((lt - 1 - low) < (high - (gt + 1))) {
                metrics.enterRecursion();
                quickSort(a, low, lt - 1, metrics);
                metrics.exitRecursion();
                low = gt + 1; // Обрабатываем правую часть в следующей итерации цикла
            } else {
                metrics.enterRecursion();
                quickSort(a, gt + 1, high, metrics);
                metrics.exitRecursion();
                high = lt - 1; // Обрабатываем левую часть в следующей итерации цикла
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}