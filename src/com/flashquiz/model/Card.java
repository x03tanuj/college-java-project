package com.flashquiz.model;

// Unit 3: Inheritance, access control (protected), final method, method overloading, abstract classes
public abstract class Card {

    // Protected fields accessible by subclasses (Unit 3: access control)
    protected int id;
    protected String question;
    protected String topic;
    protected int points;

    // Unit 3: Base constructor
    public Card() {
        this(0, "Default Question", "General", 10);
    }

    // Unit 3: Parameterized constructor invoked via super(...)
    public Card(int id, String question, String topic, int points) {
        this.id = id;
        this.question = question;
        this.topic = topic;
        this.points = points;
    }

    // Unit 3: Abstract methods that MUST be overridden by concrete subclasses
    public abstract boolean checkAnswer(String userAnswer);
    public abstract void display();
    public abstract String getAnswer();
    public abstract String getCardType();

    // Unit 3: Method Overloading - overloading checkAnswer with char parameter
    public boolean checkAnswer(char optionChar) {
        return checkAnswer(String.valueOf(optionChar));
    }

    // Unit 3: Final method - cannot be overridden by any subclass
    public final String getFormattedHeader() {
        return "=== [Card #" + id + " | " + topic + " | " + getCardType() + " | " + points + " pts] ===";
    }

    // Unit 3: Final method for immutable point retrieval
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
