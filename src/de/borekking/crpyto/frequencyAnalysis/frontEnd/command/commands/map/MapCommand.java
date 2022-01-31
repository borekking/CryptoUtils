package de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.map;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisConnector;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractMainCommand;

public class MapCommand extends AbstractMainCommand {

    private final FrequencyAnalysisConnector main;

    public MapCommand(FrequencyAnalysisConnector main) {
        super("map", "map <cipher-char> <guess-char>", "Map a cipher character (lower case) to a real character (upper case).");
        this.main = main;
    }

    @Override
    public AbstractCommand use(String[] args) {
        if (args.length < 2) {
            this.printArgumentError();
            return null;
        }

        if (args[0].length() < 1 || args[1].length() < 1) {
            System.err.println("THESE ARGUMENTS ARE NOT EVEN A VALID CHARACTERS, STUPID IDIOT!");
            return null;
        }

        // Get characters
        char cipher = args[0].charAt(0), guess = args[1].charAt(0);

        // Provide errors due to case
        if (Character.isUpperCase(cipher)) cipher = Character.toLowerCase(cipher);
        if (Character.isLowerCase(guess)) guess = Character.toUpperCase(guess);

        // Save the current cipher and guess in userGuesses and safe if there was a change in "change"
        boolean change = this.main.putUserGuess(cipher, guess);

        // Print results
        if (!change) System.out.println("(map) " + cipher + " was already mapped to " + guess + ".");
        else System.out.println("(map) Mapped " + cipher + " to " + guess + ".");
        return null;
    }
}
