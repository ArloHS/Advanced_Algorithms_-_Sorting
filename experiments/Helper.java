// package report.experiments;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Helper {

    public static Integer[] generateData(int size, String type) {
        // New instance every call with a ranodm seed
        Random random = new Random(System.nanoTime());

        Integer[] data = new Integer[size];
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(1000);
        }

        if (type.equals("Sorted")) {
            Arrays.sort(data);

        } else if (type.equals("Reverse")) {
            Arrays.sort(data, Comparator.reverseOrder());

        } else if (type.equals("Random")) {
            // Nothing Happens
        } else {
            System.out.println("INCORRECT TYPE");
            return null;
        }

        return data;
    }

    public static void writeResultsToFile(List<String> results, String filePath) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filePath));
            writer.println("Size, Type, Time(s)");
            for (String line : results) {
                writer.println(line);
            }

            writer.close();
            System.out.println("Results successfully written to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void writeHybridResultsToFile(List<String> results, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            writer.println("Size,SortThreshold,MergeThreshold,Type,Time(s)");
            for (String line : results) {
                writer.println(line);
            }
            writer.close();
            System.out.println("Results successfully written to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void printProgressBar(int current, int total, String label) {
        int barWidth = 40;
        float percentage = (float) current / total;
        int progressChars = (int) (percentage * barWidth);

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barWidth; i++) {
            if (i < progressChars) {
                bar.append("=");
            } else if (i == progressChars) {
                bar.append(">");
            } else {
                bar.append(" ");
            }
        }
        bar.append("] ");
        bar.append(String.format("%3d%%", (int) (percentage * 100)));
        bar.append(" ").append(label).append(": ").append(current).append("/").append(total);

        System.out.print("\r" + bar);

        if (current == total) {
            System.out.println();
        }

        System.out.flush();
    }
}
