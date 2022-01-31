package de.borekking.crpyto.frequencyAnalysis.backEnd.letterFrequence;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum LetterFrequency {

    /*
     * Source: https://en.wikipedia.org/wiki/Letter_frequency
     *
     */

    E(12.7),
    T(9.1),
    A(8.2),
    O(7.5),
    I(7D),
    N(6.8),
    S(6.3),
    H(6.09),
    R(5.98),
    D(4.2),
    L(4D),
    C(2.8),
    U(2.7),
    M(2.4),
    W(2.4),
    F(2.2),
    G(2.01),
    Y(1.97),
    P(1.92),
    B(1.5),
    V(1D),
    K(0.8),
    J(0.153),
    X(0.15),
    Q(0.09),
    Z(0.07);

    // Map Language to index in percentage array
    private static final Map<Language, Integer> indexes;

    static {
        // Set indexes
        indexes = new HashMap<>();
        indexes.put(Language.ENGLISH, 0);
    }

    public static List<LetterFrequency> getSortedLetters(Language language) {
        return Arrays.stream(LetterFrequency.values()).sorted(Comparator.comparingDouble(o -> o.getPercent(language))).collect(Collectors.toList());
    }

    private static int getIndex(Language language) {
        if (!LetterFrequency.indexes.containsKey(language)) {
            throw new IllegalArgumentException("This language is not supported: " + language);
        }

        return LetterFrequency.indexes.get(language);
    }

    private final char character;

    // Array of percentages depending on Language
    private final double[] percentages;

    LetterFrequency(char character, double... percentages) {
        this.character = character;
        this.percentages = percentages;
    }

    LetterFrequency(double... percentEN) {
        this(Character.MIN_VALUE, percentEN);
    }

    public char getCharacter() {
        return character != Character.MIN_VALUE ? character : this.name().charAt(0);
    }

    public double getPercent(Language language) {
        int index = LetterFrequency.getIndex(language);
        return percentages[index];
    }
}
