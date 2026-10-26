package com.flashquiz.service;

// Unit 8: Threads - synchronized block protecting shared score counters across threads
public class QuizTracker {

    // Shared mutable state
    private int correctCount;
    private int skippedCount;
    private int incorrectCount;
    private int totalPointsEarned;
    private int totalPossiblePoints;

    // Explicit lock object for synchronized blocks (Unit 8)
    private final Object lock = new Object();

    public QuizTracker() {
        this.correctCount = 0;
        this.skippedCount = 0;
        this.incorrectCount = 0;
        this.totalPointsEarned = 0;
        this.totalPossiblePoints = 0;
    }

    // Unit 8: Synchronized block for thread-safe answer recording
    public void recordAnswer(boolean isCorrect, int points) {
        synchronized (lock) {
            totalPossiblePoints += points;
            if (isCorrect) {
                correctCount++;
                totalPointsEarned += points;
            } else {
                incorrectCount++;
            }
        }
    }

    // Unit 8: Synchronized block for recording a skipped/timed-out question
    public void recordSkipped(int points) {
        synchronized (lock) {
            totalPossiblePoints += points;
            skippedCount++;
        }
    }

    public int getCorrectCount() {
        synchronized (lock) {
            return correctCount;
        }
    }

    public int getSkippedCount() {
        synchronized (lock) {
            return skippedCount;
        }
    }

    public int getIncorrectCount() {
        synchronized (lock) {
            return incorrectCount;
        }
    }

    public int getTotalPointsEarned() {
        synchronized (lock) {
            return totalPointsEarned;
        }
    }

    public int getTotalPossiblePoints() {
        synchronized (lock) {
            return totalPossiblePoints;
        }
    }

    public void reset() {
        synchronized (lock) {
            correctCount = 0;
            skippedCount = 0;
            incorrectCount = 0;
            totalPointsEarned = 0;
            totalPossiblePoints = 0;
        }
    }
}
