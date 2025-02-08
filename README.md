# Website Crawl : WORD SEARCH ENGINE

A web crawler search engine that crawls URLs, extracts content, and allows keyword searching using java & jsoup. 

## Features

- **Web Crawling**: Fetches and extracts text from web pages.
- **Keyword Searching**: Utilizes the Boyer-Moore algorithm for efficient search.
- **Ranking**: Ranks web pages based on search term occurrences.
- **Similar Word Suggestions**: Provides suggestions for similar words based on edit distance.
- **Text Extraction**: Extracts content from HTML pages using Jsoup.

## File Structure
-  **`Main.java`**: The main driver class that initiates the program. It starts the crawling and search operations.
- **`BoyerMoore.java`**: Boyer-Moore pattern search implementation.
- **`Crawler.java`**: Crawls URLs and saves extracted content as text.
- **`SearchEngine.java`**: Handles the web crawling, searching, and sorting processes.
- **`SearchWord.java`**: Handles word searches and finds similar words based on edit distance.
- **`Sorting.java`**: Ranks web pages by keyword occurrences.
- **`Constant.java`**: Stores constants such as file paths and student details.
- **`In.java`**: Handles input from various sources (file, URL, etc.).
- **`EditDistance.java`**: Computes edit distance between words.

## Console Output 
The generated files will be saved in the specified directory (`/assets/textFiles/`), and they will contain the full output of crawled URLs and **search results are copied to ConsoleOutput file**.


