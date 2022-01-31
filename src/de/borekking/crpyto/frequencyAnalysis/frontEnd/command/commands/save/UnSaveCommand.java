package de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.save;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisConnector;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractMainCommand;

public class UnSaveCommand extends AbstractMainCommand {

    private final FrequencyAnalysisConnector main;

    public UnSaveCommand(FrequencyAnalysisConnector main) {
        super("unsave", "unsave <guess-character>", "Removes saved  guess-character.");

        this.main = main;
    }

    @Override
    public AbstractCommand use(String[] args) {
        if (args.length < 1) {
            this.printArgumentError();
            return null;
        }

        if (args[0].length() < 1) {
            System.err.println("THESE ARGUMENTS ARE NOT EVEN A VALID CHARACTERS, STUPID IDIOT!");
            return null;
        }

        // Get characters
        char guess = args[0].charAt(0);
        if (Character.isLowerCase(guess)) guess = Character.toUpperCase(guess);
        Character cipher = this.main.getCipherToGuess(guess);

        // If the key/cipher was not found, send and error message
        if (cipher == null) {
            System.err.println("Cipher character/value of " + guess + " was not found!");
            return null;
        }

        boolean change = this.main.removeUserGuess(cipher);

        // Print results
        if (!change) System.out.println("(unsave) Guess " + guess + " was not saved.");
        else System.out.println("(unsave) Unsaved " + guess + " (" + cipher + ") .");
        return null;
    }
}
