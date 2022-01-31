package de.borekking.crpyto.frequencyAnalysis.backEnd;

import de.borekking.crpyto.util.TextUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LetterCounter {

    // TODO Implement analyzing Letter Pairs: TH, ER, ON, and AN

    // Amount of total characters
    private final long totalCharacters;

    // Amount of different characters
    private final int differentCharacters;

    // Map which stores the different Characters and their amount in text
    private final Map<Character, Integer> characterAmount;

    // Map which points from a character to this character's percentage
    private final Map<Character, Double> characterPercentage;

    // Sorted keys (by value)
    private final List<Character> sortedKeys;

    // Constructor taking a String text which will be analyzed
    public LetterCounter(List<String> text) {
        // Get text raw
        String textRaw = TextUtils.getTextRaw(text, true, TextUtils.Case.LOWER_CASE);

        // Get textRaw's character-array
        char[] characters = textRaw.toCharArray();

        // Amount of total characters in textRaw
        this.totalCharacters = characters.length;

        // Set characterAmount
        this.characterAmount = this.scanChars(characters);

        // Amount of different characters in text
        this.differentCharacters = this.characterAmount.size();

        // Set sortedKeys (sorted by amount)
        this.sortedKeys = this.getSortedKeys(this.characterAmount);

        // Set characterPercentage
        this.characterPercentage = this.getPercentage(this.totalCharacters, this.characterAmount);
    }

    // Methode to print the letter frequency
    public void print() {
        // Go through sortedKeys
        for (char key : this.sortedKeys) {
            double percent = this.characterPercentage.get(key);
            int amount = this.characterAmount.get(key);

            System.out.printf("%s -> %2.2f%% (%d / %d) %n", key, percent, amount, this.totalCharacters);
        }
    }

    // Methode which "scans" the text and counts every character and puts it in "characters"
    private Map<Character, Integer> scanChars(char[] chars) {
        // Initialize a Map to store character amounts
        Map<Character, Integer> characters = new HashMap<>();

        // Go through all characters of textRow
        for (char character : chars) {
            // Get characters amount (if it isn't created 0)
            int amount = characters.getOrDefault(character, 0);

            // Set (amount+1) into the characters Map
            characters.put(character, amount+1);
        }

        return characters;
    }

    // Methode to getPercentage of each character given the total amount of
    // characters and a Map of the characters amounts.
    private Map<Character, Double> getPercentage(long amount, Map<Character, Integer> map) {
        Map<Character, Double> percentages = new HashMap<>();

        for (char key : map.keySet()) {
            int value = map.get(key);
            double percentage = this.getAsPercent(amount, value);
            percentages.put(key, percentage);
        }

        return percentages;
    }

    // Methode get a Map's keys sorted by values
    private <K, V extends Comparable<? super V>> List<K> getSortedKeys(Map<K, V> map) {
        // Return keys sorted by values using streams
        return map.keySet().stream().sorted(Comparator.comparing(map::get)).collect(Collectors.toList());
    }

    private double getAsPercent(double total, double portion) {
        return (portion/total)*100;
    }

    // Getter for characters-map
    public int getCharacterAmount(char c) {
        return this.characterAmount.getOrDefault(c, -1);
    }

    public double getCharacterPercentage(char c) {
        return this.characterPercentage.getOrDefault(c, -1D);
    }

    public int getDifferentCharacters() {
        return differentCharacters;
    }

    public long getTotalCharacters() {
        return totalCharacters;
    }

    // Getter for sortedKeys-list (sorted by values of characters in characterAmount)
    public List<Character> getSortedKeys() {
        return sortedKeys;
    }
}
