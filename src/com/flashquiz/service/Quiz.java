package com.flashquiz.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.flashquiz.model.Card;
import com.flashquiz.model.Deck;
import com.flashquiz.util.StringUtil;
import com.flashquiz.exception.InvalidDeckException;
import com.flashquiz.exception.InvalidInputException;

// Unit 4, 5, 6, 8: Quiz integrating multithreading, timer thread, priority, and synchronized shared state
public class Quiz implements Scorable {

    private Deck deck;
    private int totalQuestions;
    private int timeLimitSeconds; // 0 = untimed, >0 = per-question timer
    private QuizTracker tracker; // Unit 8: Thread-safe shared score counter
    private double percentage;
    private long durationSeconds;
    private int hintsRequested;

    public Quiz(Deck deck) {
        this(deck, 0); // Default untimed
    }

    // Unit 8: Overloaded constructor accepting per-question countdown limit
    public Quiz(Deck deck, int timeLimitSeconds) {
        if (deck == null) {
            throw new InvalidInputException("Quiz constructor requires a non-null Deck object.");
        }
        this.deck = deck;
        this.totalQuestions = deck.getCardCount();
        this.timeLimitSeconds = Math.max(0, timeLimitSeconds);
        this.tracker = new QuizTracker();
        this.percentage = 0.0;
        this.durationSeconds = 0;
        this.hintsRequested = 0;
    }

    // Scanner adapter for backwards compatibility
    public void runQuiz(Scanner scanner) throws InvalidDeckException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        runQuiz(reader);
    }

    // Unit 8: Multithreaded Quiz runner with background QuestionTimer Thread
    public void runQuiz(BufferedReader reader) throws InvalidDeckException {
        deck.validateDeck();
        tracker.reset();

        System.out.println(StringUtil.formatBannerWithDecoration("STARTING QUIZ: " + deck.getName()));
        System.out.println("Total Cards in Deck : " + deck.getCardCount());
        if (timeLimitSeconds > 0) {
            System.out.println("Timer Mode Enabled  : " + timeLimitSeconds + " seconds per card (Question skips on expiry)");
        } else {
            System.out.println("Timer Mode          : Untimed (Take your time)");
        }
        System.out.println("Available Commands  : ':hint' for clue, ':skip' to pass card");

        long startTime = System.currentTimeMillis();

        try {
            for (int i = 0; i < deck.getCardCount(); i++) {
                Card card = deck.getCardAt(i);
                if (card == null) {
                    continue;
                }

                System.out.println();
                card.display();

                String answer = null;
                boolean cardHandled = false;

                while (!cardHandled) {
                    if (timeLimitSeconds > 0) {
                        // Unit 8: Instantiating Runnable for countdown timer
                        QuestionTimer timerRunnable = new QuestionTimer(timeLimitSeconds);
                        // Unit 8: Instantiating Thread with Runnable
                        Thread timerThread = new Thread(timerRunnable, "QuestionTimerThread-" + (i + 1));

                        // Unit 8: Setting thread priority
                        // Explanation: Set to Thread.MIN_PRIORITY (1) so that the background countdown
                        // timer thread yields CPU execution preference to the main user-input thread,
                        // ensuring smooth, non-lagging terminal typing and response capture.
                        timerThread.setPriority(Thread.MIN_PRIORITY);
                        timerThread.start();

                        System.out.print("Your Answer [" + timeLimitSeconds + "s limit]: ");

                        // Polling with sleep to allow timer thread execution while waiting for user input
                        while (!timerRunnable.isExpired()) {
                            try {
                                if (reader.ready()) {
                                    answer = reader.readLine();
                                    timerRunnable.cancel();
                                    timerThread.interrupt(); // Interrupt sleeper
                                    if (answer == null) {
                                        System.out.println("\n[EOF Detected] Stream closed.");
                                        return;
                                    }
                                    break;
                                }
                                Thread.sleep(50); // Small sleep to prevent busy-waiting
                            } catch (InterruptedException ie) {
                                break;
                            } catch (IOException ioe) {
                                answer = "";
                                break;
                            }
                        }

                        // Unit 8: Handling timeout - question counts as skipped
                        if (timerRunnable.isExpired()) {
                            System.out.println("\n[TIME EXPIRED!] Time limit of " + timeLimitSeconds + "s exceeded! Question skipped.");
                            // Unit 8: Shared score counter updated inside synchronized block
                            tracker.recordSkipped(card.getPoints());
                            cardHandled = true;
                            continue;
                        }
                    } else {
                        // Untimed input mode
                        System.out.print("Your Answer: ");
                        try {
                            answer = reader.readLine();
                            if (answer == null) {
                                System.out.println("\n[EOF Detected] Stream closed.");
                                return;
                            }
                        } catch (IOException e) {
                            answer = "";
                        }
                    }

                    if (answer == null) {
                        answer = "";
                    }

                    String clean = answer.trim();

                    // Check commands
                    if (clean.equalsIgnoreCase(":hint") || clean.equalsIgnoreCase("hint")) {
                        System.out.println(">> " + card.getHint());
                        hintsRequested++;
                        // Loop continues so user can submit an actual answer
                    } else if (clean.equalsIgnoreCase(":skip") || clean.equalsIgnoreCase("skip")) {
                        System.out.println(">> Skipped by user.");
                        // Unit 8: Shared state update via synchronized block
                        tracker.recordSkipped(card.getPoints());
                        cardHandled = true;
                    } else {
                        // Grade user answer
                        boolean correct = card.checkAnswer(clean);
                        // Unit 8: Shared score counter updated inside synchronized block
                        tracker.recordAnswer(correct, card.getPoints());

                        if (correct) {
                            System.out.println(">> Correct! (+" + card.getPoints() + " points)");
                        } else {
                            System.out.println(">> Incorrect! Correct Answer: " + card.getAnswer());
                        }
                        cardHandled = true;
                    }
                }
            }
        } finally {
            long endTime = System.currentTimeMillis();
            this.durationSeconds = (endTime - startTime) / 1000;
        }

        this.percentage = calculatePercentage(tracker.getTotalPointsEarned(), tracker.getTotalPossiblePoints());
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

    // Unit 5 & 8: Result report utilizing synchronized tracker values
    private void printResults() {
        StringBuffer report = new StringBuffer();
        report.append("\n============================================\n");
        report.append("             QUIZ PERFORMANCE REPORT        \n");
        report.append("============================================\n");
        report.append("Deck Name          : ").append(deck.getName()).append("\n");
        report.append("Total Cards        : ").append(totalQuestions).append("\n");
        report.append("Cards Correct      : ").append(tracker.getCorrectCount()).append(" / ").append(totalQuestions).append("\n");
        report.append("Cards Skipped      : ").append(tracker.getSkippedCount()).append("\n");
        report.append("Cards Incorrect    : ").append(tracker.getIncorrectCount()).append("\n");
        report.append("Points Scored      : ").append(tracker.getTotalPointsEarned()).append(" / ")
                .append(tracker.getTotalPossiblePoints()).append(" pts\n");
        report.append("Accuracy Score     : ").append(String.format("%.2f", percentage)).append("%\n");
        report.append("Academic Rating    : ").append(evaluateGrade(percentage)).append("\n");
        report.append("Pass Status        : ").append(isPassing(percentage) ? "PASSED" : "FAILED").append("\n");
        report.append("Timer Mode         : ").append(timeLimitSeconds > 0 ? (timeLimitSeconds + "s limit") : "Untimed").append("\n");
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
        return tracker.getCorrectCount();
    }

    public int getTotalPointsEarned() {
        return tracker.getTotalPointsEarned();
    }

    public int getTotalPossiblePoints() {
        return tracker.getTotalPossiblePoints();
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

    public QuizTracker getTracker() {
        return tracker;
    }
}
