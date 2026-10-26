package com.flashquiz.model;

import com.flashquiz.util.StringUtil;

// Unit 3 & Unit 5: TextCard implementing String comparisons (trim, equalsIgnoreCase, compareTo)
public class TextCard extends Card {

    private String expectedAnswer;

    public TextCard() {
        super();
        this.expectedAnswer = "Default Answer";
    }

    public TextCard(int id, String question, String expectedAnswer, String topic, int points) {
        super(id, question, topic, points);
        this.expectedAnswer = expectedAnswer;
    }

    // Unit 5: Answer checking using StringUtil (trim, equalsIgnoreCase, compareTo, contains)
    @Override
    public boolean checkAnswer(String userAnswer) {
        if (userAnswer == null || expectedAnswer == null) {
            return false;
        }
        return StringUtil.compareAnswers(expectedAnswer, userAnswer);
    }

    // Unit 5: Displaying card with StringBuffer
    @Override
    public void display() {
        StringBuffer sb = new StringBuffer();
        sb.append(getFormattedHeader()).append("\n");
        sb.append("Question: ").append(question).append("\n");
        sb.append("Type    : Direct Text Answer (Type ':hint' for a hint)");
        System.out.println(sb.toString());
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
