package com.flashquiz.model;

import java.util.Random;
import com.flashquiz.io.Persistable; // Unit 4: Importing interface from com.flashquiz.io package

// Unit 4: Class implementing an interface from another package (com.flashquiz.io.Persistable)
public class Deck implements Persistable {

    private static final int DEFAULT_CAPACITY = 20;

    private String name;
    private String description;
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

    public boolean addCard(Card card) {
        if (card == null) {
            return false;
        }
        if (cardCount >= cards.length) {
            resizeArray();
        }
        cards[cardCount] = card;
        cardCount++;
        // Unit 4: Calling package-private method from DeckValidator (same package access)
        DeckValidator.validateCardCount(cardCount, 1000);
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

    public Card getCardAt(int index) {
        if (index < 0 || index >= cardCount) {
            return null;
        }
        return cards[index];
    }

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

    // Unit 4: Implementation of Persistable interface methods
    @Override
    public String toDataRecord() {
        return "DECK|" + name + "|" + description + "|" + cardCount;
    }

    @Override
    public void save(String destinationPath) throws Exception {
        // Will be connected to File I/O in Unit 7
        System.out.println("[Persistable] Deck ready for serialization to: " + destinationPath);
    }

    @Override
    public void load(String sourcePath) throws Exception {
        // Will be connected to File I/O in Unit 7
        System.out.println("[Persistable] Deck ready for loading from: " + sourcePath);
    }
}
