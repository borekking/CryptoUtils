package de.borekking.crpyto.util;

import java.util.Map;

public class MapUtils {

    // Get first key to a value
    public static <K, V> K getKey(Map<K, V> map, V value) {
        return map.keySet().stream().filter(key -> map.get(key).equals(value)).findFirst().orElse(null);
    }
}
