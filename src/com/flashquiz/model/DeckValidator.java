package com.flashquiz.model;

// Unit 4: Default (package-private) access modifier on class
// This class is intentionally NOT public; it is accessible ONLY within com.flashquiz.model package.
class DeckValidator {

    // Unit 4: Default (package-private) method
    static boolean validateCardCount(int count, int maxLimit) {
        return count >= 0 && count <= maxLimit;
    }

    // Unit 4: Default (package-private) method
    static String formatCardSummary(Card card) {
        if (card == null) {
            return "Null Card";
        }
        return "Card ID=" + card.getId() + ", Topic=" + card.getTopic() + ", Type=" + card.getCardType();
    }
}
