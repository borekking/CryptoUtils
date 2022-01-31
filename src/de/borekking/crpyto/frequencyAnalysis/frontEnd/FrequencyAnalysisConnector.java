package de.borekking.crpyto.frequencyAnalysis.frontEnd;

import de.borekking.crpyto.frequencyAnalysis.backEnd.FrequencyAnalysis;
import de.borekking.crpyto.frequencyAnalysis.backEnd.LetterCounter;
import de.borekking.crpyto.frequencyAnalysis.backEnd.letterFrequence.Language;
import de.borekking.crpyto.util.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrequencyAnalysisConnector {

    /*
     * "Connects" between front- and backend
     *
     */

    // LetterCounter object
    private final LetterCounter counter;

    // Text's lines
    private final List<String> lines;

    // FrequencyAnalysis object of text/lines
    private final FrequencyAnalysis frequencyAnalysis;

    // Users guesses
    private final Map<Character, Character> userGuesses;

    // Current program's guesses
    private final Map<Character, Character> guesses;

    public FrequencyAnalysisConnector(List<String> fileLines) {
        // Init userGuesses
        this.userGuesses = new HashMap<>();

        // Set Lines
        this.lines = fileLines;

        // Get Letter Count
        this.counter = new LetterCounter(this.lines);

        // Set FrequencyAnalysis
        this.frequencyAnalysis = new FrequencyAnalysis(Language.ENGLISH, this.counter);

        // Get first guess
        this.guesses = this.frequencyAnalysis.makeGuess();
    }

    public void reloadGuesses() {
        this.guesses.clear();
        this.guesses.putAll(this.frequencyAnalysis.makeGuess(this.userGuesses));
    }

    // Return if a userGuesses contains a given guess (as value)
    public boolean containsUserGuess(char guess) {
        return this.userGuesses.values().stream().anyMatch(v -> v.equals(guess));
    }

    // Returns if cipher's value was null before.
    public boolean putUserGuess(char cipher, char guess) {
        // If there is any Node with cipher as key and guess as guess return false;
        if (this.userGuesses.keySet().stream().anyMatch(key -> key.equals(cipher) && this.userGuesses.get(key).equals(guess)))
            return false;

        // Remove eventual old cipher/key with this guess/value
        Character oldCipher = MapUtils.getKey(this.userGuesses, guess);
        if (oldCipher != null) this.removeUserGuess(oldCipher);

        // Put in and get old value/guess
        Character oldGuess = this.userGuesses.put(cipher, guess);

        // Return if the oldGuess was null
        return oldGuess == null;
    }

    // Removes a cypher character from userGuesses and returns if there was a value to this key before
    public boolean removeUserGuess(char cipher) {
        return this.userGuesses.remove(cipher) != null;
    }

    // Returns current cipher character (key) to a given guess
    public Character getCipherToGuess(char guess) {
        return MapUtils.getKey(this.guesses, guess);
    }

    // Returns current guess character (value) to a given cipher character
    public Character getGuessToCipher(char cipher) {
        return this.guesses.get(cipher);
    }

    // Returns if a cipher character is mapped to a guess by user
    public boolean isMapped(char cipher) {
        return this.userGuesses.containsKey(cipher);
    }

    public String getReplacedText(List<String> lines) {
        StringBuilder builder = new StringBuilder();

        for (String line : lines) {
            char[] chars = line.toCharArray();

            for (char current : chars) {
                if (current == ' ') {
                    builder.append(current);
                    continue;
                }

                Character character = this.guesses.get(current);

                if (character == null) builder.append("?");
                else builder.append(character);
            }

            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    public LetterCounter getCounter() {
        return counter;
    }
}
