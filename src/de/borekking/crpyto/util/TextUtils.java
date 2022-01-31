package de.borekking.crpyto.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TextUtils {

    public enum Case {
        IGNORE,
        UPPER_CASE,
        LOWER_CASE;
    }

    // Methode to get an array of Strings as row String with no spaces and lower case
    public static String getTextRaw(List<String> text, boolean removeSpaces, Case c) {
        if (removeSpaces) {
            return getTextRawWithoutSpaces(text, c);
        } else {
            return getTextRaw(text, c);
        }
    }

    public static String repeat(String str, int amount) {
        return IntStream.range(0, amount).mapToObj(i -> str).collect(Collectors.joining(""));
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < amount; i++) builder.append(str);
//        return builder.toString();
    }

    // Don't replace spaces
    private static String getTextRaw(List<String> text, Case c) {
        String str = String.join(System.lineSeparator(), text);
        return doCase(str, c);
    }

    private static String getTextRawWithoutSpaces(List<String> text, Case c) {
        String str = text.stream().map(s -> s.replace(" ", "")).collect(Collectors.joining(""));
        return doCase(str, c);
    }

    private static String doCase(String str, Case c) {
        switch(c) {
            case IGNORE:
                return str;
            case LOWER_CASE:
                return str.toLowerCase();
            case UPPER_CASE:
                return str.toUpperCase();
        }
        return null; // Unreachable
    }
}
