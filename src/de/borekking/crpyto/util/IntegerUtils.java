package de.borekking.crpyto.util;

public class IntegerUtils {

    // Integer's patters
    public static String INTEGER_PATTERN = "[-]?\\d+";

    // Return whether a String str represents an integer
    public static boolean isInt(String str) {
        if (str == null) return false;

        return str.matches(INTEGER_PATTERN);
    }
}
