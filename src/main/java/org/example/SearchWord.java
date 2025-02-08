
package org.example;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchWord {

	/**
	 * Searches for a word in the specified file and counts the occurrences.
	 * @params word - String, filePath - File
	 */
	public static int wordSearch(String word, File filePath) throws IOException {
		if (word == null || filePath == null) {
			throw new IllegalArgumentException("Word or file path cannot be null");
		}

		int count = 0;
		StringBuilder data = new StringBuilder();

		// Read the file and load its contents
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				data.append(line);
			}
		} catch (IOException e) {
			System.err.println("Error reading file: " + filePath.getName());
			throw e;
		}

		String text = data.toString();
		int offset = 0, loc = 0;
		while (loc <= text.length()) {
			offset = SearchEngine.search1(word, text.substring(loc));
			if ((offset + loc) < text.length()) {
				count++;
				System.out.println(word + " found at position " + (offset + loc));
			}
			loc += offset + word.length();
		}

		if (count > 0) {
			System.out.println("-------------------------------------------------");
			System.out.println("Word found in " + filePath.getName());
			System.out.println("-------------------------------------------------");
		}

		return count;
	}

	/**
	 * Finds data in the specified file using the provided matcher and processes it.
	 * @params sourceFile - File, fileNumber - int, matcher - Matcher, p1 - String
	 */
	public static void findData(File sourceFile, int fileNumber, Matcher matcher, String p1) {
		try {
			if (sourceFile != null && matcher != null && p1 != null) {
				EditDistance.findWord(sourceFile, fileNumber, matcher, p1);
			} else {
				System.err.println("Invalid input parameters for findData.");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Error processing file: " + sourceFile.getName());
			e.printStackTrace();
		}
	}

	/**
	 * Finds all the words with an edit distance of 1 to the provided word.
	 * @params word The word to find similar words for
	 */
	public static void altWord(String word) {
		if (word == null || word.isEmpty()) {
			System.err.println("Invalid word input for alternative search.");
			return;
		}

		String str = " ";
		String pattern1 = "[0-9a-zA-Z]+";

		Pattern pattern = Pattern.compile(pattern1);
		Matcher matcher = pattern.matcher(str);
		int fileNumber = 0;

		File dir = new File(System.getProperty("user.dir") + Constant.FILE_PATH);
		File[] files = dir.listFiles();

		if (files != null) {
			for (File file : files) {
				try {
					findData(file, fileNumber, matcher, word);
					fileNumber++;
				} catch (Exception e) {
					System.err.println("Error processing file: " + file.getName());
				}
			}
		}

		int allowedDistance = 1;
		boolean matchFound = false;
		System.out.println("Did you mean? ");

		// Check and display words with an edit distance of 1
		int i = 0;
		for (String key : SearchEngine.pageOccurrences.keySet()) {
			if (allowedDistance == SearchEngine.pageOccurrences.get(key)) {
				i++;
				System.out.println("(" + i + ") " + key);
				matchFound = true;
			}
		}

		if (!matchFound) {
			System.out.println("No similar words found.");
		}
	}
}
