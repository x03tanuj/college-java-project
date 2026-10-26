package com.flashquiz;

import java.util.Scanner;

// Unit 1: Bytecode, data types, variables, arrays, operators, decision and control statements
public class Main {

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
        System.out.println("  Demonstrating Java Unit 1 Core Concepts ");
        System.out.println("==========================================");

        // Unit 1: while control statement
        while (running) {
            System.out.println("\n---------------- MAIN MENU ----------------");
            System.out.println("1. Start Quick Starter Quiz");
            System.out.println("2. View Score History (Array Storage)");
            System.out.println("3. System & Variable Diagnostics");
            System.out.println("4. Exit");
            System.out.print("Select an option (1-4): ");

            String input = scanner.nextLine();
            // Unit 1: if-else statement for input validation
            if (input == null || input.trim().length() == 0) {
                System.out.println("[Error] Empty input! Please enter a number between 1 and 4.");
                continue;
            }

            char menuChoice = input.trim().charAt(0); // Unit 1: char primitive type

            // Unit 1: switch control statement
            switch (menuChoice) {
                case '1':
                    runQuickQuiz(scanner, systemMode, totalPossiblePoints);
                    sessionQuizCount++;
                    break;
                case '2':
                    displayScoreHistory();
                    break;
                case '3':
                    displayDiagnostics(sessionQuizCount);
                    break;
                case '4':
                    System.out.println("Thank you for using Flash Card Quiz! Goodbye.");
                    running = false;
                    break;
                default:
                    System.out.println("[Error] Invalid choice '" + menuChoice + "'. Enter 1, 2, 3, or 4.");
                    break;
            }
        }
        scanner.close();
    }

    // Unit 1: Method demonstrating operators, arrays, loops, and all primitives
    private static void runQuickQuiz(Scanner scanner, byte mode, short maxPoints) {
        System.out.println("\n--- Starting Quick Starter Quiz ---");
        // Unit 1: Array creation and initialization
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
        // Unit 1: boolean array tracking correctness per question
        boolean[] questionResults = new boolean[totalQuestions];

        // Unit 1: long primitive type for timestamp in milliseconds
        long startTimeMillis = System.currentTimeMillis();

        // Unit 1: for loop statement
        for (int i = 0; i < totalQuestions; i++) {
            System.out.println("\nCard #" + (i + 1) + ": " + questions[i]);
            System.out.print("Your Answer: ");
            String userAns = scanner.nextLine().trim();

            // Unit 1: Relational and logical operators
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
        long elapsedMillis = endTimeMillis - startTimeMillis; // Arithmetic subtraction
        float elapsedSeconds = (float) elapsedMillis / 1000.0f; // Unit 1: float type and casting

        // Unit 1: double type for floating-point calculation
        double percentage = ((double) correctAnswers / (double) totalQuestions) * 100.0;
        int scaledScore = (correctAnswers * maxPoints) / totalQuestions; // Arithmetic multiplication & division

        // Unit 1: Ternary operator (? :)
        String performanceTier = (percentage >= 80.0) ? "Excellent" : (percentage >= 50.0 ? "Good" : "Needs Review");

        System.out.println("\n========== QUIZ SUMMARY ==========");
        System.out.println("Questions Answered : " + totalQuestions);
        System.out.println("Correct Count      : " + correctAnswers);
        System.out.println("Percentage         : " + percentage + "%");
        System.out.println("Scaled Score       : " + scaledScore + " / " + maxPoints);
        System.out.println("Time Elapsed       : " + elapsedSeconds + " seconds");
        System.out.println("Performance Tier   : " + performanceTier);
        System.out.println("==================================");

        // Store result in history array if capacity permits
        if (historyCount < MAX_HISTORY) {
            scoreHistory[historyCount] = scaledScore;
            deckHistory[historyCount] = "Quick Starter (Mode " + mode + ")";
            percentageHistory[historyCount] = percentage;
            historyCount++;
        } else {
            // Shift array elements if full (Unit 1: array manipulation)
            for (int k = 1; k < MAX_HISTORY; k++) {
                scoreHistory[k - 1] = scoreHistory[k];
                deckHistory[k - 1] = deckHistory[k];
                percentageHistory[k - 1] = percentageHistory[k];
            }
            scoreHistory[MAX_HISTORY - 1] = scaledScore;
            deckHistory[MAX_HISTORY - 1] = "Quick Starter (Mode " + mode + ")";
            percentageHistory[MAX_HISTORY - 1] = percentage;
        }
    }

    // Unit 1: Arrays iteration and output formatting
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

    // Unit 1: Demonstration of operators (arithmetic, bitwise, logical) and primitive sizes
    private static void displayDiagnostics(int sessionQuizCount) {
        System.out.println("\n--- SYSTEM & VARIABLE DIAGNOSTICS ---");
        // Bitwise operators (Unit 1: & , |, ^, ~)
        int flags = 0b0101; // binary 5
        int mask = 0b0001;  // binary 1
        boolean bit1Set = (flags & mask) != 0;

        // Modulo operator demonstration
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
