package com.flashquiz.service;

import java.util.Scanner;
import com.flashquiz.model.Card;
import com.flashquiz.model.Deck;

// Unit 4: Packages and Interfaces - Quiz implements Scorable interface
public class Quiz implements Scorable {

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
            Card card = deck.getCardAt(i);
            if (card == null) {
                continue;
            }

            possiblePoints += card.getPoints();

            System.out.println();
            card.display();

            System.out.print("Your Answer: ");
            String answer = scanner.nextLine();

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
        // Unit 4: Using implemented interface method
        this.percentage = calculatePercentage(pointsEarned, possiblePoints);

        printResults();
    }

    // Unit 4: Implementing Scorable.calculatePercentage
    @Override
    public double calculatePercentage(int pointsEarned, int totalPossible) {
        if (totalPossible <= 0) {
            return 0.0;
        }
        return ((double) pointsEarned / (double) totalPossible) * 100.0;
    }

    // Unit 4: Implementing Scorable.evaluateGrade using interface constants
    @Override
    public String evaluateGrade(double pct) {
        if (pct >= Scorable.DISTINCTION_THRESHOLD) {
            return "Grade A (Distinction)";
        } else if (pct >= 70.0) {
            return "Grade B (Good)";
        } else if (pct >= Scorable.PASSING_THRESHOLD) {
            return "Grade C (Pass)";
        } else {
            return "Grade F (Fail / Needs Practice)";
        }
    }

    // Unit 4: Implementing Scorable.isPassing using interface constants
    @Override
    public boolean isPassing(double pct) {
        return pct >= Scorable.PASSING_THRESHOLD;
    }

    private void printResults() {
        System.out.println("\n=========== QUIZ RESULTS ===========");
        System.out.println("Deck Name    : " + deck.getName());
        System.out.println("Questions    : " + score + " / " + totalQuestions + " correct");
        System.out.println("Points       : " + totalPointsEarned + " / " + totalPossiblePoints + " pts");
        System.out.printf("Percentage   : %.2f%%\n", percentage);
        System.out.println("Evaluation   : " + evaluateGrade(percentage));
        System.out.println("Passed       : " + (isPassing(percentage) ? "YES" : "NO"));
        System.out.println("Time Taken   : " + durationSeconds + " second(s)");
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
