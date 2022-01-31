package de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.get.subCommands;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisConnector;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractSubCommand;

public class GetGuessFromCipherSubCommand extends AbstractSubCommand {

    private final FrequencyAnalysisConnector main;

    public GetGuessFromCipherSubCommand(AbstractCommand parent, FrequencyAnalysisConnector main) {
        super("cipher", "cipher <guess-character> ", parent);

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

        char guessCharacter = args[0].charAt(0);
        Character cipher = this.main.getCipherToGuess(guessCharacter);

        if (cipher == null) System.err.println("Cipher character to " + guessCharacter + " could not be found.");
        else System.out.println("Cipher character to " + guessCharacter + " (guess): " + cipher + " (cipher)");
        return null;
    }
}
