package com.flashquiz.service;

import java.util.Scanner;
import com.flashquiz.model.Card;
import com.flashquiz.model.Deck;
import com.flashquiz.util.StringUtil;

// Unit 4 & Unit 5: Scorable interface implementation with StringBuffer reporting and String hint interactions
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
        this.deck = deck;
        this.totalQuestions = (deck != null) ? deck.getCardCount() : 0;
        this.score = 0;
        this.totalPointsEarned = 0;
        this.totalPossiblePoints = 0;
        this.percentage = 0.0;
        this.durationSeconds = 0;
        this.hintsRequested = 0;
    }

    public void runQuiz(Scanner scanner) {
        if (deck == null || deck.getCardCount() == 0) {
            System.out.println("[Error] Cannot start quiz: The deck is empty or not loaded.");
            return;
        }

        // Unit 5: StringBuffer decorated banner via StringUtil
        System.out.println(StringUtil.formatBannerWithDecoration("STARTING QUIZ: " + deck.getName()));
        System.out.println("Total Cards in Deck: " + deck.getCardCount());
        System.out.println("Tip: Enter ':hint' at any prompt to reveal letter hints!");

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

            String answer = "";
            boolean answered = false;

            // Unit 5: Hint feature loop using String.equalsIgnoreCase and char extraction
            while (!answered) {
                System.out.print("Your Answer (or ':hint'): ");
                answer = scanner.nextLine();

                if (answer != null && (answer.trim().equalsIgnoreCase(":hint") || answer.trim().equalsIgnoreCase("hint"))) {
                    // Reveal hint extracted via character extraction
                    System.out.println(">> " + card.getHint());
                    hintsRequested++;
                } else {
                    answered = true;
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

        long endTime = System.currentTimeMillis();
        this.durationSeconds = (endTime - startTime) / 1000;
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

    // Unit 5: Building comprehensive result report using StringBuffer (append, insert, reverse)
    private void printResults() {
        StringBuffer report = new StringBuffer(); // Unit 5: StringBuffer constructor

        // Unit 5: StringBuffer append()
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

        // Unit 5: StringBuffer reverse() to generate symmetrical decorative divider
        StringBuffer borderDecor = new StringBuffer("~~~***>>> FlashQuiz Lab <<<***~~~");
        String normalBorder = borderDecor.toString();
        String reversedBorder = borderDecor.reverse().toString(); // Unit 5: reverse()
        report.append("--------------------------------------------\n");
        report.append("Header Style: ").append(normalBorder).append("\n");
        report.append("Mirror Style: ").append(reversedBorder).append("\n");
        report.append("============================================\n");

        // Unit 5: StringBuffer insert() - insert performance badge at top of report body
        String badge = "[STATUS: " + (isPassing(percentage) ? "QUALIFIED" : "RETAKE") + "] ";
        int insertPosition = report.indexOf("Deck Name");
        if (insertPosition != -1) {
            report.insert(insertPosition, badge); // Unit 5: insert()
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
