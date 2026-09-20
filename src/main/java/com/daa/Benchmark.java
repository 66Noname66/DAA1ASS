package com.daa;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {
    private static final int RUNS = 5;
    private static final int[] SIZES = {1000, 10000, 100000, 1000000};
    private static final String[] TYPES = {"random", "sorted", "duplicates"};

    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("results.csv"))) {
            writer.println("algorithm,input,n,time_ms,comparisons,max_depth");

            for (int n : SIZES) {
                for (String type : TYPES) {
                    benchmarkAlgorithm("MergeSort", type, n, writer);
                    benchmarkAlgorithm("QuickSort", type, n, writer);
                    benchmarkAlgorithm("QuickSelect", type, n, writer);
                }
            }
            System.out.println("Benchmark finished. Results saved to results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void benchmarkAlgorithm(String algo, String type, int n, PrintWriter writer) {
        long[] times = new long[RUNS];
        long medianComparisons = 0;
        int medianDepth = 0;

        for (int r = 0; r < RUNS; r++) {
            int[] data = generateData(type, n);
            Metrics metrics = new Metrics();

            long start = System.nanoTime();
            if (algo.equals("MergeSort")) {
                MergeSort.sort(data, metrics);
            } else if (algo.equals("QuickSort")) {
                QuickSort.sort(data, metrics);
            } else if (algo.equals("QuickSelect")) {
                QuickSelect.select(data, n / 2, metrics);
            }
            long end = System.nanoTime();

            times[r] = (end - start);
            if (r == RUNS / 2) {
                medianComparisons = metrics.getComparisons();
                medianDepth = metrics.getMaxDepth();
            }
        }

        Arrays.sort(times);
        double medianTimeMs = times[RUNS / 2] / 1_000_000.0;

        writer.printf("%s,%s,%d,%.3f,%d,%d\n", algo, type, n, medianTimeMs, medianComparisons, medianDepth);
    }

    private static int[] generateData(String type, int n) {
        int[] arr = new int[n];
        Random rand = new Random(42);
        if (type.equals("random")) {
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt();
        } else if (type.equals("sorted")) {
            for (int i = 0; i < n; i++) arr[i] = i;
        } else if (type.equals("duplicates")) {
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(10);
        }
        return arr;
    }
}