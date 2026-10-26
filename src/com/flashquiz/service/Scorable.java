package com.flashquiz.service;

// Unit 4: Packages, interfaces, interface constants, and contract specification
public interface Scorable {

    // Unit 4: Interface constants (implicitly public static final)
    double PASSING_THRESHOLD = 50.0;
    double DISTINCTION_THRESHOLD = 80.0;
    double PERFECT_SCORE = 100.0;

    // Unit 4: Abstract methods in interface (implicitly public abstract)
    double calculatePercentage(int pointsEarned, int totalPossiblePoints);
    String evaluateGrade(double percentage);
    boolean isPassing(double percentage);
}
