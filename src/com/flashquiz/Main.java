package com.flashquiz;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.flashquiz.model.Card;
import com.flashquiz.model.Deck;
import com.flashquiz.model.Flashcard;
import com.flashquiz.model.TextCard;
import com.flashquiz.model.MultipleChoiceCard;
import com.flashquiz.model.TrueFalseCard;
import com.flashquiz.model.CardMetadata;
import com.flashquiz.io.DeckFileReader;
import com.flashquiz.io.ScoreManager;
import com.flashquiz.exception.InvalidDeckException;
import com.flashquiz.exception.InvalidInputException;

// Unit 1: Bytecode, data types, variables, arrays, operators, decision and control statements
public class Main {

    private static final String DATA_DIRECTORY = "data";
    // Maximum history entries stored in arrays (Unit 1: arrays)
    private static final int MAX_HISTORY = 10;
    private static int[] scoreHistory = new int[MAX_HISTORY];
    private static String[] deckHistory = new String[MAX_HISTORY];
    private static double[] percentageHistory = new double[MAX_HISTORY];
    private static int historyCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Unit 1: primitive types (boolean, byte, short, int, long, float, double, char)
        boolean running = true;
        byte systemMode = 1; // 1 = Standard CLI Mode (demonstrating byte)
        short totalPossiblePoints = 100; // Demonstrating short
        int sessionQuizCount = 0; // Demonstrating int

        System.out.println("==========================================");
        System.out.println("  WELCOME TO CLI FLASH CARD QUIZ (LAB)    ");
        System.out.println("  Core Java Academic Lab Project (JDK 8+) ");
        System.out.println("==========================================");

        // Unit 1 & Unit 6: while control statement protected with try-catch-finally
        while (running) {
            System.out.println("\n---------------- MAIN MENU ----------------");
            System.out.println("1.  Start Quick Starter Quiz (Unit 1 Basics)");
            System.out.println("2.  Start OOP Flashcard Quiz (Unit 2 Objects & Decks)");
            System.out.println("3.  Start Inheritance & Polymorphism Quiz (Unit 3 Abstract & Subclasses)");
            System.out.println("4.  Load & Play Deck from File (Unit 7 File Handling)");
            System.out.println("5.  Add New Card to Deck File (Unit 7 File Appending)");
            System.out.println("6.  View Persistent Score History (Unit 7 scores.txt)");
            System.out.println("7.  View In-Memory Score History (Unit 1 Arrays)");
            System.out.println("8.  System & Variable Diagnostics");
            System.out.println("9.  Run Exception Handling Lab Test (Unit 6 Demo)");
            System.out.println("10. Exit");
            System.out.print("Select an option (1-10): ");

            String input = "";
            // Unit 6: Try/Catch with multiple catch blocks and finally
            try {
                input = scanner.nextLine();
                if (input == null || input.trim().length() == 0) {
                    throw new InvalidInputException("Input cannot be empty. Please choose 1 to 10.");
                }

                // Unit 6: NumberFormatException catch demonstration
                int choice = Integer.parseInt(input.trim());

                // Unit 6: ArrayIndexOutOfBoundsException catch demonstration
                if (choice < 1 || choice > 10) {
                    throw new ArrayIndexOutOfBoundsException("Menu option " + choice + " is out of bounds (allowed: 1 - 10).");
                }

                switch (choice) {
                    case 1:
                        runQuickQuiz(scanner, systemMode, totalPossiblePoints);
                        sessionQuizCount++;
                        break;
                    case 2:
                        runOopQuiz(scanner);
                        sessionQuizCount++;
                        break;
                    case 3:
                        runInheritanceQuiz(scanner);
                        sessionQuizCount++;
                        break;
                    case 4:
                        runFileDeckQuiz(scanner);
                        sessionQuizCount++;
                        break;
                    case 5:
                        addNewCardToDeck(scanner);
                        break;
                    case 6:
                        ScoreManager.displayFileScores();
                        break;
                    case 7:
                        displayScoreHistory();
                        break;
                    case 8:
                        displayDiagnostics(sessionQuizCount);
                        break;
                    case 9:
                        runExceptionLabDemo();
                        break;
                    case 10:
                        System.out.println("Thank you for using Flash Card Quiz! Goodbye.");
                        running = false;
                        break;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("[Exception Caught: NumberFormatException] Invalid number format: '" + input.trim() + "'. Expected an integer (1-10).");
            } catch (ArrayIndexOutOfBoundsException aie) {
                System.out.println("[Exception Caught: ArrayIndexOutOfBoundsException] " + aie.getMessage());
            } catch (InvalidInputException iie) {
                System.out.println("[Exception Caught: InvalidInputException] " + iie.getMessage());
            } catch (InvalidDeckException ide) {
                System.out.println("[Exception Caught: InvalidDeckException] " + ide.getMessage());
            } catch (IOException ioe) {
                System.out.println("[Exception Caught: IOException] File I/O Error: " + ioe.getMessage());
            } catch (Exception e) {
                System.out.println("[Exception Caught: Exception] Unexpected error: " + e.getMessage());
            } finally {
                // Unit 6: finally block executes every interaction cycle
            }
        }
        scanner.close();
    }

    // Unit 7: Loading and playing a Deck from data/*.txt file using DeckFileReader
    private static void runFileDeckQuiz(Scanner scanner) throws InvalidDeckException, IOException {
        System.out.println("\n--- LOAD DECK FROM FILE ---");
        File[] deckFiles = DeckFileReader.listAvailableDeckFiles(DATA_DIRECTORY);

        if (deckFiles.length == 0) {
            System.out.println("[Warning] No deck files (.txt) discovered in directory '" + DATA_DIRECTORY + "'.");
            return;
        }

        System.out.println("Available Decks:");
        for (int i = 0; i < deckFiles.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + deckFiles[i].getName() + " (" + deckFiles[i].length() + " bytes)");
        }
        System.out.print("Select deck number (1-" + deckFiles.length + "): ");
        String selInput = scanner.nextLine();

        int selectedIndex;
        try {
            selectedIndex = Integer.parseInt(selInput.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Selection must be an integer: " + selInput);
        }

        if (selectedIndex < 0 || selectedIndex >= deckFiles.length) {
            throw new ArrayIndexOutOfBoundsException("Selected deck index out of range (1 - " + deckFiles.length + ").");
        }

        File selectedFile = deckFiles[selectedIndex];
        System.out.println("[File I/O] Reading " + selectedFile.getName() + " using BufferedReader...");

        // Unit 7: Reading deck file line by line
        Deck loadedDeck = DeckFileReader.loadDeckFromFile(selectedFile.getPath());
        System.out.println("[File I/O] Successfully loaded deck with " + loadedDeck.getCardCount() + " cards!");

        com.flashquiz.service.Quiz quiz = new com.flashquiz.service.Quiz(loadedDeck);
        quiz.runQuiz(scanner);

        // Record in-memory array history (Unit 1)
        recordHistory(loadedDeck.getName(), quiz.getTotalPointsEarned(), quiz.getPercentage());

        // Save persistent score to scores.txt (Unit 7)
        ScoreManager.saveScore(
                loadedDeck.getName(),
                quiz.getTotalPointsEarned(),
                quiz.getTotalPossiblePoints(),
                quiz.getPercentage(),
                quiz.evaluateGrade(quiz.getPercentage())
        );
    }

    // Unit 7: Adding a new card via CLI and appending it to file using FileWriter & BufferedWriter
    private static void addNewCardToDeck(Scanner scanner) throws IOException {
        System.out.println("\n--- ADD NEW CARD TO DECK FILE ---");
        File[] deckFiles = DeckFileReader.listAvailableDeckFiles(DATA_DIRECTORY);
        if (deckFiles.length == 0) {
            System.out.println("[Error] No deck files found to append to.");
            return;
        }

        System.out.println("Select Target Deck File:");
        for (int i = 0; i < deckFiles.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + deckFiles[i].getName());
        }
        System.out.print("Choose deck (1-" + deckFiles.length + "): ");
        int fileIdx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (fileIdx < 0 || fileIdx >= deckFiles.length) {
            throw new ArrayIndexOutOfBoundsException("Invalid deck selection index.");
        }
        File targetFile = deckFiles[fileIdx];

        System.out.println("\nSelect Card Type:");
        System.out.println("  1. Text Card (Direct Answer)");
        System.out.println("  2. Multiple Choice Card (MCQ)");
        System.out.println("  3. True / False Card");
        System.out.print("Choose type (1-3): ");
        int typeChoice = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter Question: ");
        String question = scanner.nextLine().trim();
        if (question.length() == 0) {
            throw new InvalidInputException("Question text cannot be empty.");
        }

        System.out.print("Enter Topic (e.g. Java Basics): ");
        String topic = scanner.nextLine().trim();
        if (topic.length() == 0) topic = "General";

        System.out.print("Enter Points (e.g. 10): ");
        int points = 10;
        try {
            points = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[Note] Defaulting to 10 points.");
        }

        Card newCard = null;
        if (typeChoice == 1) {
            System.out.print("Enter Expected Answer: ");
            String ans = scanner.nextLine().trim();
            newCard = new TextCard(999, question, ans, topic, points);
        } else if (typeChoice == 2) {
            System.out.print("Enter Option A: ");
            String opA = scanner.nextLine().trim();
            System.out.print("Enter Option B: ");
            String opB = scanner.nextLine().trim();
            System.out.print("Enter Option C: ");
            String opC = scanner.nextLine().trim();
            System.out.print("Enter Option D: ");
            String opD = scanner.nextLine().trim();
            System.out.print("Enter Correct Option Letter (A, B, C, or D): ");
            char correct = scanner.nextLine().trim().toUpperCase().charAt(0);
            newCard = new MultipleChoiceCard(999, question, new String[]{opA, opB, opC, opD}, correct, topic, points);
        } else if (typeChoice == 3) {
            System.out.print("Enter Correct Answer (true or false): ");
            boolean boolAns = Boolean.parseBoolean(scanner.nextLine().trim().toLowerCase());
            newCard = new TrueFalseCard(999, question, boolAns, topic, points);
        } else {
            throw new InvalidInputException("Invalid card type choice: " + typeChoice);
        }

        // Unit 7: Appending to file
        DeckFileReader.appendCardToFile(targetFile.getPath(), newCard);
    }

    // Unit 6: Dedicated method demonstrating checked/unchecked exceptions, throw, throws, multiple catch, and finally
    private static void runExceptionLabDemo() {
        System.out.println("\n--- RUNNING UNIT 6 EXCEPTION HANDLING TEST ---");

        System.out.println("[Test 1] Validating an empty Deck (should throw InvalidDeckException):");
        try {
            Deck emptyDeck = new Deck("Ghost Deck", "A deck with zero cards");
            emptyDeck.validateDeck();
            System.out.println("FAILURE: Exception was not thrown!");
        } catch (InvalidDeckException ide) {
            System.out.println("SUCCESS >> Caught InvalidDeckException: " + ide.getMessage());
        }

        System.out.println("\n[Test 2] Creating a Deck with an invalid null name (should throw InvalidInputException):");
        try {
            Deck invalidDeck = new Deck(null, "Invalid");
            System.out.println("FAILURE: Exception was not thrown!");
        } catch (InvalidInputException iie) {
            System.out.println("SUCCESS >> Caught InvalidInputException: " + iie.getMessage());
        }

        System.out.println("\n[Test 3] Accessing card index 99 on small deck (should throw ArrayIndexOutOfBoundsException):");
        try {
            Deck smallDeck = new Deck("Small", "One card deck");
            smallDeck.addCard(new TextCard(1, "Test?", "Ans", "General", 10));
            smallDeck.getCardAt(99);
        } catch (ArrayIndexOutOfBoundsException aie) {
            System.out.println("SUCCESS >> Caught ArrayIndexOutOfBoundsException: " + aie.getMessage());
        } finally {
            System.out.println("SUCCESS >> finally block executed in Test 3.");
        }
        System.out.println("--- All Unit 6 Exception Tests Passed ---\n");
    }

    // Unit 3 & Unit 6: Inheritance quiz declaring throws InvalidDeckException
    private static void runInheritanceQuiz(Scanner scanner) throws InvalidDeckException {
        Deck polyDeck = new Deck("Inheritance & Polymorphism", "Unit 3 polymorphic cards");

        CardMetadata meta = new CardMetadata("FlashCard Lab Team", "1.0.0");
        System.out.println("[Metadata] " + meta.toString());

        Card textCard = new TextCard(101, "Which keyword prevents method overriding in Java?", "final", "Inheritance", 15);
        
        String[] mcqOptions = new String[]{"Multiple inheritance of classes", "Multiple constructors", "Method overloading", "Abstract methods"};
        Card mcqCard = new MultipleChoiceCard(102, "Which feature is NOT supported directly in Java?", mcqOptions, 'A', "Inheritance", 20);

        Card tfCard = new TrueFalseCard(103, "Can an abstract class declare concrete constructors?", true, "Inheritance", 15);

        System.out.println("\n[Diagnostic: Testing Method Overloading on MCQ Card]");
        System.out.println("Testing checkAnswer(String \"A\"): " + mcqCard.checkAnswer("A"));
        System.out.println("Testing checkAnswer(char 'A')  : " + mcqCard.checkAnswer('A'));

        polyDeck.addCard(textCard);
        polyDeck.addCard(mcqCard);
        polyDeck.addCard(tfCard);

        com.flashquiz.service.Quiz quiz = new com.flashquiz.service.Quiz(polyDeck);
        quiz.runQuiz(scanner);

        recordHistory(polyDeck.getName(), quiz.getTotalPointsEarned(), quiz.getPercentage());
    }

    // Unit 2 & Unit 6: Building Deck object and declaring throws InvalidDeckException
    private static void runOopQuiz(Scanner scanner) throws InvalidDeckException {
        Deck oopDeck = new Deck("Java OOP Core", "Demonstrating classes, objects, and constructors");

        Flashcard c1 = new Flashcard(1, "What is the blueprint from which objects are created?", "class", "OOP");
        Flashcard c2 = new Flashcard("Which keyword refers to the current class instance?", "this");
        c2.setId(2);
        c2.setTopic("OOP");
        Flashcard c3 = new Flashcard(3, "Can a Java class have multiple constructors? (yes/no)", "yes", "OOP");
        Flashcard c3Copy = new Flashcard(c3);
        c3Copy.setId(4);
        c3Copy.setQuestion("Does Java support constructor overloading? (yes/no)");

        oopDeck.addCard(c1);
        oopDeck.addCard(c2);
        oopDeck.addCard(c3Copy);

        com.flashquiz.service.Quiz quiz = new com.flashquiz.service.Quiz(oopDeck);
        quiz.runQuiz(scanner);

        recordHistory(oopDeck.getName(), quiz.getScore() * 10, quiz.getPercentage());
    }

    private static void recordHistory(String deckName, int scaledScore, double percentage) {
        if (historyCount < MAX_HISTORY) {
            scoreHistory[historyCount] = scaledScore;
            deckHistory[historyCount] = deckName;
            percentageHistory[historyCount] = percentage;
            historyCount++;
        } else {
            for (int k = 1; k < MAX_HISTORY; k++) {
                scoreHistory[k - 1] = scoreHistory[k];
                deckHistory[k - 1] = deckHistory[k];
                percentageHistory[k - 1] = percentageHistory[k];
            }
            scoreHistory[MAX_HISTORY - 1] = scaledScore;
            deckHistory[MAX_HISTORY - 1] = deckName;
            percentageHistory[MAX_HISTORY - 1] = percentage;
        }
    }

    // Unit 1: Method demonstrating operators, arrays, loops, and all primitives
    private static void runQuickQuiz(Scanner scanner, byte mode, short maxPoints) {
        System.out.println("\n--- Starting Quick Starter Quiz ---");
        String[] questions = new String[5];
        String[] answers = new String[5];

        questions[0] = "What is the size of an int in Java (in bytes)? (A: 2, B: 4, C: 8)";
        answers[0] = "B";

        questions[1] = "Which keyword is used to create a class instance? (new / make / create)";
        answers[1] = "new";

        questions[2] = "Is Java platform independent through bytecode? (true / false)";
        answers[2] = "true";

        questions[3] = "What is the remainder of 10 % 3? (Enter number)";
        answers[3] = "1";

        questions[4] = "Which data type stores true or false values? (boolean / bit)";
        answers[4] = "boolean";

        int totalQuestions = questions.length;
        int correctAnswers = 0;
        boolean[] questionResults = new boolean[totalQuestions];

        long startTimeMillis = System.currentTimeMillis();

        for (int i = 0; i < totalQuestions; i++) {
            System.out.println("\nCard #" + (i + 1) + ": " + questions[i]);
            System.out.print("Your Answer: ");
            String userAns = scanner.nextLine().trim();

            boolean isMatch = userAns.equalsIgnoreCase(answers[i]);
            if (isMatch) {
                System.out.println(">> Correct!");
                correctAnswers++;
                questionResults[i] = true;
            } else {
                System.out.println(">> Incorrect! Expected: " + answers[i]);
                questionResults[i] = false;
            }
        }

        long endTimeMillis = System.currentTimeMillis();
        long elapsedMillis = endTimeMillis - startTimeMillis;
        float elapsedSeconds = (float) elapsedMillis / 1000.0f;

        double percentage = ((double) correctAnswers / (double) totalQuestions) * 100.0;
        int scaledScore = (correctAnswers * maxPoints) / totalQuestions;

        String performanceTier = (percentage >= 80.0) ? "Excellent" : (percentage >= 50.0 ? "Good" : "Needs Review");

        System.out.println("\n========== QUIZ SUMMARY ==========");
        System.out.println("Questions Answered : " + totalQuestions);
        System.out.println("Correct Count      : " + correctAnswers);
        System.out.println("Percentage         : " + percentage + "%");
        System.out.println("Scaled Score       : " + scaledScore + " / " + maxPoints);
        System.out.println("Time Elapsed       : " + elapsedSeconds + " seconds");
        System.out.println("Performance Tier   : " + performanceTier);
        System.out.println("==================================");

        recordHistory("Quick Starter (Mode " + mode + ")", scaledScore, percentage);
    }

    private static void displayScoreHistory() {
        System.out.println("\n--- SCORE HISTORY (In-Memory Arrays) ---");
        if (historyCount == 0) {
            System.out.println("No quiz attempts recorded yet this session.");
            return;
        }

        System.out.println("Index | Deck Name                   | Score | Percentage");
        System.out.println("------+-----------------------------+-------+-----------");
        for (int i = 0; i < historyCount; i++) {
            System.out.printf("  %-3d | %-27s | %-5d | %6.2f%%\n",
                    (i + 1), deckHistory[i], scoreHistory[i], percentageHistory[i]);
        }
    }

    private static void displayDiagnostics(int sessionQuizCount) {
        System.out.println("\n--- SYSTEM & VARIABLE DIAGNOSTICS ---");
        int flags = 0b0101;
        int mask = 0b0001;
        boolean bit1Set = (flags & mask) != 0;

        int modResult = sessionQuizCount % 2;

        System.out.println("Primitive Types Verified:");
        System.out.println(" - byte    : " + Byte.BYTES + " byte(s)  [MIN: " + Byte.MIN_VALUE + ", MAX: " + Byte.MAX_VALUE + "]");
        System.out.println(" - short   : " + Short.BYTES + " bytes   [MIN: " + Short.MIN_VALUE + ", MAX: " + Short.MAX_VALUE + "]");
        System.out.println(" - int     : " + Integer.BYTES + " bytes   [MIN: " + Integer.MIN_VALUE + ", MAX: " + Integer.MAX_VALUE + "]");
        System.out.println(" - long    : " + Long.BYTES + " bytes   [MIN: " + Long.MIN_VALUE + ", MAX: " + Long.MAX_VALUE + "]");
        System.out.println(" - float   : " + Float.BYTES + " bytes   [MIN: " + Float.MIN_VALUE + ", MAX: " + Float.MAX_VALUE + "]");
        System.out.println(" - double  : " + Double.BYTES + " bytes   [MIN: " + Double.MIN_VALUE + ", MAX: " + Double.MAX_VALUE + "]");
        System.out.println(" - char    : " + Character.BYTES + " bytes   [Range: '\\u0000' to '\\uffff']");
        System.out.println(" - boolean : 1 bit logical (JVM dependent)");
        System.out.println("Operators Verified:");
        System.out.println(" - Bitwise AND (0b0101 & 0b0001): " + (flags & mask) + " (Flag 1 set: " + bit1Set + ")");
        System.out.println(" - Modulo (Session count " + sessionQuizCount + " % 2): " + modResult);
        System.out.println(" - Array lengths: Score array size = " + scoreHistory.length);
    }
}
