package com.flashquiz.util;

// Unit 5: Strings - constructors, character extraction, search, compare, and StringBuffer
public class StringUtil {

    // Unit 5: Demonstrating String constructors
    public static String constructFromCharArray(char[] chars) {
        if (chars == null) {
            return new String(""); // String(String) constructor
        }
        return new String(chars); // String(char[]) constructor
    }

    // Unit 5: Demonstrating character extraction with charAt() and substring()
    public static String buildHint(String rawAnswer) {
        if (rawAnswer == null) {
            return "(No hint available)";
        }
        // Unit 5: trim()
        String clean = rawAnswer.trim();
        if (clean.length() == 0) {
            return "(Empty answer)";
        }

        // If very short (1 or 2 characters), reveal the first char using charAt
        if (clean.length() <= 2) {
            char firstChar = clean.charAt(0); // Unit 5: charAt()
            return "Hint: Starts with '" + firstChar + "' (" + clean.length() + " character(s) total)";
        }

        // If longer, extract prefix with substring and mask rest with underscores
        int prefixLen = Math.min(2, clean.length() / 2);
        String prefix = clean.substring(0, prefixLen); // Unit 5: substring(start, end)
        
        // Build masked remainder
        StringBuffer masked = new StringBuffer(); // Unit 5: StringBuffer
        masked.append(prefix);
        for (int i = prefixLen; i < clean.length(); i++) {
            char ch = clean.charAt(i);
            if (Character.isWhitespace(ch)) {
                masked.append(' ');
            } else {
                masked.append('*');
            }
        }
        masked.append(" (").append(clean.length()).append(" chars)");
        return "Hint: " + masked.toString();
    }

    // Unit 5: Searching and comparing with equalsIgnoreCase, contains, indexOf, compareTo
    public static boolean compareAnswers(String expected, String actual) {
        if (expected == null || actual == null) {
            return false;
        }

        // Unit 5: trim()
        String expClean = expected.trim();
        String actClean = actual.trim();

        // 1. Exact case-insensitive match
        if (expClean.equalsIgnoreCase(actClean)) { // Unit 5: equalsIgnoreCase
            return true;
        }

        // 2. Lexicographical comparison check
        if (expClean.compareToIgnoreCase(actClean) == 0) { // Unit 5: compareToIgnoreCase
            return true;
        }

        // 3. Substring / keyword search using contains() and indexOf()
        // If expected has parentheses or options (e.g. "A (Class)"), check if actual is found inside
        if (expClean.indexOf('(') != -1) { // Unit 5: indexOf(char)
            int openParen = expClean.indexOf('(');
            int closeParen = expClean.indexOf(')');
            if (openParen != -1 && closeParen > openParen) {
                String inside = expClean.substring(openParen + 1, closeParen).trim();
                if (inside.equalsIgnoreCase(actClean)) {
                    return true;
                }
            }
        }

        // Check if user answer contains expected or vice versa for compound answers
        if (expClean.length() > 5 && actClean.length() > 5) {
            if (expClean.toLowerCase().contains(actClean.toLowerCase())) { // Unit 5: contains()
                return true;
            }
        }

        return false;
    }

    // Unit 5: Building formatted banner and strings with StringBuffer (append, insert, reverse)
    public static String formatBannerWithDecoration(String title) {
        // Unit 5: StringBuffer demonstration
        StringBuffer buffer = new StringBuffer();
        buffer.append("=== ");
        buffer.append(title.toUpperCase());
        buffer.append(" ===");

        // Symmetric decor created using reverse()
        StringBuffer decor = new StringBuffer(">>>---* ");
        String leftDecor = decor.toString();
        String rightDecor = decor.reverse().toString(); // Unit 5: reverse()

        StringBuffer finalBanner = new StringBuffer();
        finalBanner.append(leftDecor);
        finalBanner.append(buffer);
        finalBanner.append(" ");
        finalBanner.append(rightDecor);

        // Unit 5: insert() demonstration
        finalBanner.insert(0, "\n");

        return finalBanner.toString();
    }
}
