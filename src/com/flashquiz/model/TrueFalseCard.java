package com.flashquiz.model;

// Unit 3 & Unit 5: TrueFalseCard with StringBuffer display and String case comparisons
public class TrueFalseCard extends Card {

    private boolean correctAnswer;

    public TrueFalseCard() {
        super();
        this.correctAnswer = true;
    }

    public TrueFalseCard(int id, String question, boolean correctAnswer, String topic, int points) {
        super(id, question, topic, points);
        this.correctAnswer = correctAnswer;
    }

    // Unit 5: StringBuffer display formatting
    @Override
    public void display() {
        StringBuffer sb = new StringBuffer();
        sb.append(getFormattedHeader()).append("\n");
        sb.append("Question: ").append(question).append("\n");
        sb.append("Type    : True / False (Enter 'true'/'false', 'T'/'F', or ':hint' for hint)");
        System.out.println(sb.toString());
    }

    // Unit 5: String trimming, equalsIgnoreCase, and comparison
    @Override
    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null) {
            return false;
        }
        String clean = userAnswer.trim();
        // Unit 5: equalsIgnoreCase comparison
        if (clean.equalsIgnoreCase("t") || clean.equalsIgnoreCase("true")) {
            return this.correctAnswer == true;
        } else if (clean.equalsIgnoreCase("f") || clean.equalsIgnoreCase("false")) {
            return this.correctAnswer == false;
        }
        return false;
    }

    @Override
    public String getAnswer() {
        return correctAnswer ? "True" : "False";
    }

    @Override
    public String getCardType() {
        return "TRUE_FALSE";
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }
}
