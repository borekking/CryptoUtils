package de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.save;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisConnector;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractMainCommand;

public class SaveCommand extends AbstractMainCommand {

    private final FrequencyAnalysisConnector main;

    public SaveCommand(FrequencyAnalysisConnector main) {
        super("save", "save <guess-character(s)>", "Saves a guess which is guessed by the program right now.");

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

        } else if (args[0].length() == 1) {

            // Get the current cipher character of given guess/value
            char guess = args[0].charAt(0);
            this.save(guess);

        } else { // Argument's size is > 1
            char[] guesses = args[0].toCharArray();

            for (char guess : guesses) this.save(guess);
        }

        return null;
    }

    private void save(char guess) {
        if (Character.isLowerCase(guess)) guess = Character.toUpperCase(guess);
        Character cipher = this.main.getCipherToGuess(guess);

        // If the key/cipher was not found, send and error message
        if (cipher == null) {
            System.err.println("Cipher character/value of " + guess + " was not found!");
            return;
        }

        // Save the current cipher and guess in userGuesses and safe if there was a change in "change"
        boolean change = this.main.putUserGuess(cipher, guess);

        // Print results
        if (!change) System.out.println("(save) Guess " + guess + " was already saved.");
        else System.out.println("(save) Mapped " + cipher + " to " + guess + ". (Might removed old ciphers/guesses)");
    }
}
