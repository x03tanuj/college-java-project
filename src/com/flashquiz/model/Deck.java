package com.flashquiz.model;

import java.util.Random;

// Unit 2: Classes, objects, constructors, passing and returning objects, arrays of objects
public class Deck {

    private static final int DEFAULT_CAPACITY = 20;

    private String name;
    private String description;
    // Unit 2: Array of objects
    private Flashcard[] cards;
    private int cardCount;
    private Random random;

    // Unit 2: Default constructor
    public Deck() {
        this("Unnamed Deck", "Default flashcard deck");
    }

    // Unit 2: Parameterized constructor
    public Deck(String name, String description) {
        this.name = name;
        this.description = description;
        this.cards = new Flashcard[DEFAULT_CAPACITY];
        this.cardCount = 0;
        this.random = new Random();
    }

    // Unit 2: Passing an object as a method parameter
    public boolean addCard(Flashcard card) {
        if (card == null) {
            return false;
        }
        // Expand array capacity if full (plain array manipulation)
        if (cardCount >= cards.length) {
            resizeArray();
        }
        cards[cardCount] = card;
        cardCount++;
        return true;
    }

    // Unit 2: Passing an object as a parameter to check membership
    public boolean containsCard(Flashcard targetCard) {
        if (targetCard == null) {
            return false;
        }
        for (int i = 0; i < cardCount; i++) {
            if (cards[i] != null && cards[i].getId() == targetCard.getId()) {
                return true;
            }
        }
        return false;
    }

    // Unit 2: Returning an object from a method by index
    public Flashcard getCardAt(int index) {
        if (index < 0 || index >= cardCount) {
            return null;
        }
        return cards[index];
    }

    // Unit 2: Returning an object from a method (random card selection)
    public Flashcard getRandomCard() {
        if (cardCount == 0) {
            return null;
        }
        int randomIndex = random.nextInt(cardCount);
        return cards[randomIndex];
    }

    // Plain array resizing to maintain array demonstration
    private void resizeArray() {
        Flashcard[] expanded = new Flashcard[cards.length * 2];
        for (int i = 0; i < cards.length; i++) {
            expanded[i] = cards[i];
        }
        this.cards = expanded;
    }

    public int getCardCount() {
        return cardCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Unit 2: Printing summary of object state
    public void printDeckSummary() {
        System.out.println("Deck: " + name + " (" + cardCount + " cards) - " + description);
    }
}
