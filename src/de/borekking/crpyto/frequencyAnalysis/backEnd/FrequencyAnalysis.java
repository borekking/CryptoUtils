package de.borekking.crpyto.frequencyAnalysis.backEnd;

import de.borekking.crpyto.frequencyAnalysis.backEnd.letterFrequence.Language;
import de.borekking.crpyto.frequencyAnalysis.backEnd.letterFrequence.LetterFrequency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrequencyAnalysis {

    private final Language language;

    private final LetterCounter counter;

    public FrequencyAnalysis(Language language, LetterCounter counter) {
        this.language = language;
        this.counter = counter;
    }

    public Map<Character, Character> makeGuess() {
        return this.makeGuess(new HashMap<>());
    }

    // Returns a guess of which character might belong to which real characters
    public Map<Character, Character> makeGuess(Map<Character, Character> guesses) {
        // Reset guesses to don't change to actual values
        Map<Character, Character> finalGuesses = new HashMap<>(guesses);

        // Get letterFrequencies from Language
        List<LetterFrequency> letterFrequencies = LetterFrequency.getSortedLetters(this.language);

        // Remove letters which characters are already guessed
        letterFrequencies.removeIf(lf -> finalGuesses.containsValue(lf.getCharacter()));

        Map<Character, Character> restGuesses = this.makeGuess(letterFrequencies, finalGuesses.keySet());

        finalGuesses.putAll(restGuesses);
        return finalGuesses;
    }

    // Returns 1:1 mapping of counter keys (cipher) without "skips" and letterFrequencies' characters (guess)
    private Map<Character, Character> makeGuess(List<LetterFrequency> letterFrequencies, Collection<Character> skips) {
        // Create List of Characters of text sorted by amount
        List<Character> characters = new ArrayList<>(this.counter.getSortedKeys());
        // Remove skips
        characters.removeAll(skips);

        // Return 1:1 mapping
        return this.mapOneToOne(letterFrequencies, characters);
    }

    // Maps List of LetterFrequency to List of Characters 1:1 based on their percentage.
    // "characters" should be sorted by percentage!
    private Map<Character, Character> mapOneToOne(List<LetterFrequency> letterFrequencies, List<Character> characters) {
        // Set sizes
        int charactersSize = characters.size(), letterFrequenciesSize = letterFrequencies.size();

        // Check if there are more than allowed different characters (when mapping 1:1)
        if (charactersSize > letterFrequenciesSize) {
            throw new IllegalArgumentException("Too many different characters: " + charactersSize);
        }

        // Init guesses HashMap
        Map<Character, Character> guesses = new HashMap<>(charactersSize);

        // For safety reason sort ones again
        letterFrequencies.sort(Comparator.comparingDouble(o -> o.getPercent(this.language)));

        // Fill guesses going from biggest to smallest
        for (int i = charactersSize-1, j = letterFrequenciesSize-1; i >= 0; i--, j--)
            guesses.put(characters.get(i), letterFrequencies.get(j).getCharacter());

        return guesses;
    }
}
