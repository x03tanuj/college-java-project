package com.flashquiz.io;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Unit 7: File handling - saving and loading persistent score records using character streams
public class ScoreManager {

    private static final String DEFAULT_SCORE_FILE = "scores.txt";

    // Unit 7: Writing scores to file using FileWriter and BufferedWriter
    public static void saveScore(String deckName, int score, int maxScore, double percentage, String rating) {
        File file = new File(DEFAULT_SCORE_FILE); // Unit 7: File class

        BufferedWriter writer = null;
        try {
            // Append mode: new FileWriter(file, true)
            writer = new BufferedWriter(new FileWriter(file, true));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = sdf.format(new Date());

            // Build record line
            String record = timestamp + "|" + deckName + "|" + score + "|" + maxScore + "|" + String.format("%.2f", percentage) + "%|" + rating;
            writer.write(record);
            writer.newLine(); // Platform-independent newline
            writer.flush();
            System.out.println("[ScoreManager] Saved score entry to " + file.getName());
        } catch (IOException e) {
            System.out.println("[Error] Failed to write score to " + DEFAULT_SCORE_FILE + ": " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("[Warning] Failed to close score writer: " + e.getMessage());
                }
            }
        }
    }

    // Unit 7: Reading and displaying scores from file using FileReader and BufferedReader
    public static void displayFileScores() {
        File file = new File(DEFAULT_SCORE_FILE);

        if (!file.exists() || file.length() == 0) {
            System.out.println("\n[Score History] No past quiz records found in " + DEFAULT_SCORE_FILE + ".");
            return;
        }

        System.out.println("\n==========================================================================");
        System.out.println("                   PERSISTENT SCORE HISTORY (" + DEFAULT_SCORE_FILE + ")");
        System.out.println("==========================================================================");
        System.out.printf("%-20s | %-24s | %-8s | %-10s | %-12s\n", "Date / Time", "Deck Name", "Points", "Accuracy", "Rating");
        System.out.println("---------------------+--------------------------+----------+------------+-------------");

        BufferedReader reader = null;
        int count = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    System.out.printf("%-20s | %-24s | %-8s | %-10s | %-12s\n",
                            parts[0], parts[1], parts[2] + "/" + parts[3], parts[4], parts[5]);
                    count++;
                }
            }
            System.out.println("==========================================================================");
            System.out.println("Total Recorded Attempts: " + count);
        } catch (IOException e) {
            System.out.println("[Error] Failed to read scores: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("[Warning] Failed to close score reader: " + e.getMessage());
                }
            }
        }
    }
}
