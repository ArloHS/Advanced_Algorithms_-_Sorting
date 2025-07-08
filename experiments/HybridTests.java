// package report.experiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class HybridTests {

    public static void main(String[] args) {
        System.out.println("Starting...");
        // SETUP
        String[] types = { "Sorted", "Reverse", "Random" };
        Integer[] sizes = { 10, 100, 1000, 10000, 100000, 1000000 };
        int amountOfTests = 50;
        List<String> RESULTS = new ArrayList<>();

        // Threshold ranges
        Integer[] sortThresholds = { 0, 4, 8, 16, 32, 64 };
        Integer[] mergeThresholds = { 0, 4, 8, 16, 32 };

        // Warm up phase for JVM
        System.out.println("Warming up JVM");
        int warmUpIterations = 5;
        int warmUpSize = 222222;
        for (int w = 0; w < warmUpIterations; w++) {
            Helper.printProgressBar(w + 1, warmUpIterations, "JVM Warmup");
            Integer[] warmUpData = Helper.generateData(warmUpSize, "Random");
            Utilities.sort(warmUpData.clone());
        }
        System.out.println("Warm up completed");

        // Test: Vary both SORT_THRESHOLD and MERGESORT_THRESHOLD
        System.out.println("Testing with both SORT_THRESHOLD and MERGESORT_THRESHOLD varied");
        runBenchmark(types, sizes, amountOfTests, sortThresholds, mergeThresholds, RESULTS);

        // Write results to file
        Helper.writeHybridResultsToFile(RESULTS, "results/HybridMergeSort.csv");
        System.out.println("Test Finished");
    }

    private static void runBenchmark(String[] types, Integer[] sizes, int amountOfTests,
            Integer[] sortThresholds, Integer[] mergeThresholds,
            List<String> RESULTS) {
        for (int st : sortThresholds) {
            for (int mt : mergeThresholds) {
                // Set the thresholds in Utilities
                Utilities.SORT_THRESHOLD = st;
                Utilities.MERGESORT_THRESHOLD = mt;

                for (String type : types) {
                    for (int size : sizes) {
                        long totalTime = 0;
                        for (int k = 0; k < amountOfTests; k++) {
                            Helper.printProgressBar(k + 1, amountOfTests, "Current Test");
                            Integer[] dataArr = Helper.generateData(size, type);
                            Integer[] data = dataArr.clone();

                            long startTime = System.nanoTime();
                            Utilities.sort(data);
                            long endTime = System.nanoTime();
                            totalTime += (endTime - startTime);

                            // Verify sorted
                            Arrays.sort(dataArr);
                            if (!Arrays.equals(data, dataArr)) {
                                System.out.println("SORT FAILED!");
                            }
                        }

                        // Calculate average time in seconds
                        double avgTimeInSeconds = totalTime / (double) (amountOfTests * 1_000_000_000L);
                        String resultLine = String.format(Locale.US, "%d,%d,%d,%s,%.6f",
                                size, st, mt, type, avgTimeInSeconds);
                        RESULTS.add(resultLine);
                    }
                }
            }
        }
    }
}