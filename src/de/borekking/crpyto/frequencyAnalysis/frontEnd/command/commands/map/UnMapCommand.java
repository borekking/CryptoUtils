package de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.map;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisConnector;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractMainCommand;

public class UnMapCommand extends AbstractMainCommand {

    private final FrequencyAnalysisConnector main;

    public UnMapCommand(FrequencyAnalysisConnector main) {
        super("unmap", "unmap <cipher-char>", "Unmap a mapped cipher character.");
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

        char cipher = args[0].charAt(0);
        if (Character.isUpperCase(cipher)) cipher = Character.toLowerCase(cipher);

        // Remove
        boolean removed = this.main.removeUserGuess(cipher);

        // Print
        if (removed) System.out.println("(unmap) Unmapped " + cipher + ".");
        else System.err.println("(unmap) Could not find " + cipher + ".");
        return null;
    }
}
