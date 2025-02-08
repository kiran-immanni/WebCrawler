package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;

public class EditDistance {

    /**
     * Calculates the edit distance between two words using dynamic programming.
     * @params word1 - String, word2 - String
     */
    public static int findEditDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                if (c1 == c2) {
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int insert = dp[i][j + 1] + 1;
                    int delete = dp[i + 1][j] + 1;

                    int min = Math.min(replace, insert);
                    min = Math.min(min, delete);
                    dp[i + 1][j + 1] = min;
                }
            }
        }
        return dp[len1][len2];
    }

    /**
     * Reads a file, searches for matching words using a regular expression, and updates the keywords and page occurrences.
     * @params sourceFile - File, fileNumber - int, matcher - Matcher, p1 - String
     */

    public static void findWord(File sourceFile, int fileNumber, Matcher matcher, String p1) {
        if (matcher == null || p1 == null || sourceFile == null) {
            System.err.println("Error: Invalid arguments passed to findWord method.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                matcher.reset(line);
                while (matcher.find()) {
                    SearchEngine.keywords.add(matcher.group()); // Accessing static variable key

                }
            }

            for (int p = 0; p < SearchEngine.keywords.size(); p++) {
                SearchEngine.pageOccurrences.put(SearchEngine.keywords.get(p), findEditDistance(p1.toLowerCase(), SearchEngine.keywords.get(p).toLowerCase())); // Accessing static variable numbers
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + sourceFile.getName());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
