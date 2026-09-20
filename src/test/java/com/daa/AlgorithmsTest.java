package com.daa;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmsTest {

    @Test
    void testCorrectnessMergeAndQuickSort() {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int n = rand.nextInt(500) + 1;
            int[] arr1 = rand.ints(n, -1000, 1000).toArray();
            int[] arr2 = arr1.clone();
            int[] expected = arr1.clone();

            Arrays.sort(expected);
            MergeSort.sort(arr1, new Metrics());
            QuickSort.sort(arr2, new Metrics());

            assertArrayEquals(expected, arr1);
            assertArrayEquals(expected, arr2);
        }
    }

    @Test
    void testQuickSortMaxDepth() {
        int n = 100000;
        int[] sorted = new int[n];
        for (int i = 0; i < n; i++) sorted[i] = i;

        Metrics metrics = new Metrics();
        QuickSort.sort(sorted, metrics);

        double maxAllowedDepth = 2 * (Math.log(n) / Math.log(2));
        assertTrue(metrics.getMaxDepth() <= maxAllowedDepth,
                "Max depth exceeded bound: " + metrics.getMaxDepth() + " > " + maxAllowedDepth);
    }

    @Test
    void testQuickSelect() {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int n = rand.nextInt(500) + 1;
            int[] arr = rand.ints(n, -1000, 1000).toArray();
            int k = rand.nextInt(n);

            int[] sorted = arr.clone();
            Arrays.sort(sorted);

            int val = QuickSelect.select(arr, k, new Metrics());
            assertEquals(sorted[k], val);
        }
    }

    @Test
    void testEdgeCases() {
        Metrics m = new Metrics();

        // Пустой массив
        int[] empty = new int[0];
        MergeSort.sort(empty, m);
        QuickSort.sort(empty, m);
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(empty, 0, m));

        // Одинаковые элементы
        int[] same = new int[]{5, 5, 5, 5, 5};
        QuickSort.sort(same, m);
        assertArrayEquals(new int[]{5, 5, 5, 5, 5}, same);
    }
}