package de.borekking.crpyto;

import de.borekking.crpyto.listener.CommandListener;
import de.borekking.crpyto.listener.CommandManager;
import de.borekking.crpyto.util.IntegerUtils;
import de.borekking.crpyto.util.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    /*
     * TODO
     *   Frequency Analysis:
     *      - Add "finish" command which shows some analytics about command-usage, programs accuracy (first guesses to latest guesses)
     *      - Add Dictionary (https://dictionary-api.cambridge.org/api/resources#java)
     *          -> Try to find existing words (maybe min. character amount to prevent wrong words) and save them
     *          -> Try to find words where only one character is different and change it (reload guesses then)
     *
     */

    /*
     * FrequencyAnalysis:
     *
     * Program to analyze a substitution cipher based on its character frequency.
     * For more details see: https://en.wikipedia.org/wiki/Frequency_analysis
     *
     * Right now this only works on English texts with max. 26 Letters from the alphabet where
     * every cipher letter is mapped to ONE "real" letter.
     *
     * Note:
     *      Do not aspect this to work perfectly. As longer the text is, the better it will work.
     *      For shorter texts you'll have to run the program ones and then look for pattern in the text
     *      and mapping them "by hand" using the command below.
     *
     * Usage:
     *      1) Choose 0 (Frequency Analysis) at the program's start.
     *         Type in the fileName with the text.
     *           -> You'll get Letter Count printed
     *           -> You'll get the program's first guesses printed
     *      2) Commands:
     *          Get help by typing "help"
     *
     */

    private static class SystemCommandManager implements CommandManager {

        private final static String EXIT_COMMAND_NAME = "exit";

        private final static String RESET_COMMAND_NAME = "reset";

        @Override
        public String getName() {
            return "System";
        }

        @Override
        public boolean execute(String command, String[] args) {
            if (command.equals(EXIT_COMMAND_NAME)) {

                // Print Info
                System.out.println("----------");
                System.out.println("Stopping Program");
                System.out.println("----------");

                // Exit with zero exit-code
                System.exit(0);
                return true;
            } else if (command.equals(RESET_COMMAND_NAME)) {
                // Print Info
                Main.reset();
                return true;
            }

            return false;
        }

        @Override
        public List<String> getCommandNames() {
            return new ArrayList<>(Arrays.asList(EXIT_COMMAND_NAME, RESET_COMMAND_NAME));
        }
    }

    // Main-Methode calling the Main constructor
    public static void main(String[] args) {
        new Main();
    }

    public static void reset() {
        System.out.println();
        System.out.println("----------");
        System.out.println("Resetting current Program");
        System.out.println("----------");
        System.out.println();

        new Main();
    }

    // Main constructor getting a user input choosing the program to call
    private Main() {
        Program program = this.getSubProgram();

        if (program == null) {
            System.err.println("No program found - why the hell.");
            return;
        }

        System.out.println("You have chosen " + program.getName() + System.lineSeparator());

        program.run();

        // Start Command Listener
        CommandListener listener = new CommandListener();
        listener.addCommandManager(new SystemCommandManager());
        listener.addCommandManager(program.getCommandManager());
        listener.start();
    }

    private Program getSubProgram() {
        // Get array of programs
        Program[] programs = Program.values();

        // Print
        System.out.println("Please enter a program's number to use of these:");
        for (int i = 0; i < programs.length; i++) {
            Program program = programs[i];
            System.out.println("    " + i + ") " + program.getName() + ": ");

            String additionalSpaces = TextUtils.repeat(" ",2 + String.valueOf(i).length());
            System.out.println("    " + additionalSpaces + program.getDescription());
        }

        // Get user input
        Scanner sc = new Scanner(System.in);

        String input;
        while (sc.hasNext()) {
            input = sc.nextLine();

            if (IntegerUtils.isInt(input)) {
                int index = Integer.parseInt(input);
                if (index >= 0 && index < programs.length) {
                    return programs[index];
                }
            }

            System.out.println("Please enter a valid number!");
        }

        // Unreachable
        return null;
    }
}
