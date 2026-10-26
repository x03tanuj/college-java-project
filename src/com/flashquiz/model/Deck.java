package com.flashquiz.model;

import java.util.Random;

// Unit 3: Polymorphism - Deck manages polymorphic Card references (Card[])
public class Deck {

    private static final int DEFAULT_CAPACITY = 20;

    private String name;
    private String description;
    // Unit 3: Polymorphic array storing any subclass of Card
    private Card[] cards;
    private int cardCount;
    private Random random;

    public Deck() {
        this("Unnamed Deck", "Default flashcard deck");
    }

    public Deck(String name, String description) {
        this.name = name;
        this.description = description;
        this.cards = new Card[DEFAULT_CAPACITY];
        this.cardCount = 0;
        this.random = new Random();
    }

    // Unit 3: Polymorphic method argument - accepts TextCard, MultipleChoiceCard, TrueFalseCard
    public boolean addCard(Card card) {
        if (card == null) {
            return false;
        }
        if (cardCount >= cards.length) {
            resizeArray();
        }
        cards[cardCount] = card;
        cardCount++;
        return true;
    }

    public boolean containsCard(Card targetCard) {
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

    // Unit 3: Returning polymorphic base class object Card
    public Card getCardAt(int index) {
        if (index < 0 || index >= cardCount) {
            return null;
        }
        return cards[index];
    }

    // Unit 3: Returning polymorphic base class object Card
    public Card getRandomCard() {
        if (cardCount == 0) {
            return null;
        }
        int randomIndex = random.nextInt(cardCount);
        return cards[randomIndex];
    }

    private void resizeArray() {
        Card[] expanded = new Card[cards.length * 2];
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

    public void printDeckSummary() {
        System.out.println("Deck: " + name + " (" + cardCount + " cards) - " + description);
    }
}
