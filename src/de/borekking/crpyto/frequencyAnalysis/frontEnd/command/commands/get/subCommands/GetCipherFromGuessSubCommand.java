package de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.get.subCommands;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisConnector;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractSubCommand;

public class GetCipherFromGuessSubCommand extends AbstractSubCommand {

    private final FrequencyAnalysisConnector main;

    public GetCipherFromGuessSubCommand(AbstractCommand parent, FrequencyAnalysisConnector main) {
        super("guess", "guess <cipher-character> ", parent);
        this.main = main;
    }

    @Override
    public AbstractCommand use(String[] args) {
        if (args.length < 1) {
            this.printArgumentError();
            return null;
        }

        if (args[0].length() < 1) {
            System.err.println("THIS ARGUMENT IS NOT EVEN A VALID CHARACTER, STUPID IDIOT!");
            return null;
        }

        char cipherCharacter = args[0].charAt(0);
        Character guess = this.main.getGuessToCipher(cipherCharacter);

        if (guess == null) System.err.println("Guess to " + cipherCharacter + " could not be found.");
        else System.out.println("Guess to " + cipherCharacter + " (cipher): " + guess + " (guess)");
        return null;
    }
}
