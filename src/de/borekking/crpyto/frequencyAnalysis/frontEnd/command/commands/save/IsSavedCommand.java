package de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.save;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisConnector;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractMainCommand;

public class IsSavedCommand extends AbstractMainCommand {

    private final FrequencyAnalysisConnector main;

    public IsSavedCommand(FrequencyAnalysisConnector main) {
        super("issaved", "issaved <guess-character>", "Check if a guess-character (from printed text) was saved.");

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
        boolean containsGuess = this.main.containsUserGuess(guess);

        // Print
        if (containsGuess) System.out.println("(issaved) Guess " + guess + " is already saved.");
        else System.out.println("(issaved) Guess " + guess + " is NOT saved yet.");
        return null;
    }
}
