package com.flashquiz.model;

// Unit 3: Final class demonstration - cannot be extended/subclassed by any other class
public final class CardMetadata {

    private final String author;
    private final String version;
    private final long createdAtMillis;

    public CardMetadata(String author, String version) {
        this.author = author;
        this.version = version;
        this.createdAtMillis = System.currentTimeMillis();
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

    public long getCreatedAtMillis() {
        return createdAtMillis;
    }

    @Override
    public String toString() {
        return "CardMetadata [Author: " + author + ", Version: " + version + "]";
    }
}
