package de.borekking.crpyto.shiftCipher;

import de.borekking.crpyto.frequencyAnalysis.backEnd.FrequencyAnalysis;
import de.borekking.crpyto.frequencyAnalysis.backEnd.LetterCounter;
import de.borekking.crpyto.frequencyAnalysis.backEnd.letterFrequence.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ShiftCypherDecryption {

    /*
     *   Program that decrypts a Shift Cypher/Caesar cipher (https://en.wikipedia.org/wiki/Caesar_cipher)
     *   It will start using the FrequencyAnalysis ones an then going up from e (greatest percentage) and goes down
     *   (skipping doubles) then all other possibilities (max 26 for common English).
     *   The user will be able to press n (skip) or y (end).
     *
     * ONLY WORKS FOR LOWER CASE ALPHABETIC CHARACTERS!
     */

    // Text's lines
    private final List<String> lines;

    // FrequencyAnalysis object of text/lines
    private final FrequencyAnalysis frequencyAnalysis;

    public ShiftCypherDecryption(List<String> lines) {
        this.lines = lines;

        // Get Letter Count
        LetterCounter counter = new LetterCounter(this.lines);

        // Set FrequencyAnalysis
        this.frequencyAnalysis = new FrequencyAnalysis(Language.ENGLISH, counter);
    }

    // Get array of keys (0 to 25) to test based on their probability from frequencyAnalysis
    public Integer[] getKeys() {
        List<Integer> keys = new ArrayList<>();

        Map<Character, Character> guesses = this.frequencyAnalysis.makeGuess();

        List<Character> guessesKeys = new ArrayList<>(guesses.keySet());

        // Go from size to 0
        for (int i = guessesKeys.size() - 1; i >= 0; i--) {
            Character key = guessesKeys.get(i);
            int caeserKey = this.getKeyFromGuess(key, guesses.get(key));

            if (!keys.contains(caeserKey))
                keys.add(caeserKey);
        }

        // Check all possible keys (0-25) to be in
        for (int i = 0; i <= 25; i++) {
            if (!keys.contains(i)) keys.add(i);
        }

        return keys.toArray(new Integer[0]);
    }

    // Methode which takes a key (int) and returns the Text decrypted with the key
    public String useCaeserKey(int key) {
        if (key < 0) throw new IllegalArgumentException("Key " + key + " is too small!");

        StringJoiner joiner = new StringJoiner(System.lineSeparator());

        for (String line : this.lines)
            joiner.add(this.useCaeserKey(key, line));

        return joiner.toString();
    }

    private int getKeyFromGuess(char cypher, char guess) {
        // Make lower case
        if (Character.isUpperCase(cypher)) cypher = Character.toLowerCase(cypher);
        if (Character.isUpperCase(guess)) guess = Character.toLowerCase(guess);

        // Get indexes in alphabet
        int indexCypher = this.getCharIndex(cypher), indexGuess = this.getCharIndex(guess);

        // Return abs. difference
        return Math.abs(indexCypher - indexGuess);
    }

    // Methode which takes a key (int) and returns the Text decrypted with the key
    private String useCaeserKey(int key, String str) {
        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++)
            chars[i] = this.useCaeserKey(key, chars[i]);

        return new String(chars);
    }

    // Methode which takes a key (int) and returns an encrypted char with the key
    private char useCaeserKey(int key, char c) {
        // Get chars ascii-index
        int index = this.getCharIndex(c);

        // Get shifted index
        index = index + key;

        return this.getCharFromIndex(index % 26);
    }

    // Returns the index in the alphabet of a lower case, alphabetic number
    private int getCharIndex(char c) {
        return c % 97;
    }

    // Returns a lower case, alphabetic number from its index in the alphabet
    private char getCharFromIndex(int index) {
        if (index < 0 || index > 25)
            throw new IllegalArgumentException("Index " + index + " is not a valid index in the alphabet!");

        return (char) (index + 97);
    }
}
