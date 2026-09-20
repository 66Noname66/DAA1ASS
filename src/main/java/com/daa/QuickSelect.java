package com.daa;

import java.util.Random;

public class QuickSelect {
    private static final Random random = new Random();

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid array or k index out of bounds.");
        }
        metrics.enterRecursion();
        int result = quickSelect(a, 0, a.length - 1, k, metrics);
        metrics.exitRecursion();
        return result;
    }

    private static int quickSelect(int[] a, int low, int high, int k, Metrics metrics) {
        if (low == high) return a[low];

        int pivotIndex = low + random.nextInt(high - low + 1);
        swap(a, low, pivotIndex);
        int pivot = a[low];

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

        if (k >= lt && k <= gt) {
            return a[k];
        } else if (k < lt) {
            metrics.enterRecursion();
            int res = quickSelect(a, low, lt - 1, k, metrics);
            metrics.exitRecursion();
            return res;
        } else {
            metrics.enterRecursion();
            int res = quickSelect(a, gt + 1, high, k, metrics);
            metrics.exitRecursion();
            return res;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}