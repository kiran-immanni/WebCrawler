
package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class SearchEngine {
    static ArrayList<String> keywords = new ArrayList<>();
    static Hashtable<String, Integer> pageOccurrences = new Hashtable<>();
    static int searchCount = 1;
    static int rightIndex;
    static int[] rightArray;

    /**
     * Initializes the SearchEngine and prints a welcome message.
     */
    public SearchEngine() {}

    /**
     * Searches for the pattern in the given text using the Boyer-Moore algorithm.
     * @params pattern - String, text - String
     */
    public static int search1(String pattern, String text) {
        try {
            BoyerMoore boyerMoore = new BoyerMoore(pattern);
            return boyerMoore.search(text);
        } catch (Exception e) {
            System.err.println("Error in Boyer-Moore search: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Crawls a URL, processes files to search for a term, and displays web page rankings based on occurrences.
     */
    public static void searchEngine() {
        System.out.println(System.getProperty("user.dir") + Constant.FILE_PATH);
        File directory = new File("C:\\Users\\imman\\IdeaProjects\\WebCrawl\\assets\\textFiles");

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Error: Directory not found or is not a valid folder.");
            return;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    if (file.getName().endsWith(".txt") && !file.delete()) {
                        System.out.println("Warning: Failed to delete " + file.getName());
                    }
                } catch (Exception e) {
                    System.err.println("Error deleting file: " + file.getName() + " - " + e.getMessage());
                }
            }
        }

        try (Scanner inputScanner = new Scanner(System.in)) {
            new SearchEngine();

            System.out.println("\nEnter the URL you want to crawl:");
            String urlToCrawl = inputScanner.nextLine();
            System.out.println("\n***************** CRAWLING STARTED ******************");

            try {
                Crawler.spider(urlToCrawl);
            } catch (Exception e) {
                System.err.println("Error during crawling: " + e.getMessage());
                return;
            }

            System.out.println("\n***************** CRAWLING STOPPED ******************");

            Hashtable<String, Integer> occurrences = new Hashtable<>();
            String userChoice;
            do {
                System.out.println("\n***************************************************");
                System.out.println("Enter the search term:");
                String searchTerm = inputScanner.nextLine();
                System.out.println("***************************************************");

                int matchedPages = 0;

                try {
                    File[] textFiles = directory.listFiles();
                    if (textFiles != null) {
                        for (File file : textFiles) {
                            try {
                                int matchCount = SearchWord.wordSearch(searchTerm, file);
                                occurrences.put(file.getName(), matchCount);
                                if (matchCount > 0) matchedPages++;
                            } catch (Exception e) {
                                System.err.println("Error processing file: " + file.getName() + " - " + e.getMessage());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error accessing files: " + e.getMessage());
                }

                if (matchedPages == 0) {
                    System.out.println("\n---------------------------------------------------");
                    System.out.println("No exact matches found! Searching for similar words...");
                    try {
                        SearchWord.altWord(searchTerm);
                    } catch (Exception e) {
                        System.err.println("Error finding similar words: " + e.getMessage());
                    }
                } else {
                    displayOccurrences(occurrences);
                    try {
                        Sorting.sortWebPagesByOccurrence(occurrences, matchedPages);
                    } catch (Exception e) {
                        System.err.println("Error sorting web pages: " + e.getMessage());
                    }
                }

                System.out.println("\nDo you want to continue? (y/n)");
                userChoice = inputScanner.nextLine();
            } while (userChoice.equalsIgnoreCase("y"));

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Displays the occurrences of a search term in files.
     * @params occurrences - Hashtable<String, Integer>
     */
    static void displayOccurrences(Hashtable<String, Integer> occurrences) {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("| %10s | %20s |\n", "OCCURRENCES", "FILE NAME");
        System.out.println("-----------------------------------------------------------------------------");
        occurrences.forEach((fileName, count) -> System.out.printf("| %10d | %20s |\n", count, fileName));
        System.out.println("-----------------------------------------------------------------------------");
    }
}
