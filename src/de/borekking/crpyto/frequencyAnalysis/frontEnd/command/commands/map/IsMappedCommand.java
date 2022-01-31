package de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.map;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisConnector;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractMainCommand;

public class IsMappedCommand extends AbstractMainCommand {

    private final FrequencyAnalysisConnector main;

    public IsMappedCommand(FrequencyAnalysisConnector main) {
        super("ismapped", "ismapped <char>", "Check if you already mapped a cypher character.");

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
        boolean isMapped = this.main.isMapped(cipherCharacter);

        System.out.println("Cipher " + cipherCharacter + " is mapped: " + isMapped);
        return null;
    }
}
