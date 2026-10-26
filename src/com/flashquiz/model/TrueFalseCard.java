package com.flashquiz.model;

// Unit 3: Inheritance, super constructor call, method overriding
public class TrueFalseCard extends Card {

    private boolean correctAnswer;

    public TrueFalseCard() {
        super();
        this.correctAnswer = true;
    }

    // Unit 3: Calling super(...) constructor
    public TrueFalseCard(int id, String question, boolean correctAnswer, String topic, int points) {
        super(id, question, topic, points);
        this.correctAnswer = correctAnswer;
    }

    // Unit 3: Method overriding of abstract method display()
    @Override
    public void display() {
        System.out.println(getFormattedHeader());
        System.out.println("Question: " + question);
        System.out.println("Type    : True / False (Enter 'true' or 'false', or 'T'/'F')");
    }

    // Unit 3: Method overriding of abstract method checkAnswer(String)
    @Override
    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null) {
            return false;
        }
        String clean = userAnswer.trim().toLowerCase();
        if (clean.equals("t") || clean.equals("true")) {
            return this.correctAnswer == true;
        } else if (clean.equals("f") || clean.equals("false")) {
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
