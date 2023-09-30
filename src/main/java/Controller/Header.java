package controller;

public class Header {

    static String lines = "===========================";
    public static void printHeader(String words) {

        System.out.println(
                lines
                        + "\n" + words
                        + "\n" + lines

        );
    }
    public static void printHeader(String words, String wordsTwo) {
        String lines = "===========================";
        System.out.println(
                lines
                + "\n" + words
                + "\n" + lines
                + "\n\n" + wordsTwo
        );
    }

}
