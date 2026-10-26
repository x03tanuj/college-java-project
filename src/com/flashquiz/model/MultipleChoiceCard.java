package com.flashquiz.model;

// Unit 3: Inheritance, super keyword, method overriding, method overloading
public class MultipleChoiceCard extends Card {

    private String[] options;
    private char correctOption; // e.g. 'A', 'B', 'C', 'D'

    public MultipleChoiceCard() {
        super();
        this.options = new String[]{"Option A", "Option B", "Option C", "Option D"};
        this.correctOption = 'A';
    }

    // Unit 3: Calling super(...) constructor with arguments
    public MultipleChoiceCard(int id, String question, String[] options, char correctOption, String topic, int points) {
        super(id, question, topic, points);
        this.options = options;
        this.correctOption = Character.toUpperCase(correctOption);
    }

    // Unit 3: Method overriding of abstract method display()
    @Override
    public void display() {
        System.out.println(getFormattedHeader());
        System.out.println("Question: " + question);
        char label = 'A';
        for (int i = 0; i < options.length; i++) {
            System.out.println("  [" + label + "] " + options[i]);
            label++;
        }
        System.out.println("Type    : Multiple Choice (Enter option letter A, B, C, or D)");
    }

    // Unit 3: Method overriding of checkAnswer(String)
    @Override
    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null || userAnswer.trim().length() == 0) {
            return false;
        }
        String clean = userAnswer.trim();
        // Match option letter
        if (clean.length() == 1) {
            return checkAnswer(clean.charAt(0)); // Delegates to overloaded checkAnswer(char)
        }
        // Also allow matching the full text of the correct option
        int correctIndex = Character.toUpperCase(correctOption) - 'A';
        if (correctIndex >= 0 && correctIndex < options.length) {
            return clean.equalsIgnoreCase(options[correctIndex].trim());
        }
        return false;
    }

    // Unit 3: Method overloading - checkAnswer specifically with primitive char
    @Override
    public boolean checkAnswer(char optionChar) {
        return Character.toUpperCase(optionChar) == Character.toUpperCase(this.correctOption);
    }

    @Override
    public String getAnswer() {
        int index = Character.toUpperCase(correctOption) - 'A';
        if (index >= 0 && index < options.length) {
            return correctOption + " (" + options[index] + ")";
        }
        return String.valueOf(correctOption);
    }

    public char getCorrectOptionChar() {
        return correctOption;
    }

    @Override
    public String getCardType() {
        return "MCQ";
    }

    public String[] getOptions() {
        return options;
    }
}
