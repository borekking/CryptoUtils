package de.borekking.crpyto.frequencyAnalysis.frontEnd;

import de.borekking.crpyto.IProgram;
import de.borekking.crpyto.frequencyAnalysis.backEnd.LetterCounter;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.FrequencyAnalysisCommandManager;
import de.borekking.crpyto.listener.CommandManager;
import de.borekking.crpyto.util.FrontEndUtils;

import java.io.FileNotFoundException;
import java.util.List;

public class FrequencyAnalysisFrontEnd implements IProgram {

    private List<String> lines;

    private CommandManager commandManager;

    // Connector which "Connects" between front- and backend
    private FrequencyAnalysisConnector connector;

    public FrequencyAnalysisFrontEnd() {
    }

    public void print(FrequencyAnalysisConnector program) {
        // Get counter
        LetterCounter counter = program.getCounter();

        // Set identifier
        String identifier = "----------";

        // Print identifier
        System.out.println(identifier);

        // Get sorted keys
        List<Character> sortedCharacter = counter.getSortedKeys();

        // Print Letter with percentage amount and mapping
        for (char c : sortedCharacter) {
            double percent = counter.getCharacterPercentage(c);
            int amount = counter.getCharacterAmount(c);
            char guess = program.getGuessToCipher(c);

            System.out.printf("%s -> %2.2f%% (%d / %d) => %s %n", c, percent, amount, counter.getTotalCharacters(), guess);
        }

        // Print empty line
        System.out.println();

        // Print replaced Text
        String replaced = program.getReplacedText(this.lines);
        System.out.println(replaced);

        // Print identifier
        System.out.println(identifier);
    }

    @Override
    public void run() {
        try {
            this.lines = FrontEndUtils.getLines();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.connector = new FrequencyAnalysisConnector(this.lines);

        this.commandManager = new FrequencyAnalysisCommandManager(this.connector, this);

        // Print FrequencyAnalysis with guesses
        this.print(this.connector);
    }

    @Override
    public CommandManager getCommandManager() {
        return this.commandManager;
    }
}
