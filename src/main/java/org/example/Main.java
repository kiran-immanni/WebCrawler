package org.example;
public class Main {
    public static void main(String[] args) {

        System.out.println("\nCSULB ID: "+ Constant.CSULB_ID);
        System.out.println("Student Name: "+ Constant.STUDENT_NAME+"\n");
        System.out.println("***************************************************");
        System.out.println("******** Website Crawl : WORD SEARCH ENGINE *******");
        System.out.println("***************************************************");

        SearchEngine.searchEngine();

        System.out.println("\n***************************************************");
        System.out.println("          THANK YOU");
        System.out.println("******************************************************");
    }
}