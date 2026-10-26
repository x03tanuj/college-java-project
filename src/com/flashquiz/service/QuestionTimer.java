package com.flashquiz.service;

// Unit 8: Threads - implementing Runnable interface for background countdown timer
public class QuestionTimer implements Runnable {

    private final int secondsLimit;
    private volatile boolean cancelled;
    private volatile boolean expired;
    private int remainingSeconds;

    public QuestionTimer(int secondsLimit) {
        this.secondsLimit = secondsLimit;
        this.cancelled = false;
        this.expired = false;
        this.remainingSeconds = secondsLimit;
    }

    // Unit 8: Runnable run() method executed in a separate Thread
    @Override
    public void run() {
        try {
            while (remainingSeconds > 0 && !cancelled) {
                Thread.sleep(1000); // Sleep for 1 second intervals
                remainingSeconds--;
            }
            if (!cancelled && remainingSeconds <= 0) {
                expired = true;
            }
        } catch (InterruptedException e) {
            // Thread was interrupted when user responded before timeout
            cancelled = true;
        }
    }

    // Signals timer to stop countdown
    public void cancel() {
        this.cancelled = true;
    }

    public boolean isExpired() {
        return expired;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public int getSecondsLimit() {
        return secondsLimit;
    }
}
