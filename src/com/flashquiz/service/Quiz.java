package com.flashquiz.service;

import java.util.Scanner;
import com.flashquiz.model.Deck;
import com.flashquiz.model.Flashcard;

// Unit 2: Passing and returning objects, object collaboration, constructors
public class Quiz {

    // Object composition: Quiz has-a Deck
    private Deck deck;
    private int totalQuestions;
    private int score;
    private double percentage;
    private long durationSeconds;

    // Unit 2: Constructor taking an object (Deck) as a parameter
    public Quiz(Deck deck) {
        this.deck = deck;
        this.totalQuestions = (deck != null) ? deck.getCardCount() : 0;
        this.score = 0;
        this.percentage = 0.0;
        this.durationSeconds = 0;
    }

    // Unit 2: Method interacting with composed objects
    public void runQuiz(Scanner scanner) {
        if (deck == null || deck.getCardCount() == 0) {
            System.out.println("[Error] Cannot start quiz: The deck is empty or not loaded.");
            return;
        }

        System.out.println("\n==========================================");
        System.out.println("  STARTING QUIZ: " + deck.getName());
        System.out.println("  Total Flashcards: " + deck.getCardCount());
        System.out.println("==========================================");

        int correctCount = 0;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < deck.getCardCount(); i++) {
            // Unit 2: Receiving an object returned by Deck.getCardAt()
            Flashcard card = deck.getCardAt(i);
            if (card == null) {
                continue;
            }

            System.out.println("\n[Card " + (i + 1) + " of " + deck.getCardCount() + "] " + card.getQuestion());
            System.out.print("Your Answer: ");
            String answer = scanner.nextLine();

            // Calling method on card object
            if (card.checkAnswer(answer)) {
                System.out.println(">> Correct!");
                correctCount++;
            } else {
                System.out.println(">> Incorrect! Correct Answer: " + card.getAnswer());
            }
        }

        long endTime = System.currentTimeMillis();
        this.durationSeconds = (endTime - startTime) / 1000;
        this.score = correctCount;
        this.totalQuestions = deck.getCardCount();
        this.percentage = ((double) correctCount / (double) totalQuestions) * 100.0;

        printResults();
    }

    private void printResults() {
        System.out.println("\n=========== QUIZ RESULTS ===========");
        System.out.println("Deck Name    : " + deck.getName());
        System.out.println("Score        : " + score + " / " + totalQuestions);
        System.out.printf("Percentage   : %.2f%%\n", percentage);
        System.out.println("Time Taken   : " + durationSeconds + " second(s)");
        System.out.println("Rating       : " + (percentage >= 80.0 ? "Mastered" : (percentage >= 50.0 ? "Competent" : "Needs Practice")));
        System.out.println("=====================================");
    }

    // Unit 2: Returning the composed Deck object
    public Deck getDeck() {
        return deck;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public double getPercentage() {
        return percentage;
    }
}
