package com.flashquiz.io;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import com.flashquiz.model.Card;
import com.flashquiz.model.Deck;
import com.flashquiz.model.TextCard;
import com.flashquiz.model.MultipleChoiceCard;
import com.flashquiz.model.TrueFalseCard;
import com.flashquiz.exception.InvalidDeckException;
import com.flashquiz.exception.InvalidInputException;

// Unit 7: File handling - File class, FileReader/BufferedReader, FileWriter/BufferedWriter
public class DeckFileReader {

    // Unit 7: Reading text file line by line using File, FileReader, and BufferedReader
    public static Deck loadDeckFromFile(String filePath) throws InvalidDeckException, IOException {
        File file = new File(filePath); // Unit 7: File class

        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("Deck file not found at: " + filePath);
        }

        String deckName = file.getName();
        if (deckName.endsWith(".txt")) {
            deckName = deckName.substring(0, deckName.length() - 4);
        }
        // Capitalize / format deck name
        deckName = deckName.replace('_', ' ').toUpperCase();

        Deck deck = new Deck(deckName, "Loaded from " + file.getName() + " (" + file.length() + " bytes)");

        BufferedReader reader = null;
        try {
            // Unit 7: Chaining FileReader into BufferedReader for efficient line reading
            reader = new BufferedReader(new FileReader(file));
            String line;
            int lineNumber = 0;
            int cardId = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String trimmed = line.trim();

                // Skip comments and empty lines
                if (trimmed.length() == 0 || trimmed.startsWith("#")) {
                    continue;
                }

                // Parse pipe-delimited format: TYPE|question|answer|extra...
                // Split with limit to preserve pipes in questions if any
                String[] tokens = splitByPipe(trimmed);
                if (tokens.length < 3) {
                    System.out.println("[Warning] Skipping malformed line " + lineNumber + ": " + trimmed);
                    continue;
                }

                String type = tokens[0].trim().toUpperCase();
                Card card = null;

                if (type.equals("TEXT")) {
                    // TEXT|question|answer|topic|points
                    String question = tokens[1].trim();
                    String answer = tokens[2].trim();
                    String topic = (tokens.length > 3) ? tokens[3].trim() : "General";
                    int points = (tokens.length > 4) ? parsePoints(tokens[4]) : 10;
                    card = new TextCard(cardId, question, answer, topic, points);

                } else if (type.equals("MCQ")) {
                    // MCQ|question|correctOption|optionsSeparatedBySemicolon|topic|points
                    String question = tokens[1].trim();
                    char correctOption = tokens[2].trim().charAt(0);
                    String rawOptions = (tokens.length > 3) ? tokens[3].trim() : "Option A;Option B";
                    String[] options = splitBySemicolon(rawOptions);
                    String topic = (tokens.length > 4) ? tokens[4].trim() : "General";
                    int points = (tokens.length > 5) ? parsePoints(tokens[5]) : 15;
                    card = new MultipleChoiceCard(cardId, question, options, correctOption, topic, points);

                } else if (type.equals("TF") || type.equals("TRUE_FALSE")) {
                    // TF|question|true/false|topic|points
                    String question = tokens[1].trim();
                    boolean correct = Boolean.parseBoolean(tokens[2].trim().toLowerCase());
                    String topic = (tokens.length > 3) ? tokens[3].trim() : "General";
                    int points = (tokens.length > 4) ? parsePoints(tokens[4]) : 10;
                    card = new TrueFalseCard(cardId, question, correct, topic, points);
                } else {
                    System.out.println("[Warning] Unknown card type '" + type + "' on line " + lineNumber);
                    continue;
                }

                if (card != null) {
                    deck.addCard(card);
                    cardId++;
                }
            }
        } finally {
            // Unit 7: Stream cleanup in finally block
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("[Warning] Failed to close reader: " + e.getMessage());
                }
            }
        }

        // Validate the loaded deck (Unit 6 checked exception)
        deck.validateDeck();
        return deck;
    }

    // Unit 7: Appending a card to a deck file using FileWriter (append mode) and BufferedWriter
    public static void appendCardToFile(String filePath, Card card) throws IOException {
        if (card == null) {
            throw new InvalidInputException("Cannot append null Card to file.");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("Cannot append to non-existent file: " + filePath);
        }

        BufferedWriter writer = null;
        try {
            // Unit 7: FileWriter with boolean append=true
            writer = new BufferedWriter(new FileWriter(file, true));

            String formattedLine = formatCardForFile(card);
            writer.newLine(); // Unit 7: BufferedWriter.newLine()
            writer.write(formattedLine); // Unit 7: BufferedWriter.write()
            writer.flush(); // Flush buffer
            System.out.println("[File I/O] Card #" + card.getId() + " appended successfully to " + file.getName());
        } finally {
            if (writer != null) {
                try {
                    writer.close(); // Close stream releases OS lock
                } catch (IOException e) {
                    System.out.println("[Warning] Failed to close writer: " + e.getMessage());
                }
            }
        }
    }

    // Unit 7: Discovers available deck files in the data directory using File.listFiles()
    public static File[] listAvailableDeckFiles(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return new File[0];
        }

        File[] allFiles = dir.listFiles();
        if (allFiles == null) {
            return new File[0];
        }

        // Count .txt files without heavy collections
        int txtCount = 0;
        for (int i = 0; i < allFiles.length; i++) {
            if (allFiles[i].isFile() && allFiles[i].getName().endsWith(".txt")) {
                txtCount++;
            }
        }

        File[] deckFiles = new File[txtCount];
        int index = 0;
        for (int i = 0; i < allFiles.length; i++) {
            if (allFiles[i].isFile() && allFiles[i].getName().endsWith(".txt")) {
                deckFiles[index++] = allFiles[i];
            }
        }
        return deckFiles;
    }

    private static String formatCardForFile(Card card) {
        if (card instanceof TextCard) {
            return "TEXT|" + card.getQuestion() + "|" + card.getAnswer() + "|" + card.getTopic() + "|" + card.getPoints();
        } else if (card instanceof MultipleChoiceCard) {
            MultipleChoiceCard mcq = (MultipleChoiceCard) card;
            StringBuffer opts = new StringBuffer();
            String[] options = mcq.getOptions();
            for (int i = 0; i < options.length; i++) {
                opts.append(options[i]);
                if (i < options.length - 1) {
                    opts.append(";");
                }
            }
            return "MCQ|" + card.getQuestion() + "|" + mcq.getCorrectOptionChar() + "|" + opts.toString() + "|" + card.getTopic() + "|" + card.getPoints();
        } else if (card instanceof TrueFalseCard) {
            TrueFalseCard tf = (TrueFalseCard) card;
            return "TF|" + card.getQuestion() + "|" + tf.isCorrectAnswer() + "|" + card.getTopic() + "|" + card.getPoints();
        } else {
            return "TEXT|" + card.getQuestion() + "|" + card.getAnswer() + "|" + card.getTopic() + "|" + card.getPoints();
        }
    }

    // Manual splitting helper avoiding regex overhead
    private static String[] splitByPipe(String line) {
        // Count pipes
        int pipes = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '|') {
                pipes++;
            }
        }
        String[] tokens = new String[pipes + 1];
        int start = 0;
        int tokenIdx = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '|') {
                tokens[tokenIdx++] = line.substring(start, i);
                start = i + 1;
            }
        }
        tokens[tokenIdx] = line.substring(start);
        return tokens;
    }

    private static String[] splitBySemicolon(String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ';') {
                count++;
            }
        }
        String[] tokens = new String[count + 1];
        int start = 0;
        int tokenIdx = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ';') {
                tokens[tokenIdx++] = line.substring(start, i);
                start = i + 1;
            }
        }
        tokens[tokenIdx] = line.substring(start);
        return tokens;
    }

    private static int parsePoints(String str) {
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return 10;
        }
    }
}
