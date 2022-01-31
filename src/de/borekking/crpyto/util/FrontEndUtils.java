package de.borekking.crpyto.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class FrontEndUtils {

    private static final Scanner scanner = new Scanner(System.in);

    // Reading inputs
    public static String getInput(String msg, Predicate<String> matcher, BiConsumer<Boolean, String> replier) {
        System.out.println(msg);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            if (!matcher.test(line)) {
                replier.accept(false, line);
                continue;
            }

            replier.accept(true, line);
            return line;
        }

        return null;
    }

    public static String getInput(String msg, Predicate<String> matcher) {
        return getInput(msg, matcher, (a, b) -> {});
    }

    public static String getInput(String msg) {
        return getInput(msg, a -> true, (a, b) -> {});
    }

    // Get lines of File from user input
    public static List<String> getLines() throws FileNotFoundException {
        String fileName = FrontEndUtils.getInput("Please enter filename:", str -> Files.exists(Paths.get(str)),
                (success, str) -> {
                    if (!success) System.err.println("Input \"" + str + "\" was not a valid input!");
                });

        return FrontEndUtils.getLines(fileName);
    }

    // Readings file
    public static List<String> getLines(String fileName) throws FileNotFoundException {
        return getLines(new File(fileName));
    }

    public static List<String> getLines(File file) throws FileNotFoundException {
        Scanner sc;
        sc = new Scanner(file);

        // Create List lines
        List<String> lines = new ArrayList<>();

        // Fill "lines" with files content
        while (sc.hasNext())
            lines.add(sc.nextLine().toLowerCase());

        return lines;
    }
}
