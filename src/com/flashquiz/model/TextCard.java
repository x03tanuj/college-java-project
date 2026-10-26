package com.flashquiz.model;

// Unit 3: Inheritance (extends), super(...) constructor call, method overriding
public class TextCard extends Card {

    private String expectedAnswer;

    public TextCard() {
        super();
        this.expectedAnswer = "Default Answer";
    }

    // Unit 3: super(...) constructor call passing parameters to Card base class
    public TextCard(int id, String question, String expectedAnswer, String topic, int points) {
        super(id, question, topic, points);
        this.expectedAnswer = expectedAnswer;
    }

    // Unit 3: Method overriding of abstract method checkAnswer(String)
    @Override
    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null || expectedAnswer == null) {
            return false;
        }
        return this.expectedAnswer.trim().equalsIgnoreCase(userAnswer.trim());
    }

    // Unit 3: Method overriding of abstract method display()
    @Override
    public void display() {
        System.out.println(getFormattedHeader());
        System.out.println("Question: " + question);
        System.out.println("Type    : Direct Text Answer");
    }

    @Override
    public String getAnswer() {
        return expectedAnswer;
    }

    @Override
    public String getCardType() {
        return "TEXT";
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }
}
