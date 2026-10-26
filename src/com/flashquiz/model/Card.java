package com.flashquiz.model;

import com.flashquiz.util.StringUtil;

// Unit 3 & Unit 5: Abstract Card class with StringBuffer-based header formatting and String hint extraction
public abstract class Card {

    protected int id;
    protected String question;
    protected String topic;
    protected int points;

    public Card() {
        this(0, "Default Question", "General", 10);
    }

    public Card(int id, String question, String topic, int points) {
        this.id = id;
        this.question = question;
        this.topic = topic;
        this.points = points;
    }

    public abstract boolean checkAnswer(String userAnswer);
    public abstract void display();
    public abstract String getAnswer();
    public abstract String getCardType();

    public boolean checkAnswer(char optionChar) {
        return checkAnswer(String.valueOf(optionChar));
    }

    // Unit 5: Using StringBuffer to build formatted header (append)
    public final String getFormattedHeader() {
        StringBuffer sb = new StringBuffer();
        sb.append("=== [Card #").append(id);
        sb.append(" | ").append(topic);
        sb.append(" | ").append(getCardType());
        sb.append(" | ").append(points).append(" pts] ===");
        return sb.toString();
    }

    // Unit 5: Hint feature using character extraction via StringUtil
    public String getHint() {
        return StringUtil.buildHint(getAnswer());
    }

    public final int getPoints() {
        return points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
