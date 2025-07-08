// package report.experiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class InsertionBenchmark {

    public static void main(String args[]) {
        System.out.println("Starting...");
        // SETUP
        String[] types = { "Sorted", "Reverse", "Random" };
        Integer[] sizes = { 10, 100, 1000, 10000, 100000, 1000000 };
        int amountOfTests = 50;
        List<String> RESULTS = new ArrayList<>();

        // Timing
        long totalTime = 0;

        // FOR Insertion
        Utilities.SORT_THRESHOLD = 10000000;

        // Warm up phase for JVM
        System.out.println("Warming up JVM");
        int warmUpIterations = 5;
        int warmUpSize = 222222;
        for (int w = 0; w < warmUpIterations; w++) {
            // Update progress bar
            Helper.printProgressBar(w + 1, warmUpIterations, "JVM Warmup");

            Integer[] warmUpData = Helper.generateData(warmUpSize, "Random");
            Utilities.sort(warmUpData.clone());
        }

        System.out.println("Warm up completed");

        System.out.println("Base Insertion sort with: " + amountOfTests + " amount of iterations per test.");
        int totalTests = types.length * sizes.length * amountOfTests;
        int currentTest = 0;

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < sizes.length; j++) {
                totalTime = 0;
                for (int k = 0; k < amountOfTests; k++) {
                    // Update progress bar
                    Helper.printProgressBar(k + 1, amountOfTests, "Current Test");
                    currentTest++;

                    Integer[] dataArr = Helper.generateData(sizes[j], types[i]);
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

                // Convert nanoseconds to seconds
                double avgTimeInSeconds = totalTime / (double) (amountOfTests * 1_000_000_000L);
                String resultLine = String.format(Locale.US, "%d, %s, %.6f", sizes[j], types[i], avgTimeInSeconds);
                RESULTS.add(resultLine);
            }
        }

        Helper.writeResultsToFile(RESULTS, "results/BaseInsertionSort.csv");
        System.err.println("Test Finished");
    }
}
