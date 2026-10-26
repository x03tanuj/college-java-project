package com.flashquiz.model;

// Unit 3: Multi-level inheritance - Flashcard extends TextCard, which extends Card
public class Flashcard extends TextCard {

    // Default constructor
    public Flashcard() {
        super(0, "Default Question", "Default Answer", "General", 10);
    }

    // Parameterized constructor (2 arguments)
    public Flashcard(String question, String answer) {
        super(0, question, answer, "General", 10);
    }

    // Parameterized constructor (4 arguments) calling super(...)
    public Flashcard(int id, String question, String answer, String topic) {
        super(id, question, answer, topic, 10);
    }

    // Copy constructor calling super(...)
    public Flashcard(Flashcard other) {
        super(other != null ? other.id : 0,
              other != null ? other.question : "",
              other != null ? other.getAnswer() : "",
              other != null ? other.topic : "General",
              10);
    }

    public void setAnswer(String answer) {
        setExpectedAnswer(answer);
    }

    @Override
    public String toString() {
        return "[" + topic + " #" + id + "] Q: " + question + " (Ans: " + getAnswer() + ")";
    }
}
