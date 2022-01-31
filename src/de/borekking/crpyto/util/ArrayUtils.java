package de.borekking.crpyto.util;

import java.util.Arrays;

public class ArrayUtils {

    /**
     *
     * @param arr The array.
     * @param amount The amount of elements to remove from the start of the array.
     * @param <T> The Arrays type.
     * @return The array without the amount elements in the front.
     */
    public static <T> T[] removeFirst(T[] arr, int amount) {
        return Arrays.copyOfRange(arr, amount, arr.length);
    }
}
