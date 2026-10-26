package com.flashquiz.service;

import java.util.Scanner;
import com.flashquiz.model.Card;
import com.flashquiz.model.Deck;
import com.flashquiz.util.StringUtil;
import com.flashquiz.exception.InvalidDeckException;
import com.flashquiz.exception.InvalidInputException;

// Unit 4, 5, 6: Quiz demonstrating try/catch, throw, throws, finally, and custom exceptions
public class Quiz implements Scorable {

    private Deck deck;
    private int totalQuestions;
    private int score;
    private int totalPointsEarned;
    private int totalPossiblePoints;
    private double percentage;
    private long durationSeconds;
    private int hintsRequested;

    public Quiz(Deck deck) {
        if (deck == null) {
            // Unit 6: throw unchecked exception
            throw new InvalidInputException("Quiz constructor requires a non-null Deck object.");
        }
        this.deck = deck;
        this.totalQuestions = deck.getCardCount();
        this.score = 0;
        this.totalPointsEarned = 0;
        this.totalPossiblePoints = 0;
        this.percentage = 0.0;
        this.durationSeconds = 0;
        this.hintsRequested = 0;
    }

    // Unit 6: Method declaring checked exception using 'throws'
    public void runQuiz(Scanner scanner) throws InvalidDeckException {
        // Unit 6: Validating deck state; may throw checked InvalidDeckException
        deck.validateDeck();

        System.out.println(StringUtil.formatBannerWithDecoration("STARTING QUIZ: " + deck.getName()));
        System.out.println("Total Cards in Deck: " + deck.getCardCount());
        System.out.println("Tip: Enter ':hint' at any prompt to reveal letter hints!");

        int correctCount = 0;
        int pointsEarned = 0;
        int possiblePoints = 0;
        long startTime = System.currentTimeMillis();

        // Unit 6: try-finally block to ensure duration calculation even if interrupted
        try {
            for (int i = 0; i < deck.getCardCount(); i++) {
                Card card = deck.getCardAt(i);
                if (card == null) {
                    continue;
                }

                possiblePoints += card.getPoints();

                System.out.println();
                card.display();

                String answer = "";
                boolean answered = false;

                while (!answered) {
                    System.out.print("Your Answer (or ':hint'): ");
                    try {
                        answer = scanner.nextLine();
                        if (answer == null) {
                            throw new InvalidInputException("Input stream was closed or returned null.");
                        }

                        if (answer.trim().equalsIgnoreCase(":hint") || answer.trim().equalsIgnoreCase("hint")) {
                            System.out.println(">> " + card.getHint());
                            hintsRequested++;
                        } else {
                            answered = true;
                        }
                    } catch (InvalidInputException e) {
                        System.out.println("[Warning] " + e.getMessage() + ". Please re-enter answer.");
                    }
                }

                boolean correct = card.checkAnswer(answer);
                if (correct) {
                    System.out.println(">> Correct! (+" + card.getPoints() + " points)");
                    correctCount++;
                    pointsEarned += card.getPoints();
                } else {
                    System.out.println(">> Incorrect! Correct Answer: " + card.getAnswer());
                }
            }
        } finally {
            // Unit 6: finally block guarantees execution
            long endTime = System.currentTimeMillis();
            this.durationSeconds = (endTime - startTime) / 1000;
        }

        this.score = correctCount;
        this.totalPointsEarned = pointsEarned;
        this.totalPossiblePoints = possiblePoints;
        this.totalQuestions = deck.getCardCount();
        this.percentage = calculatePercentage(pointsEarned, possiblePoints);

        printResults();
    }

    @Override
    public double calculatePercentage(int pointsEarned, int totalPossible) {
        if (totalPossible <= 0) {
            return 0.0;
        }
        return ((double) pointsEarned / (double) totalPossible) * 100.0;
    }

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

    @Override
    public boolean isPassing(double pct) {
        return pct >= Scorable.PASSING_THRESHOLD;
    }

    private void printResults() {
        StringBuffer report = new StringBuffer();
        report.append("\n============================================\n");
        report.append("             QUIZ PERFORMANCE REPORT        \n");
        report.append("============================================\n");
        report.append("Deck Name          : ").append(deck.getName()).append("\n");
        report.append("Total Cards        : ").append(totalQuestions).append("\n");
        report.append("Cards Correct      : ").append(score).append(" / ").append(totalQuestions).append("\n");
        report.append("Points Scored      : ").append(totalPointsEarned).append(" / ").append(totalPossiblePoints).append(" pts\n");
        report.append("Accuracy Score     : ").append(String.format("%.2f", percentage)).append("%\n");
        report.append("Academic Rating    : ").append(evaluateGrade(percentage)).append("\n");
        report.append("Pass Status        : ").append(isPassing(percentage) ? "PASSED" : "FAILED").append("\n");
        report.append("Hints Consulted    : ").append(hintsRequested).append("\n");
        report.append("Completion Time    : ").append(durationSeconds).append(" second(s)\n");

        StringBuffer borderDecor = new StringBuffer("~~~***>>> FlashQuiz Lab <<<***~~~");
        String normalBorder = borderDecor.toString();
        String reversedBorder = borderDecor.reverse().toString();
        report.append("--------------------------------------------\n");
        report.append("Header Style: ").append(normalBorder).append("\n");
        report.append("Mirror Style: ").append(reversedBorder).append("\n");
        report.append("============================================\n");

        String badge = "[STATUS: " + (isPassing(percentage) ? "QUALIFIED" : "RETAKE") + "] ";
        int insertPosition = report.indexOf("Deck Name");
        if (insertPosition != -1) {
            report.insert(insertPosition, badge);
        }

        System.out.println(report.toString());
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

    public int getHintsRequested() {
        return hintsRequested;
    }
}
