
package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Crawler {
    static HashSet<String> visitedLinks = new HashSet<>();

    /**
     * Crawls a URL to fetch its content and processes anchor tags to find new URLs.
     * @params url - String
     */
    public static void crawlURL(String url) {
        if (url == null || url.isEmpty()) {
            System.err.println("Invalid URL.");
            return;
        }

        try {
            Document pageContent = Jsoup.connect(url).get();
            visitedLinks.add(url);  // Add the URL to visited links only after it is fetched

            // Regular expression to match web URLs
            String pattern = "^((https?://)|(www\\.))[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            System.out.println("\nParsing: " + pattern);

            String tempURL;
            // Iterate over anchor tags with href attributes using CSS selector
            for (Element anchorTag : pageContent.select("a[href]")) {
                tempURL = anchorTag.attr("abs:href");

                // Validate and process the URL
                if (!Pattern.matches(pattern, tempURL)) {
                    System.out.println("Found URL: " + tempURL + " => unknown");
                } else if (visitedLinks.contains(tempURL)) {
                    System.out.println("Found URL: " + tempURL + " => already visited");
                } else {
                    visitedLinks.add(tempURL);
                    System.out.println("Found URL: " + tempURL + " => added to crawl list");
                }
            }
        } catch (org.jsoup.HttpStatusException e) {
            System.out.println("URL: " + url + " => blocked, not crawled");
        } catch (IOException e) {
            System.out.println("URL: " + url + " => I/O error, not crawled");
        }
    }

    /**
     * Extracts text content from HTML pages and saves it to a file.
     */
    public static void extractTextFromHTML() {
        String currentURL, textContent;
        String filePath = System.getProperty("user.dir") + Constant.FILE_PATH;

        try {
            Iterator<String> iterator = visitedLinks.iterator();
            while (iterator.hasNext()) {
                currentURL = iterator.next();
                try {
                    Document document = Jsoup.connect(currentURL).get();
                    textContent = document.text();

                    // Clean the document title and save content
                    String docTitle = document.title().replaceAll("[^a-zA-Z0-9_-]", "") + ".txt";
                    try (BufferedWriter out = new BufferedWriter(new FileWriter(filePath + docTitle, true))) {
                        out.write(currentURL + " " + textContent);
                    }
                } catch (org.jsoup.HttpStatusException e) {
                    System.out.println("URL from page: " + currentURL + " => blocked, not crawled");
                } catch (IOException e) {
                    System.out.println("Error writing to file for URL: " + currentURL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crawls a URL and extracts text content from HTML pages.
     * @params url - String
     */
    public static void spider(String url) {
        if (url == null || url.isEmpty()) {
            System.err.println("Invalid URL provided for crawling.");
            return;
        }

        crawlURL(url);
        extractTextFromHTML();
    }
}
