
package org.example;

public class BoyerMoore {
    private static final int RADIX = 100000;
    private int[] badCharacterSkip;
    private char[] patternArray;
    private String pattern;

    /**
     * Initializes the Boyer-Moore algorithm with the provided pattern.
     * @params pattern - String
     */
    public BoyerMoore(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }

        this.pattern = pattern;
        this.badCharacterSkip = new int[RADIX];

        for (int i = 0; i < RADIX; i++) {
            badCharacterSkip[i] = -1;
        }

        try {
            for (int i = 0; i < pattern.length(); i++) {
                badCharacterSkip[Character.toLowerCase(pattern.charAt(i))] = i;
            }
        } catch (Exception e) {
            System.err.println("Error initializing badCharacterSkip: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Searches for the pattern in the given text using the Boyer-Moore algorithm.
     * @params text - String, the text to search in
     */
    public int search(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        int patternLength = pattern.length();
        int textLength = text.length();
        int skip;

        try {
            for (int i = 0; i <= textLength - patternLength; i += skip) {
                skip = 0;

                for (int j = patternLength - 1; j >= 0; j--) {
                    if (Character.toLowerCase(pattern.charAt(j)) != Character.toLowerCase(text.charAt(i + j))) {
                        skip = Math.max(1, j - badCharacterSkip[Character.toLowerCase(text.charAt(i + j))]);
                        break;
                    }
                }

                if (skip == 0) return i;
            }
        } catch (Exception e) {
            System.err.println("Error during search operation: " + e.getMessage());
            e.printStackTrace();
        }

        return textLength;
    }
}
