package com.leather;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ReadabilityScore {
    public static void main(String[] args) {
        String path = "./src/main/resources/in.txt";

        try {
            String textAsString = readFileAsString(path);

            System.out.println(textAsString);

            TextAnalyzer text = new TextAnalyzer(textAsString);
            text.printStatistic();

            Menu(text);
        } catch (IOException e) {
            System.out.println("Can't read file: " + e.getMessage());
        }
    }


    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void Menu(TextAnalyzer text) {
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner scanner = new Scanner(System.in);
        String action = scanner.next();
        System.out.println();

        switch (action) {
            case "ARI":
                text.printARI();
                break;
            case "FK":
                text.printFK();
                break;
            case "SMOG":
                text.printSMOG();
                break;
            case "CL":
                text.printCL();
                break;
            case "all":
                text.printARI();
                text.printFK();
                text.printSMOG();
                text.printCL();
                System.out.printf("\nThis text should be understood in average by %.2f-year-olds.", text.getAvgYearsOld());
                break;
        }
    }
}


class TextAnalyzer {
    private final int countWord;
    private final int countSentences;
    private final int countCharacters;
    private final int countSyllables;
    private final int countPolysyllables;

    String[] indexArray = {"0", "6", "7", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "24", "24+"};

    public TextAnalyzer(String textAsString) {
        countWord = textAsString.split("\\s+").length;
        countSentences = textAsString.split("[!?.]").length;
        countCharacters = textAsString.replaceAll("\\s+", "").length();
        countSyllables = setSyllAndPolysyll(textAsString)[0];
        countPolysyllables = setSyllAndPolysyll(textAsString)[1];

    }

    public int countSyllablesInWord(String word) {
        String vowels = "AEIOUY";
        word = word.toUpperCase().replaceAll("[!.,?]", "");
        int countSyllables = 0;
        StringBuilder help = new StringBuilder();

        if (vowels.contains(String.valueOf(word.charAt(0)))) {
            help.append("V");
            countSyllables += 1;
        } else {
            help.append("C");
        }

        for (int i = 1; i < word.length(); i++) {
            boolean contains = vowels.contains(String.valueOf(word.charAt(i)));
            if (contains && help.charAt(help.length() - 1) != 'V') {
                help.append("V");
                countSyllables += 1;
            }

            if (!contains) {
                help.append("C");
            }
        }

        if (word.charAt(word.length() - 1) == 'E') {
            help.deleteCharAt(help.length() - 1);
            countSyllables--;
        }

        //System.out.println(word + "--> " + help + " --> " + countSyllables);
        return countSyllables == 0 ? 1 : countSyllables;
    }

    public int[] setSyllAndPolysyll(String text) {
        int syllables = 0;
        int polysyllables = 0;

        for (String str : text.split("\\s+")) {
            syllables += countSyllablesInWord(str);
            if (countSyllablesInWord(str) > 2) {
                polysyllables += 1;
            }
        }

        return new int[]{syllables, polysyllables};
    }

    public void printStatistic() {
        System.out.println("Words: " + countWord);
        System.out.println("Sentences: " + countSentences);
        System.out.println("Characters: " + countCharacters);
        System.out.println("Syllables: " + countSyllables);
        System.out.println("Polysyllables: " + countPolysyllables + "\n");
    }

    public double calculateARI() {
        return 4.71 * countCharacters / countWord + 0.5 * countWord / countSentences - 21.43;
    }

    public double calculateFK() {
        return 0.39 * countWord / countSentences + 11.8 * countSyllables / countWord - 15.59;
    }

    public double calculateSMOG() {
        return 1.043 * Math.sqrt(1.0 * countPolysyllables * 30 / countSentences) + 3.1291;
    }

    public double calculateCL() {
        double L = 1.0 * countCharacters / countWord * 100;
        double S = 1.0 * countSentences / countWord * 100;
        return 0.0588 * L - 0.296 * S - 15.8;
    }

    public void printARI() {
        double score = calculateARI();
        int intScore = (int) Math.ceil(score);
        // Creating array of age and automated readability index (score)

        //return "This text should be understood by " + indexArray[intScore] + "-years-olds. " + score;
        System.out.printf("Automated Readability Index: %.2f (about %s-year-olds)\n", score, indexArray[intScore]);
    }

    public void printFK() {
        double score = calculateFK();
        int intScore = (int) Math.ceil(score);

        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %s-year-olds)\n", score, this.indexArray[intScore]);

    }

    public void printSMOG() {
        double score = calculateSMOG();
        int intScore = (int) Math.ceil(score);

        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %s-year-olds)\n", score, indexArray[intScore]);
    }

    public void printCL() {
        double score = calculateCL();
        int intScore = (int) Math.ceil(score);
        intScore = Math.min(intScore, 14);

        System.out.printf("Coleman–Liau index: %.2f (about %s-year-olds)\n", score, indexArray[intScore]);
    }

    public double getAvgYearsOld() {
        double ageARI = Double.parseDouble(indexArray[(int) Math.min(Math.ceil(calculateARI()), 14)]);
        double ageFK = Double.parseDouble(indexArray[(int) Math.min(Math.ceil(calculateFK()), 14)]);
        double ageSMOG = Double.parseDouble(indexArray[(int) Math.min(Math.ceil(calculateSMOG()), 14)]);
        double ageCL = Double.parseDouble(indexArray[(int) Math.min(Math.ceil(calculateCL()), 14)]);

        return 1.0 * (ageARI + ageFK + ageSMOG + ageCL) / 4;
    }

}
