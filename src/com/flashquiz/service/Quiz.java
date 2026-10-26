package com.flashquiz.service;

import java.util.Scanner;
import com.flashquiz.model.Card;
import com.flashquiz.model.Deck;

// Unit 3: Dynamic Method Dispatch (Polymorphism) and overriding execution
public class Quiz {

    private Deck deck;
    private int totalQuestions;
    private int score;
    private int totalPointsEarned;
    private int totalPossiblePoints;
    private double percentage;
    private long durationSeconds;

    public Quiz(Deck deck) {
        this.deck = deck;
        this.totalQuestions = (deck != null) ? deck.getCardCount() : 0;
        this.score = 0;
        this.totalPointsEarned = 0;
        this.totalPossiblePoints = 0;
        this.percentage = 0.0;
        this.durationSeconds = 0;
    }

    // Unit 3: Dynamic method dispatch in action
    public void runQuiz(Scanner scanner) {
        if (deck == null || deck.getCardCount() == 0) {
            System.out.println("[Error] Cannot start quiz: The deck is empty or not loaded.");
            return;
        }

        System.out.println("\n==========================================");
        System.out.println("  STARTING QUIZ: " + deck.getName());
        System.out.println("  Total Cards: " + deck.getCardCount());
        System.out.println("==========================================");

        int correctCount = 0;
        int pointsEarned = 0;
        int possiblePoints = 0;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < deck.getCardCount(); i++) {
            // Unit 3: Base class reference (Card) pointing to subclass instance
            Card card = deck.getCardAt(i);
            if (card == null) {
                continue;
            }

            possiblePoints += card.getPoints(); // Unit 3: Final method call

            System.out.println();
            // Unit 3: Polymorphic method call - invokes specific subclass display()
            card.display();

            System.out.print("Your Answer: ");
            String answer = scanner.nextLine();

            // Unit 3: Polymorphic method call - invokes specific subclass checkAnswer()
            boolean correct = card.checkAnswer(answer);
            if (correct) {
                System.out.println(">> Correct! (+" + card.getPoints() + " points)");
                correctCount++;
                pointsEarned += card.getPoints();
            } else {
                System.out.println(">> Incorrect! Correct Answer: " + card.getAnswer());
            }
        }

        long endTime = System.currentTimeMillis();
        this.durationSeconds = (endTime - startTime) / 1000;
        this.score = correctCount;
        this.totalPointsEarned = pointsEarned;
        this.totalPossiblePoints = possiblePoints;
        this.totalQuestions = deck.getCardCount();
        this.percentage = (possiblePoints > 0) ? ((double) pointsEarned / (double) possiblePoints) * 100.0 : 0.0;

        printResults();
    }

    private void printResults() {
        System.out.println("\n=========== QUIZ RESULTS ===========");
        System.out.println("Deck Name    : " + deck.getName());
        System.out.println("Questions    : " + score + " / " + totalQuestions + " correct");
        System.out.println("Points       : " + totalPointsEarned + " / " + totalPossiblePoints + " pts");
        System.out.printf("Percentage   : %.2f%%\n", percentage);
        System.out.println("Time Taken   : " + durationSeconds + " second(s)");
        System.out.println("Rating       : " + (percentage >= 80.0 ? "Mastered" : (percentage >= 50.0 ? "Competent" : "Needs Practice")));
        System.out.println("=====================================");
    }

    public Deck getDeck() {
        return deck;
    }

    public int getScore() {
        return score;
    }

    public int getTotalPointsEarned() {
        return totalPointsEarned;
    }

    public int getTotalPossiblePoints() {
        return totalPossiblePoints;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public double getPercentage() {
        return percentage;
    }
}
