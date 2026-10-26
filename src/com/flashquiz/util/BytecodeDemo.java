package com.flashquiz.util;

// Unit 1: Bytecode demonstration class
// This class contains simple arithmetic, branching, and method invocations
// for inspection with javap -c.
public class BytecodeDemo {

    // Demonstrates local variable loading, addition, and returning an int
    public int calculateBonus(int baseScore, int streak) {
        int bonus = 0;
        if (streak > 3) {
            bonus = baseScore * 2;
        } else {
            bonus = baseScore + streak;
        }
        return bonus;
    }

    // Demonstrates loop, counter increment, and comparison
    public int sumArray(int[] numbers) {
        int total = 0;
        for (int i = 0; i < numbers.length; i++) {
            total += numbers[i];
        }
        return total;
    }
}
