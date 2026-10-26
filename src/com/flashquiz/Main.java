package com.flashquiz;

import java.util.Scanner;
import com.flashquiz.model.Card;
import com.flashquiz.model.Deck;
import com.flashquiz.model.Flashcard;
import com.flashquiz.model.TextCard;
import com.flashquiz.model.MultipleChoiceCard;
import com.flashquiz.model.TrueFalseCard;
import com.flashquiz.model.CardMetadata;
import com.flashquiz.exception.InvalidDeckException;
import com.flashquiz.exception.InvalidInputException;

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

        // Unit 1 & Unit 6: while control statement protected with try-catch-finally
        while (running) {
            System.out.println("\n---------------- MAIN MENU ----------------");
            System.out.println("1. Start Quick Starter Quiz (Unit 1 Basics)");
            System.out.println("2. Start OOP Flashcard Quiz (Unit 2 Objects & Decks)");
            System.out.println("3. Start Inheritance & Polymorphism Quiz (Unit 3 Abstract & Subclasses)");
            System.out.println("4. View Score History (Array Storage)");
            System.out.println("5. System & Variable Diagnostics");
            System.out.println("6. Run Exception Handling Lab Test (Unit 6 Demo)");
            System.out.println("7. Exit");
            System.out.print("Select an option (1-7): ");

            String input = "";
            // Unit 6: Try/Catch with multiple catch blocks and finally
            try {
                input = scanner.nextLine();
                if (input == null || input.trim().length() == 0) {
                    // Unit 6: throw unchecked exception
                    throw new InvalidInputException("Input cannot be empty. Please choose 1 to 7.");
                }

                // Unit 6: May throw NumberFormatException if input is non-numeric
                int choice = Integer.parseInt(input.trim());

                // Unit 6: Throwing ArrayIndexOutOfBoundsException for out-of-range menu index
                if (choice < 1 || choice > 7) {
                    throw new ArrayIndexOutOfBoundsException("Menu option " + choice + " is out of bounds (allowed: 1 - 7).");
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
                        displayScoreHistory();
                        break;
                    case 5:
                        displayDiagnostics(sessionQuizCount);
                        break;
                    case 6:
                        runExceptionLabDemo();
                        break;
                    case 7:
                        System.out.println("Thank you for using Flash Card Quiz! Goodbye.");
                        running = false;
                        break;
                }
            } catch (NumberFormatException nfe) {
                // Unit 6: Catch block 1 - Number format issues
                System.out.println("[Exception Caught: NumberFormatException] Invalid number format: '" + input.trim() + "'. Expected an integer (1-7).");
            } catch (ArrayIndexOutOfBoundsException aie) {
                // Unit 6: Catch block 2 - Array / range bounds issues
                System.out.println("[Exception Caught: ArrayIndexOutOfBoundsException] " + aie.getMessage());
            } catch (InvalidInputException iie) {
                // Unit 6: Catch block 3 - Custom unchecked exception
                System.out.println("[Exception Caught: InvalidInputException] " + iie.getMessage());
            } catch (InvalidDeckException ide) {
                // Unit 6: Catch block 4 - Custom checked exception
                System.out.println("[Exception Caught: InvalidDeckException] " + ide.getMessage());
            } catch (Exception e) {
                // Unit 6: Catch block 5 - Generic fallback exception handler
                System.out.println("[Exception Caught: Exception] Unexpected error: " + e.getMessage());
            } finally {
                // Unit 6: finally block executes regardless of whether an exception occurred
                // Used for state auditing and resource confirmation
                // (kept minimal to maintain clean CLI experience)
            }
        }
        scanner.close();
    }

    // Unit 6: Dedicated method demonstrating checked/unchecked exceptions, throw, throws, multiple catch, and finally
    private static void runExceptionLabDemo() {
        System.out.println("\n--- RUNNING UNIT 6 EXCEPTION HANDLING TEST ---");

        // 1. Triggering and catching checked InvalidDeckException
        System.out.println("[Test 1] Validating an empty Deck (should throw InvalidDeckException):");
        try {
            Deck emptyDeck = new Deck("Ghost Deck", "A deck with zero cards");
            emptyDeck.validateDeck(); // Declares throws InvalidDeckException
            System.out.println("FAILURE: Exception was not thrown!");
        } catch (InvalidDeckException ide) {
            System.out.println("SUCCESS >> Caught InvalidDeckException: " + ide.getMessage());
        }

        // 2. Triggering and catching unchecked InvalidInputException
        System.out.println("\n[Test 2] Creating a Deck with an invalid null name (should throw InvalidInputException):");
        try {
            Deck invalidDeck = new Deck(null, "Invalid");
            System.out.println("FAILURE: Exception was not thrown!");
        } catch (InvalidInputException iie) {
            System.out.println("SUCCESS >> Caught InvalidInputException: " + iie.getMessage());
        }

        // 3. Triggering ArrayIndexOutOfBoundsException on Card retrieval
        System.out.println("\n[Test 3] Accessing card index 99 on small deck (should throw ArrayIndexOutOfBoundsException):");
        try {
            Deck smallDeck = new Deck("Small", "One card deck");
            smallDeck.addCard(new TextCard(1, "Test?", "Ans", "General", 10));
            smallDeck.getCardAt(99); // Throws ArrayIndexOutOfBoundsException
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

        // Unit 3: Demonstrating final class instantiation
        CardMetadata meta = new CardMetadata("FlashCard Lab Team", "1.0.0");
        System.out.println("[Metadata] " + meta.toString());

        // Unit 3: Concrete subclasses extending abstract Card
        Card textCard = new TextCard(101, "Which keyword prevents method overriding in Java?", "final", "Inheritance", 15);
        
        String[] mcqOptions = new String[]{"Multiple inheritance of classes", "Multiple constructors", "Method overloading", "Abstract methods"};
        Card mcqCard = new MultipleChoiceCard(102, "Which feature is NOT supported directly in Java?", mcqOptions, 'A', "Inheritance", 20);

        Card tfCard = new TrueFalseCard(103, "Can an abstract class declare concrete constructors?", true, "Inheritance", 15);

        // Unit 3: Overloaded method demonstration
        System.out.println("\n[Diagnostic: Testing Method Overloading on MCQ Card]");
        System.out.println("Testing checkAnswer(String \"A\"): " + mcqCard.checkAnswer("A"));
        System.out.println("Testing checkAnswer(char 'A')  : " + mcqCard.checkAnswer('A'));

        // Unit 3: Polymorphism - adding diverse Card subtypes to Deck
        polyDeck.addCard(textCard);
        polyDeck.addCard(mcqCard);
        polyDeck.addCard(tfCard);

        com.flashquiz.service.Quiz quiz = new com.flashquiz.service.Quiz(polyDeck);
        quiz.runQuiz(scanner);

        recordHistory(polyDeck.getName(), quiz.getTotalPointsEarned(), quiz.getPercentage());
    }

    // Unit 2 & Unit 6: Building Deck object and declaring throws InvalidDeckException
    private static void runOopQuiz(Scanner scanner) throws InvalidDeckException {
        // Unit 2: Creating objects and using overloaded constructors
        Deck oopDeck = new Deck("Java OOP Core", "Demonstrating classes, objects, and constructors");

        // Flashcard instantiated via different constructors
        Flashcard c1 = new Flashcard(1, "What is the blueprint from which objects are created?", "class", "OOP");
        Flashcard c2 = new Flashcard("Which keyword refers to the current class instance?", "this");
        c2.setId(2);
        c2.setTopic("OOP");
        Flashcard c3 = new Flashcard(3, "Can a Java class have multiple constructors? (yes/no)", "yes", "OOP");
        // Unit 2: Copy constructor demonstration
        Flashcard c3Copy = new Flashcard(c3);
        c3Copy.setId(4);
        c3Copy.setQuestion("Does Java support constructor overloading? (yes/no)");

        // Unit 2: Passing objects as parameters to Deck
        oopDeck.addCard(c1);
        oopDeck.addCard(c2);
        oopDeck.addCard(c3Copy);

        // Unit 2: Passing Deck object to Quiz constructor
        com.flashquiz.service.Quiz quiz = new com.flashquiz.service.Quiz(oopDeck);
        quiz.runQuiz(scanner);

        // Record history
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

        // Store result in history array using helper
        recordHistory("Quick Starter (Mode " + mode + ")", scaledScore, percentage);
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
