
package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;

/**
 * This class implements web page ranking using merge sort.
 */
public class Sorting {

    /**
     * Displays the occurrences of a search term in files.
     * @params occurrences - Hashtable<String, Integer>
     */
    public static void sortWebPagesByOccurrence(Hashtable<String, Integer> pagesMap, int occur) {
        if (pagesMap == null || pagesMap.isEmpty()) {
            System.err.println("Error: pagesMap is null or empty.");
            return;
        }

        ArrayList<Map.Entry<String, Integer>> entryList = new ArrayList<>(pagesMap.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        Collections.reverse(entryList);
        if (occur != 0) {
            System.out.println("\n------------------------------Web Page Ranking------------------------------\n");
            int topResults = 5; // number of top results to be shown
            int index = 0;

            System.out.printf("%-10s %s\n", "S.No.", "URL and Occurrence");
            System.out.println("-------------------------------------------------\n");

            while (index < entryList.size() && topResults > 0) {
                Map.Entry<String, Integer> entry = entryList.get(index);
                System.out.printf("%-10d| %s | %d\n", index + 1, entry.getKey(), entry.getValue());
                index++;
                topResults--;
            }
            System.out.println("------------------------------------------------------------\n");
        }
    }
}
