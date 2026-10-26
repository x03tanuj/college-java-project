package com.flashquiz.model;

import com.flashquiz.util.StringUtil;

// Unit 3 & Unit 5: MultipleChoiceCard with StringBuffer option formatting and charAt parsing
public class MultipleChoiceCard extends Card {

    private String[] options;
    private char correctOption; // 'A', 'B', 'C', 'D'

    public MultipleChoiceCard() {
        super();
        this.options = new String[]{"Option A", "Option B", "Option C", "Option D"};
        this.correctOption = 'A';
    }

    public MultipleChoiceCard(int id, String question, String[] options, char correctOption, String topic, int points) {
        super(id, question, topic, points);
        this.options = options;
        this.correctOption = Character.toUpperCase(correctOption);
    }

    // Unit 5: Using StringBuffer to format multi-line options display
    @Override
    public void display() {
        StringBuffer sb = new StringBuffer();
        sb.append(getFormattedHeader()).append("\n");
        sb.append("Question: ").append(question).append("\n");
        char label = 'A';
        for (int i = 0; i < options.length; i++) {
            sb.append("  [").append(label).append("] ").append(options[i]).append("\n");
            label++;
        }
        sb.append("Type    : Multiple Choice (Enter A, B, C, D or ':hint' for a hint)");
        System.out.println(sb.toString());
    }

    // Unit 5: String trimming, charAt extraction, and compareTo validation
    @Override
    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null || userAnswer.trim().length() == 0) {
            return false;
        }
        String clean = userAnswer.trim();
        // Unit 5: Character extraction using charAt(0)
        if (clean.length() == 1) {
            return checkAnswer(clean.charAt(0));
        }

        // Check if user entered full option text
        int correctIndex = Character.toUpperCase(correctOption) - 'A';
        if (correctIndex >= 0 && correctIndex < options.length) {
            return StringUtil.compareAnswers(options[correctIndex], clean);
        }
        return false;
    }

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
