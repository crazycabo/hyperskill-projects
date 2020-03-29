import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Brian Smith on 3/29/20.
 * Description:
 */
public class Main {

    private static int wordCount;
    private static int sentenceCount;
    private static int characterCount;
    private static int syllableCount;
    private static int polySyllableCount;

    public Main() {
        wordCount = 0;
        sentenceCount = 0;
        characterCount = 0;
    }

    private static void analyzeFileText(String line) {

        String[] words = line.split(" ");
        wordCount += words.length;

        for (String word : words) {
            if (word.matches("\\w*[.!?]")) {
                sentenceCount++;
            }

            syllableCount += countSyllables(word);
        }

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (String.valueOf(c).matches("[^ \n\t]")) {
                characterCount++;
            }

            if (i == line.length() - 1 && !String.valueOf(c).matches("[.!?]")) {
                sentenceCount++;
            }
        }
    }

    private static int countSyllables(String word) {
        int syllableCount = 0;
        int characterCount = word.length();
        int currentCharIndex = 0;
        String previousChar = "";

        for (String character : word.split("")) {

            if (character.matches("[aeiouy]") && !previousChar.matches("[aeiouy]")) {

                if (character.matches("[e]") && currentCharIndex != characterCount - 1) {
                    syllableCount++;
                } else {
                    syllableCount++;
                }
            }

            previousChar = character;
            currentCharIndex++;
        }

        if (syllableCount > 2) {
            polySyllableCount++;
        }

        return (syllableCount == 0 && currentCharIndex == word.length() - 1) ? 1 : syllableCount;
    }

    private static double calculateReadabilityIndex() {
        return 4.71 * ((double) characterCount / (double) wordCount) + 0.5 * ((double) wordCount / (double) sentenceCount) - 21.43;
    }

    private static double calculateFleschKincaidReadability() {
        return 0.39 * ((double) wordCount / (double) sentenceCount) + 11.8 * ((double) syllableCount / (double) wordCount) - 15.59;
    }

    private static double calculateSmogIndex() {
        return 1.043 * Math.sqrt(polySyllableCount * (30 / (double) sentenceCount)) + 3.1291;
    }

    private static double calculateColemanLiauIndex() {
        return 0.0588 * ((100 / (double) wordCount) * characterCount) - 0.296 * (100 / (double) wordCount * sentenceCount) - 15.8;
    }

    private static int getYearRange(double score) {
        switch ((int) score) {
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 9;
            case 4:
                return 10;
            case 5:
                return 11;
            case 6:
                return 12;
            case 7:
                return 13;
            case 8:
                return 14;
            case 9:
                return 15;
            case 10:
                return 16;
            case 11:
                return 17;
            case 12:
                return 18;
            default:
                return 24;
        }
    }

    private static void printLine(String text, Object... args) {
        System.out.printf(text, args);
    }

    public static void main(String[] args) {

        File file = new File(args[0]);

        try {
            Scanner fileScanner = new Scanner(file);
            analyzeFileText(fileScanner.nextLine());
        } catch (FileNotFoundException e) {
            System.out.println("Input file could not be found!");
        }

        // Indexes
        double automatedReadabilityIndex = calculateReadabilityIndex();
        double fleschKincaidReadabilityIndex = calculateFleschKincaidReadability();
        double simpleMeasureOfGobbledygook = calculateSmogIndex();
        double colemanLiauIndex = calculateColemanLiauIndex();

        // Ages
        double automatedReadabilityIndexAge = getYearRange(Math.ceil(automatedReadabilityIndex));
        double fleschKincaidReadabilityIndexAge = getYearRange(Math.ceil(fleschKincaidReadabilityIndex));
        double simpleMeasureOfGobbledygookAge = getYearRange(Math.ceil(simpleMeasureOfGobbledygook));
        double colemanLiauIndexAge = getYearRange(Math.ceil(colemanLiauIndex));

        // Average age of all calculations
        double avgAge = ((automatedReadabilityIndexAge + fleschKincaidReadabilityIndexAge + simpleMeasureOfGobbledygookAge + colemanLiauIndexAge) / 4);

        printLine("Words: %s%n", wordCount);
        printLine("Sentences: %s%n", sentenceCount);
        printLine("Characters: %s%n", characterCount);
        printLine("Syllables: %s%n", syllableCount);
        printLine("Polysyllables: %s%n", polySyllableCount);

        printLine("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): %s%n", "all");

        printLine("Automated Readability Index: %.2f (about %s year olds).%n", automatedReadabilityIndex, automatedReadabilityIndexAge);
        printLine("Flesch–Kincaid readability tests: %.2f (about %s year olds).%n", fleschKincaidReadabilityIndex, fleschKincaidReadabilityIndexAge);
        printLine("Simple Measure of Gobbledygook: %.2f (about %s year olds).%n", simpleMeasureOfGobbledygook, simpleMeasureOfGobbledygookAge);
        printLine("Coleman–Liau index: %.2f (about %s year olds).%n", colemanLiauIndex, colemanLiauIndexAge);

        printLine("This text should be understood by %s year olds.%n", avgAge);
    }
}
