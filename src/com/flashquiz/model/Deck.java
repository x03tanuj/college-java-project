package com.flashquiz.model;

import java.util.Random;
import com.flashquiz.io.Persistable;
import com.flashquiz.exception.InvalidDeckException; // Unit 6: Checked exception import
import com.flashquiz.exception.InvalidInputException; // Unit 6: Unchecked exception import

// Unit 4 & Unit 6: Deck with exception declarations (throws) and throwing exceptions (throw)
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
        if (name == null || name.trim().length() == 0) {
            // Unit 6: throw unchecked exception on illegal argument
            throw new InvalidInputException("Deck name cannot be null or empty.");
        }
        this.name = name;
        this.description = description;
        this.cards = new Card[DEFAULT_CAPACITY];
        this.cardCount = 0;
        this.random = new Random();
    }

    public boolean addCard(Card card) {
        if (card == null) {
            // Unit 6: throw unchecked exception
            throw new InvalidInputException("Cannot add a null Card object to Deck.");
        }
        if (cardCount >= cards.length) {
            resizeArray();
        }
        cards[cardCount] = card;
        cardCount++;
        DeckValidator.validateCardCount(cardCount, 1000);
        return true;
    }

    // Unit 6: Method declaring checked exception using 'throws' keyword
    public void validateDeck() throws InvalidDeckException {
        if (cardCount == 0) {
            // Unit 6: throw checked exception using 'throw' keyword
            throw new InvalidDeckException("Deck validation failed: '" + name + "' contains 0 cards.");
        }
        for (int i = 0; i < cardCount; i++) {
            if (cards[i] == null) {
                throw new InvalidDeckException("Deck corrupted: Null card found at slot " + i);
            }
            if (cards[i].getQuestion() == null || cards[i].getQuestion().trim().length() == 0) {
                throw new InvalidDeckException("Deck card #" + (i + 1) + " has an empty question.");
            }
        }
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

    // Unit 6: Throwing ArrayIndexOutOfBoundsException when index is invalid
    public Card getCardAt(int index) {
        if (index < 0 || index >= cardCount) {
            throw new ArrayIndexOutOfBoundsException("Card index " + index + " is out of bounds (count: " + cardCount + ")");
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
        if (name == null || name.trim().length() == 0) {
            throw new InvalidInputException("Deck name cannot be set to null or empty.");
        }
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

    @Override
    public String toDataRecord() {
        return "DECK|" + name + "|" + description + "|" + cardCount;
    }

    @Override
    public void save(String destinationPath) throws Exception {
        System.out.println("[Persistable] Deck ready for serialization to: " + destinationPath);
    }

    @Override
    public void load(String sourcePath) throws Exception {
        System.out.println("[Persistable] Deck ready for loading from: " + sourcePath);
    }
}
