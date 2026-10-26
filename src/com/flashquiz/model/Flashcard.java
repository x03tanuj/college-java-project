package com.flashquiz.model;

// Unit 2: Classes, objects, constructors, encapsulation, passing and returning objects
public class Flashcard {

    // Instance variables (encapsulation)
    private int id;
    private String question;
    private String answer;
    private String topic;

    // Unit 2: Default (no-argument) constructor
    public Flashcard() {
        this(0, "Default Question", "Default Answer", "General");
    }

    // Unit 2: Parameterized constructor (2 parameters)
    public Flashcard(String question, String answer) {
        this(0, question, answer, "General");
    }

    // Unit 2: Overloaded parameterized constructor (4 parameters) using 'this' keyword
    public Flashcard(int id, String question, String answer, String topic) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.topic = topic;
    }

    // Unit 2: Copy constructor - passing an object of the same class to initialize another
    public Flashcard(Flashcard other) {
        if (other != null) {
            this.id = other.id;
            this.question = other.question;
            this.answer = other.answer;
            this.topic = other.topic;
        }
    }

    // Unit 2: Method demonstrating object inspection
    public boolean checkAnswer(String userResponse) {
        if (userResponse == null || this.answer == null) {
            return false;
        }
        return this.answer.trim().equalsIgnoreCase(userResponse.trim());
    }

    // Getters and Setters
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    // Unit 2: Overriding toString() for clean object representation
    @Override
    public String toString() {
        return "[" + topic + " #" + id + "] Q: " + question + " (Ans: " + answer + ")";
    }
}
